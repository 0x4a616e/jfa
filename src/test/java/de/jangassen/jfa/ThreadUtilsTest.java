package de.jangassen.jfa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

class ThreadUtilsTest {

  @Test
  void testDispatchSync() {
    AtomicBoolean wasCalled = new AtomicBoolean(false);

    ThreadUtils.dispatch_sync(() -> {
      wasCalled.set(true);
    });

    Assertions.assertTrue(wasCalled.get());
  }
}