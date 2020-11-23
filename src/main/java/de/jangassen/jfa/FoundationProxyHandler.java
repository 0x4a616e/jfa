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

  public void addBeforeMethodHook(Method method, Function<NSInvocation, Boolean> handler) {
    beforeMethodHooks.put(Selector.forMethod(method), handler);
  }

  public void addAfterMethodHook(Method method, Consumer<NSInvocation> handler) {
    afterMethodHooks.put(Selector.forMethod(method), handler);
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
