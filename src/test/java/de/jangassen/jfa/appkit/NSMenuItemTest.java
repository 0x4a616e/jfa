package de.jangassen.jfa.appkit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NSMenuItemTest {

  @Test
  public void createMenuItem() {
    NSMenuItem item = NSMenuItem.alloc();

    Assertions.assertNotNull(item);
    Assertions.assertFalse(item.hasSubmenu());
  }

  @Test
  public void initMenuItem() {
    NSMenuItem item = NSMenuItem.alloc().initWithTitle("Test", null, "");

    Assertions.assertNotNull(item);
    Assertions.assertEquals("Test", item.title());
  }

  @Test
  public void addSubmenu() {
    NSMenuItem item = NSMenuItem.alloc().initWithTitle("Test", null, "");
    NSMenu submenu = NSMenu.alloc().initWithTitle("test");
    item.setSubmenu(submenu);

    Assertions.assertNotNull(item);
    Assertions.assertTrue(item.hasSubmenu());
  }
}
