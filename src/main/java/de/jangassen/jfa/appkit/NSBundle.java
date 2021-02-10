package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

public interface NSBundle extends NSObject {
  static NSBundle mainBundle() {
    return ObjcToJava.invokeStatic(NSBundle.class, "mainBundle");
  }

  static NSBundle bundleWithPath(String path) {
    return ObjcToJava.invokeStatic(NSBundle.class, "bundleWithPath:", Foundation.nsString(path));
  }

  NSString bundleIdentifier();

  NSBundle initWithURL(NSURL url);

  NSBundle initWithPath(String url);

  NSURL bundleURL();

  String bundlePath();

  ID classNamed(String className);

  boolean load();
}
