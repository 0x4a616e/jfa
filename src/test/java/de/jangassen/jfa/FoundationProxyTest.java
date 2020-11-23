package de.jangassen.jfa;

import de.jangassen.jfa.appkit.NSMenu;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FoundationProxyTest {

    @Test
    void testProxy() {
        NSMenu menu = NSMenu.alloc().initWithTitle("test");

        FoundationProxy foundationProxy = new FoundationProxy(menu);
        ID map = JavaToObjc.map(foundationProxy);

        ID titleId = Foundation.invoke(map, "title");
        String title = Foundation.toStringViaUTF8(titleId);

        Assertions.assertEquals("test", title);
    }
}
