package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.annotation.NamedArg;

public interface NSAttributedString extends NSObject {
  static NSAttributedString alloc() {
    return ObjcToJava.alloc(NSAttributedString.class);
  }

  NSAttributedString initWithString(NSString str);

  NSAttributedString initWithString(NSString str, @NamedArg("attributes") NSDictionary<NSAttributedStringKey, ? extends NSObject> attrs);

  NSString string();

  int length();
}
