package de.jangassen.jfa.appkit;


import com.sun.jna.Pointer;
import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSObject {
  static NSObject alloc() {
    return ObjcToJava.alloc(NSObject.class);
  }

  String description();

  String className();

  void dealloc();

  NSMethodSignature methodSignatureForSelector(Pointer selector);
}
