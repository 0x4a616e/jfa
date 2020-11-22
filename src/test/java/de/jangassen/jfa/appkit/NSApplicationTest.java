package de.jangassen.jfa.appkit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NSApplicationTest {
  @Test
  public void getSharedApplication() {
    NSApplication app = NSApplication.sharedApplication();

    Assertions.assertNotNull(app);
  }
}
