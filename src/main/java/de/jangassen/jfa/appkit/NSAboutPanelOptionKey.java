package de.jangassen.jfa.appkit;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;
import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.ID;

@SuppressWarnings("unused")
public enum NSAboutPanelOptionKey implements NativeMapped {
  NSAboutPanelOptionApplicationIcon("ApplicationIcon"),
  NSAboutPanelOptionApplicationName("ApplicationName"),
  NSAboutPanelOptionApplicationVersion("ApplicationVersion"),
  NSAboutPanelOptionCredits("Credits"),
  NSAboutPanelOptionVersion("Version");

  private final String value;

  NSAboutPanelOptionKey(String value) {
    this.value = value;
  }

  @Override
  public Object fromNative(Object nativeValue, FromNativeContext context) {
    return ObjcToJava.map(new ID((Pointer) nativeValue), NSString.class);
  }

  @Override
  public Object toNative() {
    return ObjcToJava.toID(NSString.of(value)).toPointer();
  }

  @Override
  public Class<?> nativeType() {
    return NSString.class;
  }
}
