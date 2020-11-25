package de.jangassen.jfa;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import de.jangassen.jfa.appkit.NSMenu;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FoundationProxyTest {

  @Test
  void testProxyStringMethod() throws NoSuchMethodException {
    NSMenu menu = NSMenu.alloc().initWithTitle("test");

    FoundationProxyHandler foundationProxyHandler = new FoundationProxyHandler();
    foundationProxyHandler.addBeforeMethodHook(NSMenu.class.getMethod("title"), (invocation -> {
      ID bla = Foundation.nsString("bla");
      invocation.setReturnValue(new PointerByReference(new Pointer(bla.longValue())));
      return false;
    }));

    FoundationProxy foundationProxy = new FoundationProxy(menu, foundationProxyHandler);
    ID map = JavaToObjc.map(foundationProxy);

    ID titleId = Foundation.invoke(map, "title");
    String title = Foundation.toStringViaUTF8(titleId);

    Assertions.assertEquals("bla", title);
  }

  @Test
  void testProxyLongMethod() throws NoSuchMethodException {
    long longValue = 10L;
    NSMenu menu = NSMenu.alloc().initWithTitle("test");

    FoundationProxyHandler foundationProxyHandler = new FoundationProxyHandler();
    foundationProxyHandler.addBeforeMethodHook(NSMenu.class.getMethod("numberOfItems"), (invocation -> {
      invocation.setReturnValue(new LongByReference(longValue));
      return false;
    }));

    ID map = JavaToObjc.map(new FoundationProxy(menu, foundationProxyHandler));
    ID titleId = Foundation.invoke(map, "numberOfItems");
    Assertions.assertEquals(longValue, titleId.longValue());
  }
}
