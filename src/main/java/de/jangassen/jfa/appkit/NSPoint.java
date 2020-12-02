package de.jangassen.jfa.appkit;

import com.sun.jna.Structure;
import de.jangassen.jfa.foundation.Foundation;

@Structure.FieldOrder({"x", "y"})
@SuppressWarnings("unused")
public class NSPoint extends Structure implements Structure.ByValue {
  public Foundation.CGFloat x;
  public Foundation.CGFloat y;
}
