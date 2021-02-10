package de.jangassen.jfa.appkit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NSDistributedNotificationCenterTest {

  @Test
  void testDefaultNotificationCenter() {
    NSDistributedNotificationCenter nsDistributedNotificationCenter = NSDistributedNotificationCenter.defaultCenter();

    assertNotNull(nsDistributedNotificationCenter);
  }
}