package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSImage extends NSObject {
  static NSImage alloc() {
    return ObjcToJava.alloc(NSImage.class);
  }

  NSImage initByReferencingFile(String fileName);

  NSImage initWithContentsOfFile(String fileName);

  NSImage initByReferencingURL(NSURL url);

  NSImage initWithContentsOfURL(NSURL url);

  NSImage initWithData(NSData data);
}
