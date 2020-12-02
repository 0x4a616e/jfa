package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.ID;

@SuppressWarnings("unused")
public interface NSProxy extends NSObject {
  static NSProxy alloc() {
    return ObjcToJava.alloc(NSProxy.class);
  }

  void forwardInvocation(NSInvocation invocation);

  NSMethodSignature methodSignatureForSelector(ID sel);
}
