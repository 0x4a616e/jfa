package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSWindow extends NSObject {
  static NSWindow alloc() {
    return ObjcToJava.alloc(NSWindow.class);
  }

  NSView contentView();

  NSAppearance appearance();

  void setAppearance(NSAppearance appearance);
}
