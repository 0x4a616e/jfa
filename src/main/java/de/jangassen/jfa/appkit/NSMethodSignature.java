package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSMethodSignature extends NSObject {
  static NSMethodSignature alloc() {
    return ObjcToJava.alloc(NSMethodSignature.class);
  }

  String methodReturnType();

  int methodReturnLength();
}
