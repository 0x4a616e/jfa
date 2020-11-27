package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.Foundation;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;
import static de.jangassen.jfa.foundation.Foundation.invoke;

public interface NSStatusBar extends NSObject {
  Foundation.CGFloat NSSquareStatusItemLength = new Foundation.CGFloat(-2.0);

  static NSStatusBar systemStatusBar() {
    return ObjcToJava.map(invoke(getObjcClass("NSStatusBar"), "systemStatusBar"), NSStatusBar.class);
  }

  NSStatusItem statusItemWithLength(Foundation.CGFloat length);

  void removeStatusItem(NSStatusItem item);
}
