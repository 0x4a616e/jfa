package de.jangassen.jfa.appkit;


import de.jangassen.jfa.ObjcToJava;

import static de.jangassen.jfa.foundation.Foundation.getObjcClass;
import static de.jangassen.jfa.foundation.Foundation.invoke;

@SuppressWarnings("unused")
public interface NSWorkspace extends NSObject {
    static NSWorkspace sharedWorkspace() {
        return ObjcToJava.map(invoke(getObjcClass("NSWorkspace"), "sharedWorkspace"), NSWorkspace.class);
    }

    void hideOtherApplications();
}