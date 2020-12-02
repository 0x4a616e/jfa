package de.jangassen.jfa;

import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class FoundationCallbackRegistry {

  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  private static final Map<ID, Object> REFERENCE_MAP = new ConcurrentHashMap<>();

  public static FoundationCallback registerCallback(Consumer<ID> callback) {
    ID objcObject = JavaToObjc.map(callback, Consumer.class);
    REFERENCE_MAP.put(objcObject, callback);
    return new FoundationCallback(objcObject, Foundation.createSelector("accept:"));
  }

  public static void unregister(FoundationCallback callback) {
    REFERENCE_MAP.remove(callback.getTarget());
  }
}
