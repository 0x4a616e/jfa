package de.jangassen.jfa.appkit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NSMenuTest {
  @Test
  public void createMenu() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");

    Assertions.assertNotNull(menu);
    Assertions.assertEquals("title", menu.title());
  }

  @Test
  public void addMenuItem() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    NSMenuItem item = menu.itemAtIndex(0);

    Assertions.assertEquals("title", menuItem.title());
    Assertions.assertEquals(item, menuItem);
  }

  @Test
  public void insertMenuItem() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.insertItem(menuItem, 0);
    NSMenuItem item = menu.itemAtIndex(0);

    Assertions.assertEquals("title", menuItem.title());
    Assertions.assertEquals(item, menuItem);
  }

  @Test
  public void RemoveMenuItem() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    menu.removeItem(menuItem);

    Assertions.assertEquals(0, menu.numberOfItems());
  }

  @Test
  public void removeMenuItemAtIndex() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    menu.removeItemAtIndex(0);

    Assertions.assertEquals(0, menu.numberOfItems());
  }

  @Test
  public void removeAllItems() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    menu.removeAllItems();

    Assertions.assertEquals(0, menu.numberOfItems());
  }
}
