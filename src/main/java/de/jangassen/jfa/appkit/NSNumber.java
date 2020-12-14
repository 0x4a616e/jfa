package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

public interface NSNumber extends NSObject {
  static NSNumber alloc() {
    return ObjcToJava.alloc(NSNumber.class);
  }

  NSNumber initWithChar(char value);

  NSNumber initWithShort(short value);

  NSNumber initWithInt(int value);

  NSNumber initWithLong(long value);

  NSNumber initWithFloat(float value);

  NSNumber initWithDouble(double value);

  NSNumber initWithBool(boolean value);

  NSNumber initWithInteger(NSInteger value);
}
