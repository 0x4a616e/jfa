package de.jangassen.jfa.appkit;

import de.jangassen.jfa.annotation.Protocol;

@Protocol
public interface NSApplicationDelegate extends NSObject {
  NSMenu applicationDockMenu(NSApplication sender);
}
