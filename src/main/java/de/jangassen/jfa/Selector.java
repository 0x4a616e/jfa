package de.jangassen.jfa;

import com.sun.jna.Pointer;
import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.foundation.Foundation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Selector {
  public static Pointer forMethod(Method method) {
    return forString(Selector.stringForMethod(method));
  }

  public static Pointer forString(String selector) {
    return Foundation.createSelector(selector);
  }

  public static Pointer forNamedTypes(String methodName, NamedType... namedTypes) {
    return Selector.forString(stringForNamedTypes(methodName, namedTypes));
  }

  public static String stringForNamedTypes(String methodName, NamedType... namedTypes) {
    if (namedTypes.length == 0) {
      return methodName;
    }

    Stream<String> parameterNames = Arrays.stream(namedTypes)
            .skip(1)
            .map(NamedType::getName);

    return getSelector(methodName, parameterNames);
  }

  public static String stringForMethod(Method method) {
    if (method.getParameterCount() == 0) {
      return method.getName();
    }

    Stream<String> parameterNames = Arrays.stream(method.getParameters())
            .skip(1)
            .map(Selector::getParameterNames);

    return getSelector(method.getName(), parameterNames);
  }

  private static String getSelector(String methodName, Stream<String> parameterNames) {
    String selectorComponent = parameterNames
            .map(p -> p + ":")
            .collect(Collectors.joining());

    return methodName + ":" + selectorComponent;
  }

  private static String getParameterNames(Parameter p) {
    return Optional.ofNullable(p.getAnnotation(NamedArg.class))
            .map(NamedArg::value)
            .orElse("");
  }
}
