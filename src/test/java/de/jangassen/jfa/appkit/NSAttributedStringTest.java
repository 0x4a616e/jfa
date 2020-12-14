package de.jangassen.jfa.appkit;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NSAttributedStringTest {

  @Test
  void testNSAttributedString() {
    NSAttributedString test = NSAttributedString.alloc().initWithString(NSString.of("test"));

    assertEquals(4, test.length());
    assertEquals("test", NSString.toString(test.string()));
  }

  @Test
  void testNSAttributedStringWithAttributes() {
    NSDictionary<NSAttributedStringKey, NSNumber> attributes = NSDictionary.of(Map.of(
            NSAttributedStringKey.NSStrokeWidthAttributeName, NSNumber.alloc().initWithInt(1))
    );

    NSAttributedString test = NSAttributedString.alloc().initWithString(NSString.of("test"), attributes);

    assertEquals(4, test.length());
    assertEquals("test", NSString.toString(test.string()));
  }
}