package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

public interface NSNotification extends NSObject {
  static NSNotification alloc() {
    return ObjcToJava.alloc(NSNotification.class);
  }

  String name();
}
