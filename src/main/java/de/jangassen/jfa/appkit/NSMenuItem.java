package de.jangassen.jfa.appkit;


import com.sun.jna.Pointer;
import de.jangassen.jfa.FoundationProxy;
import de.jangassen.jfa.foundation.ID;
import de.jangassen.jfa.annotation.NamedArg;

@SuppressWarnings("unused")
public interface NSMenuItem extends NSObject {

    static NSMenuItem alloc() {
        return FoundationProxy.alloc(NSMenuItem.class);
    }

    String title();

    boolean hasSubmenu();

    void setSubmenu(NSMenu menu);

    NSMenu submenu();

    void setTitle(String title);

    void release();

    Pointer action();
    void setAction(Pointer selector);

    ID target();
    void setTarget(ID target);

    String tag();
    void setTag(String tag);

    NSMenuItem init();

    NSMenuItem initWithTitle(@NamedArg("title") String text, @NamedArg("action") Pointer action, @NamedArg("keyEquivalent") String toKeyEquivalentString);
}
