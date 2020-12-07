package de.jangassen.jfa;

import com.sun.jna.Pointer;
import de.jangassen.jfa.appkit.NSInvocation;
import de.jangassen.jfa.foundation.ID;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FoundationProxyHandler {
  private final Map<Pointer, Function<NSInvocation, Boolean>> beforeMethodHooks = new HashMap<>();
  private final Map<Pointer, Consumer<NSInvocation>> afterMethodHooks = new HashMap<>();
  private final Map<Pointer, FoundationMethod> additionalMethods = new HashMap<>();

  public void addMethod(FoundationMethod handler) {
    additionalMethods.put(handler.getSelector(), handler);
  }

  FoundationMethod getAdditionalMethod(Pointer selector) {
    return additionalMethods.get(selector);
  }

  boolean hasAdditionalMethods() {
    return !additionalMethods.isEmpty();
  }

  boolean hasAdditionalMethod(Pointer selector) {
    return additionalMethods.containsKey(selector);
  }

  public void addBeforeMethodHook(Method method, Function<NSInvocation, Boolean> handler) {
    addBeforeMethodHook(handler, Selector.forMethod(method));
  }

  public void addBeforeMethodHook(String selector, Function<NSInvocation, Boolean> handler) {
    addBeforeMethodHook(handler, Selector.forString(selector));
  }

  private void addBeforeMethodHook(Function<NSInvocation, Boolean> handler, Pointer key) {
    beforeMethodHooks.put(key, handler);
  }

  public void addAfterMethodHook(Method method, Consumer<NSInvocation> handler) {
    addAfterMethodHook(handler, Selector.forMethod(method));
  }

  public void addAfterMethodHook(String selector, Consumer<NSInvocation> handler) {
    addAfterMethodHook(handler, Selector.forString(selector));
  }

  private void addAfterMethodHook(Consumer<NSInvocation> handler, Pointer key) {
    afterMethodHooks.put(key, handler);
  }

  public boolean beforeTarget(NSInvocation invocation) {
    Function<NSInvocation, Boolean> nsInvocationBooleanFunction = beforeMethodHooks.get(getPointer(invocation));
    if (nsInvocationBooleanFunction != null) {
      return nsInvocationBooleanFunction.apply(invocation);
    }

    return true;
  }

  public void afterTarget(NSInvocation invocation) {
    Consumer<NSInvocation> nsInvocationBooleanFunction = afterMethodHooks.get(getPointer(invocation));
    if (nsInvocationBooleanFunction != null) {
      nsInvocationBooleanFunction.accept(invocation);
    }
  }

  private Pointer getPointer(NSInvocation invocation) {
    ID selector = invocation.selector();
    return new Pointer(selector.longValue());
  }
}
