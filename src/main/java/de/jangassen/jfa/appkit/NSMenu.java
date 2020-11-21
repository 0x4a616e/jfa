package de.jangassen.jfa.appkit;


import de.jangassen.jfa.FoundationProxy;
import de.jangassen.jfa.annotation.NamedArg;

public interface NSMenu extends NSObject {

    static NSMenu alloc() {
        return FoundationProxy.alloc(NSMenu.class);
    }

    String title();

    NSMenu init();

    NSMenu initWithTitle(String title);

    void setTitle(String title);

    void removeAllItems();

    void removeItem(NSMenuItem item);

    void removeItemAtIndex(int index);

    void addItem(NSMenuItem item);

    void insertItem(NSMenuItem item, @NamedArg("atIndex") int index);

    NSMenuItem itemAtIndex(int index);
    
    long numberOfItems();
}
