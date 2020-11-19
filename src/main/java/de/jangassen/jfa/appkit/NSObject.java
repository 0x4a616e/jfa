package de.jangassen.jfa.appkit;


import de.jangassen.jfa.FoundationProxy;

public interface NSObject {
    static NSObject alloc() {
        return FoundationProxy.alloc(NSObject.class);
    }

    String description();

    String className();

    void release();
}
