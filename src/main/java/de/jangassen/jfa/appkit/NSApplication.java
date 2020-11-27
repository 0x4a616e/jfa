package de.jangassen.jfa.appkit;


import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.ID;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;
import static de.jangassen.jfa.foundation.Foundation.invoke;

@SuppressWarnings("unused")
public interface NSApplication extends NSObject {
    static NSApplication sharedApplication() {
        return ObjcToJava.map(invoke(getObjcClass("NSApplication"), "sharedApplication"), NSApplication.class);
    }

    Object isActive();

    NSMenu mainMenu();

    void setMainMenu(NSMenu mainMenu);

    void hide(ID sender);

    void unhide(ID sender);

    NSApplicationDelegate delegate();

    void setDelegate(NSApplicationDelegate delegate);

    NSWindow keyWindow();

    NSWindow mainWindow();

    NSAppearance appearance();

    void setAppearance(NSAppearance appearance);
}