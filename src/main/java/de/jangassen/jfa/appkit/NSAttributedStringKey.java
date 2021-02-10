package de.jangassen.jfa.appkit;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;
import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.foundation.ID;

@SuppressWarnings("unused")
public enum NSAttributedStringKey implements NativeMapped {
  NSFontAttributeName("NSFont"),
  NSParagraphStyleAttributeName("NSParagraphStyle"),
  NSForegroundColorAttributeName("NSForegroundColor"),
  NSBackgroundColorAttributeName("NSBackgroundColor"),
  NSLigatureAttributeName("NSLigature"),
  NSKernAttributeName("NSKern"),
  NSTrackingAttributeName("NSTracking"),
  NSStrikethroughStyleAttributeName("NSStrikethroughStyle"),
  NSUnderlineStyleAttributeName("NSUnderlineStyle"),
  NSStrokeColorAttributeName("NSStrokeColor"),
  NSStrokeWidthAttributeName("NSStrokeWidth"),
  NSShadowAttributeName("NSShadow"),
  NSTextEffectAttributeName("NSTextEffect"),
  NSAttachmentAttributeName("NSAttachment"),
  NSLinkAttributeName("NSLink"),
  NSBaselineOffsetAttributeName("NSBaselineOffset"),
  NSUnderlineColorAttributeName("NSUnderlineColor"),
  NSStrikethroughColorAttributeName("NSStrikethroughColor"),
  NSObliquenessAttributeName("NSObliqueness"),
  NSExpansionAttributeName("NSExpansion"),
  NSWritingDirectionAttributeName("NSWritingDirection"),
  NSVerticalGlyphFormAttributeName("NSVerticalGlyphForm"),
  NSCursorAttributeName("NSCursor"),
  NSToolTipAttributeName("NSToolTip"),
  NSMarkedClauseSegmentAttributeName("NSMarkedClauseSegment"),
  NSTextAlternativesAttributeName("NSTextAlternatives"),
  NSSpellingStateAttributeName("NSSpellingState"),
  NSSuperscriptAttributeName("NSSuperscript"),
  NSGlyphInfoAttributeName("NSGlyphInfo");

  private final String value;

  NSAttributedStringKey(String value) {
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
