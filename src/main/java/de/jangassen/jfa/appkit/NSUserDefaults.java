package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSUserDefaults extends NSObject {
  String AppleInterfaceStyle = "AppleInterfaceStyle";

  static NSUserDefaults standardUserDefaults() {
    return ObjcToJava.invokeStatic(NSUserDefaults.class, "standardUserDefaults");
  }

  String objectForKey(String key);
}
