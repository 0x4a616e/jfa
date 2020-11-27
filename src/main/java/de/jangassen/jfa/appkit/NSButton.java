package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

public interface NSButton extends NSObject {
  static NSButton alloc() {
    return ObjcToJava.alloc(NSButton.class);
  }

  NSImage image();

  void setImage(NSImage image);
}
