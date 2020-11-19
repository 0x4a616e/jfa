package de.jangassen.jfa.appkit;


import de.jangassen.jfa.FoundationProxy;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;
import static de.jangassen.jfa.foundation.Foundation.invoke;

public interface NSApplication extends NSObject {
    static NSApplication sharedApplication() {
        return FoundationProxy.wrap(invoke(getObjcClass("NSApplication"), "sharedApplication"), NSApplication.class);
    }

    Object isActive();

    NSMenu mainMenu();

    void setMainMenu(NSMenu mainMenu);

    void hide();

    void hideOtherApplications();

    void unhideAllApplications();
}