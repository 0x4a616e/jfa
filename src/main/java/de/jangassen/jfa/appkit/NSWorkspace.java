package de.jangassen.jfa.appkit;


import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSWorkspace extends NSObject {
  static NSWorkspace sharedWorkspace() {
    return ObjcToJava.invokeStatic(NSWorkspace.class, "sharedWorkspace");
  }

  void hideOtherApplications();
}