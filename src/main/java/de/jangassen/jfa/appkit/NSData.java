package de.jangassen.jfa.appkit;

import com.sun.jna.Memory;
import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.annotation.NamedArg;

@SuppressWarnings("unused")
public interface NSData extends NSObject {
  static NSData alloc() {
    return ObjcToJava.alloc(NSData.class);
  }

  NSData initWithBytes(Memory bytes, @NamedArg("length") int length);

  NSData initWithData(NSData data);

  String base64EncodedStringWithOptions(int options);

  int length();
}
