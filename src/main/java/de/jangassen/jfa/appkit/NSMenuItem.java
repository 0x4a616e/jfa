package de.jangassen.jfa.appkit;


import com.sun.jna.Pointer;
import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.foundation.ID;

@SuppressWarnings("unused")
public interface NSMenuItem extends NSObject {

  static NSMenuItem alloc() {
    return ObjcToJava.alloc(NSMenuItem.class);
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

  String keyEquivalent();

  void setKeyEquivalent(String keyEquivalent);

  int keyEquivalentModifierMask();

  void setKeyEquivalentModifierMask(int flags);

  NSImage image();

  void setImage(NSImage image);

  NSMenuItem initWithTitle(@NamedArg("title") String text, @NamedArg("action") Pointer action, @NamedArg("keyEquivalent") String toKeyEquivalentString);
}
