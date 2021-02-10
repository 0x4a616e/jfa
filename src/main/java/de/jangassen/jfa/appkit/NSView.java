package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSView extends NSObject {
  static NSView alloc() {
    return ObjcToJava.alloc(NSView.class);
  }

}
