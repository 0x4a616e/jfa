package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSURL extends NSObject {
  static NSURL alloc() {
    return ObjcToJava.alloc(NSURL.class);
  }

  NSURL initWithString(String url);
}
