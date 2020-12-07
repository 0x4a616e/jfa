package de.jangassen.jfa.appkit;

import com.sun.jna.Pointer;
import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSString extends NSObject {
  static NSString alloc() {
    return ObjcToJava.alloc(NSString.class);
  }

  static NSString of(String value) {
    return alloc().initWithString(value);
  }

  static String toString(NSString nsString) {
    Pointer pointer = nsString.UTF8String();
    return pointer.getString(0);
  }

  NSString init();

  NSString initWithString(String value);

  int length();

  Pointer UTF8String();
}
