package de.jangassen.jfa;

import org.junit.jupiter.api.Test;

class FoundationCallbackRegistryTest {

  @Test
  void testCallback() {
    FoundationCallbackRegistry.registerCallback(System.out::println);
  }
}