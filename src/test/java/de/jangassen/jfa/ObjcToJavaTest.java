package de.jangassen.jfa;

import de.jangassen.jfa.appkit.NSImage;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ObjcToJavaTest {

  @Test
  void testMapToObject() {
    ID instance = Foundation.invoke(Foundation.getObjcClass("NSImage"), "alloc");

    Optional<Class<?>> javaClass = ObjcToJava.getJavaClass(instance);

    Assertions.assertTrue(javaClass.isPresent());
    Assertions.assertEquals(NSImage.class, javaClass.get());
  }
}