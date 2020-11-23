package de.jangassen.jfa;

import de.jangassen.jfa.annotation.Superclass;
import de.jangassen.jfa.appkit.NSInvocation;
import de.jangassen.jfa.appkit.NSMethodSignature;
import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.ID;

@Superclass("NSProxy")
public class FoundationProxy {
  private final NSObject target;
  private final FoundationProxyHandler handler;

  public FoundationProxy(NSObject target, FoundationProxyHandler handler) {
    this.target = target;
    this.handler = handler;
  }

  public void forwardInvocation(NSInvocation invocation) {
    if (handler.beforeTarget(invocation)) {
      invocation.invokeWithTarget(target);
      handler.afterTarget(invocation);
    }
  }

  public NSMethodSignature methodSignatureForSelector(ID sel) {
    return target.methodSignatureForSelector(sel);
  }
}
