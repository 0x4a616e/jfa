package de.jangassen.jfa.appkit;

import org.junit.Assert;
import org.junit.Test;

public class NSMenuItemTest {

  @Test
  public void createMenuItem() {
    NSMenuItem item = NSMenuItem.alloc();

    Assert.assertNotNull(item);
    Assert.assertFalse(item.hasSubmenu());
  }

  @Test
  public void initMenuItem() {
    NSMenuItem item = NSMenuItem.alloc().initWithTitle("Test", null, "");

    Assert.assertNotNull(item);
    Assert.assertEquals("Test", item.title());
  }

  @Test
  public void addSubmenu() {
    NSMenuItem item = NSMenuItem.alloc().initWithTitle("Test", null, "");
    NSMenu submenu = NSMenu.alloc().initWithTitle("test");
    item.setSubmenu(submenu);

    Assert.assertNotNull(item);
    Assert.assertTrue(item.hasSubmenu());
  }
}
