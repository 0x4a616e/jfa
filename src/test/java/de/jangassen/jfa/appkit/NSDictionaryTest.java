package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class NSDictionaryTest {

  @Test
  void testNSDictionary() {
    NSString key = NSString.of("key");
    NSString value = NSString.of("value");

    NSDictionary<NSString, NSString> dict = NSDictionary.<NSString, NSString>alloc()
            .initWithObjects(NSArray.of(value),
                    NSArray.of(key));

    ID id = dict.objectForKey(key);

    NSString nsString = ObjcToJava.map(id, NSString.class);
    Assertions.assertEquals("value", NSString.toString(nsString));
  }

  @Test
  void testNSDictionaryForMap() {
    NSString key = NSString.of("key");
    NSString value = NSString.of("value");

    NSDictionary<NSString, NSString> dict = NSDictionary.of(Map.of(key, value));
    ID id = dict.objectForKey(key);

    NSString nsString = ObjcToJava.map(id, NSString.class);
    Assertions.assertEquals("value", NSString.toString(nsString));
  }

  @Test
  void testNSDictionaryWithEnum() {
    NSAboutPanelOptionKey key = NSAboutPanelOptionKey.NSAboutPanelOptionApplicationIcon;
    NSString value = NSString.of("value");

    NSDictionary<NSAboutPanelOptionKey, NSString> dict = NSDictionary.of(Map.of(key, value));
    ID id = dict.objectForKey(key);

    NSString nsString = ObjcToJava.map(id, NSString.class);
    Assertions.assertEquals("value", NSString.toString(nsString));
  }
}