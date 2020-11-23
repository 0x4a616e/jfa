package de.jangassen.jfa;

import de.jangassen.jfa.annotation.Superclass;
import de.jangassen.jfa.appkit.NSInvocation;
import de.jangassen.jfa.appkit.NSMethodSignature;
import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.ID;

@Superclass("NSProxy")
public class FoundationProxy {
  private final NSObject target;

  public FoundationProxy(NSObject target) {
    this.target = target;
  }

  public void forwardInvocation(NSInvocation invocation) {
    invocation.invokeWithTarget(target);
  }

  public NSMethodSignature methodSignatureForSelector(ID sel) {
    return target.methodSignatureForSelector(sel);
  }
}
