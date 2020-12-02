package de.jangassen.jfa.appkit;


import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.foundation.ID;

@SuppressWarnings({"unused", "EmptyMethod"})
public interface NSObject {
  static NSObject alloc() {
    return ObjcToJava.alloc(NSObject.class);
  }

  String description();

  String className();

  void dealloc();

  NSMethodSignature methodSignatureForSelector(ID selector);

  boolean respondsToSelector(ID aSelector);

  void performSelectorOnMainThread(ID aSelector, @NamedArg("withObject") ID arg, @NamedArg("waitUntilDone") boolean wait);
}
