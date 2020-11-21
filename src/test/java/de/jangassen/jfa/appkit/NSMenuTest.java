package de.jangassen.jfa.appkit;

import org.junit.Assert;
import org.junit.Test;

public class NSMenuTest {
  @Test
  public void createMenu() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");

    Assert.assertNotNull(menu);
    Assert.assertEquals("title", menu.title());
  }

  @Test
  public void addMenuItem() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    NSMenuItem item = menu.itemAtIndex(0);

    Assert.assertEquals("title", menuItem.title());
    Assert.assertEquals(item, menuItem);
  }

  @Test
  public void insertMenuItem() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.insertItem(menuItem, 0);
    NSMenuItem item = menu.itemAtIndex(0);

    Assert.assertEquals("title", menuItem.title());
    Assert.assertEquals(item, menuItem);
  }

  @Test
  public void RemoveMenuItem() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    menu.removeItem(menuItem);

    Assert.assertEquals(0, menu.numberOfItems());
  }

  @Test
  public void removeMenuItemAtIndex() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    menu.removeItemAtIndex(0);

    Assert.assertEquals(0, menu.numberOfItems());
  }

  @Test
  public void removeAllItems() {
    NSMenu menu = NSMenu.alloc().initWithTitle("title");
    NSMenuItem menuItem = NSMenuItem.alloc().initWithTitle("title", null, "");

    menu.addItem(menuItem);
    menu.removeAllItems();

    Assert.assertEquals(0, menu.numberOfItems());
  }
}
