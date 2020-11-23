package de.jangassen.jfa.appkit;

import de.jangassen.jfa.foundation.ID;

public interface NSProxy extends NSObject {
  void forwardInvocation(NSInvocation invocation);

  NSMethodSignature methodSignatureForSelector(ID sel);
}
