package de.jangassen.jfa;

import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.ByReference;
import de.jangassen.jfa.annotation.Protocol;
import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import de.jangassen.jfa.foundation.VarArgs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;

public class ObjcToJava implements InvocationHandler {
  private static final String EQUALS = "equals";
  private static final String HASH_CODE = "hashCode";
  private static final String TO_STRING = "toString";
  private static final String DESCRIPTION = "description";

  private final ID id;

  private ObjcToJava(ID id) {
    this.id = id;
  }

  public static <T extends NSObject> T alloc(Class<T> clazz) {
    return invokeStatic(clazz, "alloc");
  }

  public static <T extends NSObject> T invokeStatic(Class<T> clazz, String selector, Object... args) {
    if (clazz.isAnnotationPresent(Protocol.class)) {
      throw new IllegalArgumentException("Cannot allocate protocols.");
    }

    ID instance = Foundation.invoke(getObjcClass(clazz.getSimpleName()), selector, args);
    return map(instance, clazz);
  }

  @SuppressWarnings("unchecked")
  public static <T> T map(ID result, Class<T> javaType) {
    if (NSObject.class.isAssignableFrom(javaType)) {
      return mapNSObject(result, javaType);
    } else if (String.class.isAssignableFrom(javaType)) {
      return (T) Foundation.toStringViaUTF8(result);
    } else if (long.class == javaType || Long.class == javaType) {
      return (T) Long.valueOf(result.longValue());
    } else if (int.class == javaType || Integer.class == javaType) {
      return (T) Integer.valueOf(result.intValue());
    } else if (double.class == javaType || Double.class == javaType) {
      return (T) Double.valueOf(result.doubleValue());
    } else if (float.class == javaType || Float.class == javaType) {
      return (T) Float.valueOf(result.floatValue());
    } else if (boolean.class == javaType || Boolean.class == javaType) {
      return (T) Boolean.valueOf(result.booleanValue());
    } else if (byte.class == javaType || Byte.class == javaType) {
      return (T) Byte.valueOf(result.byteValue());
    } else if (short.class == javaType || Short.class == javaType) {
      return (T) Short.valueOf(result.shortValue());
    } else if (ID.class.isAssignableFrom(javaType) || Object.class == javaType) {
      return (T) result;
    } else if (Void.class == javaType || void.class == javaType) {
      return null;
    } else if (Pointer.class == javaType) {
      return (T) new Pointer(result.longValue());
    }

    throw new IllegalArgumentException(javaType.getSimpleName() + " is not supported.");
  }

  private static <T> T mapNSObject(ID id, Class<T> clazz) {
    return clazz.cast(Proxy.newProxyInstance(ObjcToJava.class.getClassLoader(), new Class[]{clazz}, new ObjcToJava(id)));
  }

  public static Optional<Class<?>> getJavaClass(ID id) {
    return getJavaClass(id, NSObject.class.getPackage());
  }

  public static Optional<Class<?>> getJavaClass(ID id, Package containingPackage) {
    if (id != null && !ID.NIL.equals(id) && containingPackage != null) {
      try {
        Pointer classForCoderSelector = Foundation.createSelector("classForCoder");
        if (respondsToSelector(id, classForCoderSelector)) {
          ID classNameId = Foundation.invoke(id, classForCoderSelector);
          String className = Foundation.stringFromClass(classNameId);
          return Optional.of(Class.forName(containingPackage.getName() + "." + className));
        }
      } catch (ClassNotFoundException | RuntimeException ignored) {
      }
    }
    return Optional.empty();
  }

  private static boolean respondsToSelector(ID id, Pointer classNameSelector) {
    return Foundation.invoke(id, "respondsToSelector:", classNameSelector).booleanValue();
  }

  public static Object toFoundationArgument(Object arg) {
    if (arg instanceof Structure || arg instanceof Foundation.CGFloat) {
      return arg;
    }

    return toID(arg);
  }

  public static ID toID(Object arg) {
    if (arg == null) {
      return ID.NIL;
    } else if (isFoundationProxy(arg)) {
      return getIdFromProxy(arg);
    } else if (arg instanceof String) {
      return Foundation.nsString((String) arg);
    } else if (arg instanceof ID) {
      return (ID) arg;
    } else if (arg instanceof Number) {
      return new ID(((Number) arg).longValue());
    } else if (arg instanceof Pointer) {
      return new ID((Pointer) arg);
    } else if (arg instanceof ByReference) {
      return new ID(((ByReference) arg).getPointer());
    } else if (arg instanceof Method) {
      return new ID(Selector.forMethod((Method) arg));
    } else if (arg instanceof NativeMapped) {
      return toID(((NativeMapped) arg).toNative());
    } else if (arg instanceof Enum<?>) {
      return Foundation.nsString(((Enum<?>) arg).name());
    } else if (arg instanceof Boolean) {
      return new ID((boolean) arg ? 1L : 0L);
    }

    throw new IllegalArgumentException(arg.getClass().getSimpleName() + " is not supported");
  }

  private static ID getIdFromProxy(Object arg) {
    return ((ObjcToJava) Proxy.getInvocationHandler(arg)).getId();
  }

  private static boolean isFoundationProxy(Object arg) {
    return Proxy.isProxyClass(arg.getClass()) && Proxy.getInvocationHandler(arg) instanceof ObjcToJava;
  }

  private ID getId() {
    return id;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    switch (method.getName()) {
      case EQUALS:
        return isFoundationProxy(args[0]) && Objects.equals(getId(), getIdFromProxy(args[0]));
      case HASH_CODE:
        return id.hashCode();
      case TO_STRING:
        return Foundation.toStringViaUTF8(Foundation.invoke(id, DESCRIPTION));
      default:
        return invokeNative(method, args);
    }
  }

  private Object invokeNative(Method method, Object[] args) {
    Object[] foundationArguments = getFoundationArguments(args);
    String selector = Selector.stringForMethod(method);

    ID result = Foundation.invoke(id, selector, foundationArguments);
    if (!isPrimitiveType(method.getReturnType()) && Foundation.isNil(result) || void.class == method.getReturnType()) {
      return null;
    }

    return wrapReturnValue(method, result);
  }

  private boolean isPrimitiveType(Class<?> returnType) {
    return boolean.class == returnType
            || int.class == returnType
            || long.class == returnType
            || double.class == returnType
            || float.class == returnType;
  }

  private Object wrapReturnValue(Method method, ID result) {
    Class<?> returnType = getReturnType(method, result);
    return map(result, returnType);
  }

  private Class<?> getReturnType(Method method, ID result) {
    Class<?> returnType = method.getReturnType();

    if (NSObject.class.isAssignableFrom(returnType)) {
      Optional<Class<?>> javaClass = getJavaClass(result);
      if (javaClass.isPresent()) {
        return javaClass.get();
      }
    }

    return returnType;
  }

  private Object[] getFoundationArguments(Object[] args) {
    return args == null ? new Object[0] : Arrays.stream(args)
            .flatMap(this::flattenVarArgs)
            .map(ObjcToJava::toFoundationArgument).toArray();
  }

  private Stream<Object> flattenVarArgs(Object value) {
    if (value instanceof VarArgs) {
      return Stream.concat(((VarArgs<?>) value).getArgs().stream(), Stream.of((Object) null));
    }

    return Stream.of(value);
  }
}

