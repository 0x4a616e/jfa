package de.jangassen.jfa.appkit;

import de.jangassen.jfa.annotation.Protocol;

@Protocol
@SuppressWarnings("unused")
public interface NSApplicationDelegate extends NSObject {
  NSMenu applicationDockMenu(NSApplication sender);

  void applicationWillBecomeActive(NSNotification notification);

  void applicationDidBecomeActive(NSNotification notification);

  void applicationWillFinishLaunching(NSNotification notification);

  void applicationDidFinishLaunching(NSNotification notification);

  void applicationWillUpdate(NSNotification notification);

  void applicationDidUpdate(NSNotification notification);
}
