package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;
import static de.jangassen.jfa.foundation.Foundation.invoke;

public interface NSUserDefaults extends NSObject {
  String AppleInterfaceStyle = "AppleInterfaceStyle";

  static NSUserDefaults standardUserDefaults() {
    return ObjcToJava.map(invoke(getObjcClass("NSUserDefaults"), "standardUserDefaults"), NSUserDefaults.class);
  }

  String objectForKey(String key);
}
