package de.jangassen.jfa.appkit;


import de.jangassen.jfa.FoundationProxy;

public interface NSMenu extends NSObject {

    static NSMenu alloc() {
        return FoundationProxy.alloc(NSMenu.class);
    }

    String title();

    NSMenu init(String title);

    void setTitle(String title);

    void removeAllItems();

    void removeItem(NSMenuItem item);

    void removeItemAtIndex(int index);

    void addItem(NSMenuItem item);

    void insertItem(NSMenuItem item, int index);

    NSMenuItem itemAtIndex(int index);

    long numberOfItems();
}
