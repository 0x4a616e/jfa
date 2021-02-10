package de.jangassen.jfa.appkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NSBundleTest {
  @Test
  void testGetBundle() {
    NSBundle nsBundle = NSBundle.mainBundle();

    Assertions.assertNotNull(nsBundle);
  }
}