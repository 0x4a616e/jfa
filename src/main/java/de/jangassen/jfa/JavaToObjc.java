package de.jangassen.jfa;

import com.sun.jna.Callback;
import de.jangassen.jfa.annotation.ConformsToProtocols;
import de.jangassen.jfa.annotation.Superclass;
import de.jangassen.jfa.annotation.Unmapped;
import de.jangassen.jfa.cleanup.NSCleaner;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import de.jangassen.jfa.util.StreamUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class JavaToObjc {

  private static final ConcurrentHashMap<String, ID> NAME_TO_CLASS = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<ID, WeakReference<Object>> INSTANCE_TO_JAVA = new ConcurrentHashMap<>();
  // We need to keep references to all callbacks to prevent GC
  private static final ConcurrentHashMap<ID, List<Callback>> CLASS_TO_CALLBACK = new ConcurrentHashMap<>();

  private JavaToObjc() {
  }

  public static ID map(Object obj) {
    return map(obj, obj.getClass());
  }

  public static ID map(Object obj, Class<?> clazz) {
    ID classId = getClassId(clazz);
    ID instanceId = Foundation.invoke(classId, "alloc");

    INSTANCE_TO_JAVA.put(instanceId, new WeakReference<>(obj));
    NSCleaner.register(obj, instanceId);

    return instanceId;
  }

  public static ID getClassId(Class<?> objectClass) {
    return NAME_TO_CLASS.computeIfAbsent(objectClass.getSimpleName(), key -> defineClass(objectClass, key));
  }

  private static ID defineClass(Class<?> clazz, String simpleName) {
    if (simpleName == null || "".equals(simpleName)) {
      throw new IllegalArgumentException("Mapping anonymous classes is not supported");
    }

    String superclass = getSuperclass(clazz);
    ID classId = Foundation.allocateObjcClassPair(Foundation.getObjcClass(superclass), simpleName);
    addProtocols(clazz, classId);

    Foundation.registerObjcClassPair(classId);

    addMethods(clazz, classId);
    return classId;
  }

  private static String getSuperclass(Class<?> clazz) {
    return Optional.ofNullable(clazz.getAnnotation(Superclass.class))
            .map(Superclass::value)
            .orElse("NSObject");
  }

  private static void addMethods(Class<?> clazz, ID classId) {
    Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> !method.isSynthetic() && !Modifier.isStatic(method.getModifiers()))
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .filter(method -> !method.isAnnotationPresent(Unmapped.class))
            .forEach(method -> addMethod(classId, method));
  }

  private static void addProtocols(Class<?> clazz, ID classId) {
    String[] protocols = Optional.ofNullable(clazz.getAnnotation(ConformsToProtocols.class)).map(ConformsToProtocols::value).orElse(new String[0]);
    for (String protocol : protocols) {
      Foundation.addProtocol(classId, Foundation.getProtocol(protocol));
    }
  }

  private static void addMethod(ID classId, Method method) {
    Callback callback = getCallback(method);
    CLASS_TO_CALLBACK.computeIfAbsent(classId, key -> new ArrayList<>()).add(callback);
    Foundation.addMethod(classId, Selector.forMethod(method), callback, getTypes(method));
  }

  private static Callback getCallback(Method method) {
    switch (method.getParameterCount()) {
      case 0:
        return new Callback() {
          @SuppressWarnings("unused")
          public ID callback(ID self, ID cmd) throws InvocationTargetException, IllegalAccessException {
            return invoke(self, method, new ID[0]);
          }
        };
      case 1:
        return new Callback() {
          @SuppressWarnings("unused")
          public ID callback(ID self, ID cmd, ID arg) throws InvocationTargetException, IllegalAccessException {
            return invoke(self, method, new ID[]{arg});
          }
        };
      default:
        throw new IllegalArgumentException("Method with " + method.getParameterCount() + " parameters not supported.");
    }
  }

  private static ID invoke(ID self, Method method, ID[] args) throws IllegalAccessException, InvocationTargetException {
    WeakReference<?> javaObject = INSTANCE_TO_JAVA.get(self);

    if (javaObject != null) {
      Object obj = javaObject.get();
      if (obj != null) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] objects = StreamUtils.zipWithIndex(parameterTypes).map(t ->
                ObjcToJava.map(args[t.getIndex()], (Class<?>) t.getValue())
        ).toArray();

        Object invoke = method.invoke(obj, objects);
        return ObjcToJava.toID(invoke);
      }
    }

    return ID.NIL;
  }

  private static String getTypes(Method method) {
    return toType(method.getReturnType()) + "@:@" + Arrays.stream(method.getParameterTypes())
            .map(JavaToObjc::toType)
            .map(t -> ":" + t)
            .collect(Collectors.joining());
  }

  private static String toType(Class<?> clazz) {
    if (clazz == Void.class || clazz == void.class) {
      return "v";
    } else if (int.class == clazz) {
      return "i";
    } else if (long.class == clazz) {
      return "l";
    } else if (Object.class.isAssignableFrom(clazz)) {
      return "@";
    }

    return "?";
  }
}
