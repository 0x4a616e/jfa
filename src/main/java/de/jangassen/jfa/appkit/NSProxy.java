package de.jangassen.jfa.appkit;

import de.jangassen.jfa.foundation.ID;

public interface NSProxy {
  void forwardInvocation(NSInvocation invocation);

  NSMethodSignature methodSignatureForSelector(ID sel);
}
