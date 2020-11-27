package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

public interface NSStatusBarButton extends NSButton {
  static NSStatusBarButton alloc() {
    return ObjcToJava.alloc(NSStatusBarButton.class);
  }
}
