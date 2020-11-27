package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;

import static de.jangassen.jfa.foundation.Foundation.*;

public interface NSAppearance extends NSObject {
  enum NSAppearanceName {
    NSAppearanceNameAqua("NSAppearanceNameAqua"),
    NSAppearanceNameDarkAqua("NSAppearanceNameDarkAqua"),
    NSAppearanceNameVibrantLight("NSAppearanceNameVibrantLight"),
    NSAppearanceNameVibrantDark("NSAppearanceNameVibrantDark"),
    NSAppearanceNameAccessibilityHighContrastAqua("NSAppearanceNameAccessibilityHighContrastAqua"),
    NSAppearanceNameAccessibilityHighContrastDarkAqua("NSAppearanceNameAccessibilityHighContrastDarkAqua"),
    NSAppearanceNameAccessibilityHighContrastVibrantLight("NSAppearanceNameAccessibilityHighContrastVibrantLight"),
    NSAppearanceNameAccessibilityHighContrastVibrantDark("NSAppearanceNameAccessibilityHighContrastVibrantDark");

    private String name;

    NSAppearanceName(String name) {
      NSAppearanceName.this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  static NSAppearance appearanceNamed(NSAppearanceName appearanceName) {
    return ObjcToJava.map(invoke(getObjcClass("NSAppearance"), "appearanceNamed:", nsString(appearanceName.getName())), NSAppearance.class);
  }

  String name();
}
