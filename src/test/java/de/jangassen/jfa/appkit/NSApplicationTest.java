package de.jangassen.jfa.appkit;

import org.junit.Assert;
import org.junit.Test;

public class NSApplicationTest {
  @Test
  public void getSharedApplication() {
    NSApplication app = NSApplication.sharedApplication();

    Assert.assertNotNull(app);
  }
}
