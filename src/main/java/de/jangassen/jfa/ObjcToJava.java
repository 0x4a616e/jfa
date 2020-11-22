package de.jangassen.jfa;

import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;

public class ObjcToJava implements InvocationHandler {
  private static final String EQUALS = "equals";
  private static final String HASH_CODE = "hashCode";
  private static final String TO_STRING = "toString";
  private static final String DESCRIPTION = "description";

  private final ID id;

    public static <T extends NSObject> T alloc(Class<T> clazz) {
        return invokeStatic(clazz, "alloc");
    }

    public static <T extends NSObject> T invokeStatic(Class<T> clazz, String selector) {
        ID instance = Foundation.invoke(getObjcClass(clazz.getSimpleName()), selector);
        return map(instance, clazz);
    }

    public static <T extends NSObject> T map(ID id, Class<T> clazz) {
        return clazz.cast(Proxy.newProxyInstance(ObjcToJava.class.getClassLoader(), new Class[]{clazz}, new ObjcToJava(id)));
    }

    private ObjcToJava(ID id) {
        this.id = id;
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

        ID result = Foundation.safeInvoke(id, selector, foundationArguments);
        if (!isPrimitiveType(method.getReturnType()) && Foundation.isNil(result)) {
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

    @SuppressWarnings("unchecked")
    private Object wrapReturnValue(Method method, ID result) {
        Class<?> returnType = method.getReturnType();
        if (NSObject.class.isAssignableFrom(returnType)) {
            return map(result, (Class<? extends NSObject>) returnType);
        } else if (String.class.isAssignableFrom(returnType)) {
            return Foundation.toStringViaUTF8(result);
        } else if (long.class == returnType || Long.class == returnType) {
            return result.longValue();
        } else if (int.class == returnType || Integer.class == returnType) {
            return result.longValue();
        } else if (double.class == returnType || Double.class == returnType) {
            return result.doubleValue();
        } else if (float.class == returnType || Float.class == returnType) {
            return result.floatValue();
        } else if (boolean.class == returnType || Boolean.class == returnType) {
            return result.booleanValue();
        }

        return result;
    }

    private Object[] getFoundationArguments(Object[] args) {
        return args == null ? new Object[0] : Arrays.stream(args).map(this::toFoundationArgument).toArray();
    }

    private Object toFoundationArgument(Object arg) {
        if (arg == null) {
            return ID.NIL;
        } else if (isFoundationProxy(arg)) {
            return getIdFromProxy(arg);
        } else if (arg instanceof String) {
            return Foundation.nsString((String) arg);
        }

        return arg;
    }

  private ID getIdFromProxy(Object arg) {
    return ((ObjcToJava) Proxy.getInvocationHandler(arg)).getId();
  }

  private boolean isFoundationProxy(Object arg) {
    return Proxy.isProxyClass(arg.getClass()) && Proxy.getInvocationHandler(arg) instanceof ObjcToJava;
  }
}

