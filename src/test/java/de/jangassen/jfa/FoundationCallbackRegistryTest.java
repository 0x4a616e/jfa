package de.jangassen.jfa;

import de.jangassen.jfa.foundation.Foundation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

class FoundationCallbackRegistryTest {

  @Test
  void testCallback() {
    AtomicBoolean wasCalled = new AtomicBoolean(false);

    FoundationCallback foundationCallback = FoundationCallbackRegistry.registerCallback(id -> wasCalled.set(true));
    Foundation.invoke(foundationCallback.getTarget(), foundationCallback.getSelector());
    FoundationCallbackRegistry.unregister(foundationCallback);

    Assertions.assertTrue(wasCalled.get());
  }
}