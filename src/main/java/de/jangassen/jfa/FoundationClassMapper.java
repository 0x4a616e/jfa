package de.jangassen.jfa;

import com.sun.jna.Callback;
import de.jangassen.jfa.cleanup.NSCleaner;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class FoundationClassMapper {

  private static final ConcurrentHashMap<String, ID> NAME_TO_CLASS = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<ID, WeakReference<Object>> INSTANCE_TO_JAVA = new ConcurrentHashMap<>();

  private FoundationClassMapper() {
  }

  public static ID createInstance(Object obj) {
    Class<?> objectClass = obj.getClass();

    ID classId = NAME_TO_CLASS.computeIfAbsent(objectClass.getSimpleName(), key -> defineClass(objectClass, key));
    ID instanceId = Foundation.invoke(classId, "alloc");

    INSTANCE_TO_JAVA.put(instanceId, new WeakReference<>(obj));
    NSCleaner.register(obj, instanceId);

    return instanceId;
  }

  private static ID defineClass(Class<?> clazz, String simpleName) {
    ID classId = Foundation.allocateObjcClassPair(Foundation.getObjcClass("NSObject"), simpleName);
    Foundation.registerObjcClassPair(classId);

    Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> !method.isSynthetic() && !Modifier.isStatic(method.getModifiers()))
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .forEach(method -> addMethod(classId, method));

    return classId;
  }

  private static void addMethod(ID classId, Method method) {
    Foundation.addMethod(classId, Foundation.createSelector(method.getName()), getCallback(method), getTypes(method));
  }

  private static Callback getCallback(Method method) {
    return new Callback() {
      @SuppressWarnings("unused")
      public void callback(ID self, ID cmd) throws InvocationTargetException, IllegalAccessException {
        invoke(self, method, new Object[0]);
      }
    };
  }

  private static void invoke(ID self, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
    WeakReference<?> javaObject = INSTANCE_TO_JAVA.get(self);

    if (javaObject != null) {
      Object obj = javaObject.get();
      if (obj != null) {
        method.invoke(obj, args);
      }
    }
  }

  private static String getTypes(Method method) {
    return toType(method.getReturnType()) + "@:@" + Arrays.stream(method.getParameterTypes())
            .map(FoundationClassMapper::toType)
            .map(t -> ":" + t)
            .collect(Collectors.joining());
  }

  private static char toType(Class<?> clazz) {
    if (clazz == Void.class || clazz == void.class) {
      return 'v';
    } else if (int.class == clazz) {
      return 'i';
    } else if (long.class == clazz) {
      return 'l';
    } else if (Object.class.isAssignableFrom(clazz)) {
      return '@';
    }

    return '?';
  }
}
