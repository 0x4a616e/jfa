package de.jangassen.jfa;

import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaToObjcTest {

  @Test
  void defineClass() {
    TestClass testClass = new TestClass();
    ID instance = JavaToObjc.map(testClass);
    Foundation.invoke(instance, "testMethod");

    Assertions.assertTrue(testClass.wasInvoked);
  }

}