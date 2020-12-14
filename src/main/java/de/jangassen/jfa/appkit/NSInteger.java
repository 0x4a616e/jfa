package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

public interface NSInteger extends NSNumber {
  static NSInteger alloc() {
    return ObjcToJava.alloc(NSInteger.class);
  }
}
