package de.jangassen.jfa.appkit;


import com.sun.jna.Memory;
import de.jangassen.jfa.Selector;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

class NSObjectTest {

  @Test
  void testNSObjectSelectors() {
    assertClassSelectors(NSObject.class);
  }

  @Test
  void testNSMenuSelectors() {
    assertClassSelectors(NSMenu.class);
  }

  @Test
  void testNSMenuItemSelectors() {
    assertClassSelectors(NSMenuItem.class);
  }

  @Test
  void testNSApplicationSelectors() {
    assertClassSelectors(NSApplication.class);
  }

  @Test
  void testNSWorkspaceSelectors() {
    assertClassSelectors(NSWorkspace.class);
  }

  @Test
  void testNSInvocationSelectors() {
    assertClassSelectors(NSInvocation.class);
  }

  @Test
  void testNSImageSelectors() {
    assertClassSelectors(NSImage.class);
  }

  @Test
  void testNSURLSelectors() {
    assertClassSelectors(NSURL.class);
  }

  @Test
  void testNSDataSelectors() {
    assertClassSelectors(NSData.class);
  }

  @Test
  void testNSMethodSignatureSelectors() {
    assertClassSelectors(NSMethodSignature.class);
  }

  @Test
  void testNSNotificationSelectors() {
    assertClassSelectors(NSNotification.class);
  }

  @Test
  void testNSViewSelectors() {
    assertClassSelectors(NSView.class);
  }

  @Test
  void testNSWindowSelectors() {
    assertClassSelectors(NSWindow.class);
  }

  @Test
  void testNSData() {
    byte[] arr = "test".getBytes();
    Memory ptr = new Memory(arr.length);
    ptr.write(0, arr, 0, arr.length);

    NSData data = NSData.alloc().initWithBytes(ptr, arr.length);

    String base64 = data.base64EncodedStringWithOptions(0);

    Assertions.assertEquals(arr.length, data.length());
    Assertions.assertEquals("dGVzdA==", base64);
  }

  private static void assertClassSelectors(Class<? extends NSObject> nsClass) {
    assertClassSelectors(nsClass, Foundation.getObjcClass(nsClass.getSimpleName()));
  }

  private static void assertClassSelectors(Class<? extends NSObject> nsClass, ID clazzId) {
    Arrays.stream(nsClass.getDeclaredMethods())
            .filter(method -> !Modifier.isStatic(method.getModifiers()))
            .forEach(method -> Assertions.assertTrue(respondsToSelector(clazzId, Selector.forMethod(method)), nsClass.getSimpleName() + " does not respond to " + method.getName()));
  }

  private static boolean respondsToSelector(ID instance, com.sun.jna.Pointer selector) {
    return Foundation.invoke(instance, "instancesRespondToSelector:", selector).booleanValue();
  }
}