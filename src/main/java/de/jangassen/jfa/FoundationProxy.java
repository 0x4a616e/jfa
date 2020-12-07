package de.jangassen.jfa;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.PointerByReference;
import de.jangassen.jfa.annotation.Superclass;
import de.jangassen.jfa.appkit.NSInvocation;
import de.jangassen.jfa.appkit.NSMethodSignature;
import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

@Superclass("NSProxy")
public class FoundationProxy {
  private final NSObject target;
  private final FoundationProxyHandler handler;
  private final Pointer respondsToSelector;

  public FoundationProxy(NSObject target, FoundationProxyHandler handler) {
    this.target = target;
    this.handler = handler;

    respondsToSelector = Foundation.createSelector("respondsToSelector:");
  }

  @SuppressWarnings("unused")
  public void forwardInvocation(NSInvocation invocation) {
    if (handler.hasAdditionalMethods()) {
      Pointer selector = new Pointer(invocation.selector().longValue());
      if (respondsToSelector.equals(selector)) {
        PointerByReference ref = new PointerByReference();
        invocation.getArgument(ref, 2);
        if (handler.hasAdditionalMethod(ref.getValue())) {
          ByteByReference result = new ByteByReference((byte) 1);
          invocation.setReturnValue(result);
          return;
        }
      } else {
        FoundationMethod additionalMethod = handler.getAdditionalMethod(selector);
        if (additionalMethod != null) {
          additionalMethod.invoke(invocation);
          return;
        }
      }
    }

    if (handler.beforeTarget(invocation)) {
      invocation.invokeWithTarget(target);
      handler.afterTarget(invocation);
    }
  }

  @SuppressWarnings("unused")
  public NSMethodSignature methodSignatureForSelector(ID sel) {
    if (handler.hasAdditionalMethods()) {
      FoundationMethod additionalMethod = handler.getAdditionalMethod(new Pointer(sel.longValue()));
      if (additionalMethod != null) {
        return additionalMethod.getMethodSignature();
      }
    }
    return target.methodSignatureForSelector(sel);
  }
}
