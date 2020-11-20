package de.jangassen.jfa;

import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;

public class FoundationProxy implements InvocationHandler {
    private final ID id;

    public static <T extends NSObject> T alloc(Class<T> clazz) {
        ID instance = Foundation.invoke(getObjcClass(clazz.getSimpleName()), "alloc");
        return wrap(instance, clazz);
    }

    public static <T extends NSObject> T wrap(ID id, Class<T> clazz) {
        return clazz.cast(Proxy.newProxyInstance(FoundationProxy.class.getClassLoader(), new Class[]{clazz}, new FoundationProxy(id)));
    }

    private FoundationProxy(ID id) {
        this.id = id;
    }

    private ID getId() {
        return id;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.getName().equals("equals")) {
            return false;
        } else if (method.getName().equals("hashCode")) {
            return id.hashCode();
        }
        if (method.getName().equals("toString")) {
            return Foundation.toStringViaUTF8(Foundation.invoke(id, "description"));
        }

        Object[] foundationArguments = getFoundationArguments(args);
        String selector = getSelector(method);

        ID result = Foundation.safeInvoke(id, selector, foundationArguments);
        if (Foundation.isNil(result)) {
            return null;
        }
        return wrapReturnValue(method, result);
    }

    private String getSelector(Method method) {
        if (method.getParameterCount() == 0) {
            return method.getName();
        }

        String parameterNames = Arrays.stream(method.getParameters())
                .skip(1)
                .map(this::getParameterNames)
                .map(p -> p + ":")
                .collect(Collectors.joining());

        return method.getName() + ":" + parameterNames;
    }

    private String getParameterNames(Parameter p) {
        return Optional.ofNullable(p.getAnnotation(NamedArg.class))
                .map(NamedArg::value)
                .orElse("");
    }

    @SuppressWarnings("unchecked")
    private Object wrapReturnValue(Method method, ID result) {
        Class<?> returnType = method.getReturnType();
        if (NSObject.class.isAssignableFrom(returnType)) {
            return wrap(result, (Class<? extends NSObject>) returnType);
        } else if (String.class.isAssignableFrom(returnType)) {
            return Foundation.toStringViaUTF8(result);
        } else if (long.class == returnType || Long.class == returnType) {
            return result.longValue();
        } else if (int.class == returnType || Integer.class == returnType) {
            return result.longValue();
        }

        return result;
    }

    private Object[] getFoundationArguments(Object[] args) {
        return args == null ? new Object[0] : Arrays.stream(args).map(this::toFoundationArgument).toArray();
    }

    private Object toFoundationArgument(Object arg) {
        if (arg == null) {
            return ID.NIL;
        } else if (Proxy.isProxyClass(arg.getClass()) && Proxy.getInvocationHandler(arg) instanceof FoundationProxy) {
            return ((FoundationProxy) Proxy.getInvocationHandler(arg)).getId();
        } else if (arg instanceof String) {
            return Foundation.nsString((String) arg);
        }

        return arg;
    }
}

