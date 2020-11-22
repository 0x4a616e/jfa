package de.jangassen.jfa.cleanup;

import de.jangassen.jfa.FoundationCallback;
import de.jangassen.jfa.FoundationCallbackRegistry;
import de.jangassen.jfa.appkit.NSObject;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

import java.lang.ref.Cleaner;

public final class NSCleaner {
  private NSCleaner() {}

  public static final Cleaner CLEANER = Cleaner.create();

  public static void register(Object obj, NSObject nsObject) {
    CLEANER.register(obj, nsObject::dealloc);
  }

  public static void register(Object obj, ID id) {
    CLEANER.register(obj, () -> Foundation.invoke(id, "dealloc"));
  }

  public static void register(Object obj, FoundationCallback callback) {
    CLEANER.register(obj, () -> FoundationCallbackRegistry.unregister(callback));
  }
}
