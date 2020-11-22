package de.jangassen.jfa;

import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FoundationClassMapperTest {

  @Test
  void defineClass() {
    TestClass testClass = new TestClass();
    ID instance = FoundationClassMapper.createInstance(testClass);
    Foundation.invoke(instance, "testMethod");

    Assertions.assertTrue(testClass.wasInvoked);
  }

  static class TestClass {
    boolean wasInvoked;

    public void testMethod() {
      wasInvoked = true;
    }
  }
}