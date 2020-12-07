package de.jangassen.jfa.appkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NSStringTest {

  @Test
  void testNSString() {
    NSString nsString = NSString.alloc().initWithString("test");

    Assertions.assertEquals("test", NSString.toString(nsString));
  }
}