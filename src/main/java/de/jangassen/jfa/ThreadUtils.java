package de.jangassen.jfa;

import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

@SuppressWarnings("unused")
public final class ThreadUtils {
  private ThreadUtils() {
  }

  public static void dispatch_sync(Runnable runnable) {
    dispatch(runnable, true);
  }

  public static void dispatch_async(Runnable runnable) {
    dispatch(runnable, false);
  }

  private static void dispatch(Runnable runnable, boolean waitUntilDone) {
    ID objcObject = JavaToObjc.map(runnable, Runnable.class);

    Foundation.invoke(objcObject, "performSelectorOnMainThread:withObject:waitUntilDone:", Foundation.createSelector("run"), ID.NIL, waitUntilDone);
  }
}
