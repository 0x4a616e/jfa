package de.jangassen.jfa.appkit;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import de.jangassen.jfa.ObjcToJava;

@SuppressWarnings("unused")
public interface NSMethodSignature extends NSObject {
  static NSMethodSignature alloc() {
    return ObjcToJava.alloc(NSMethodSignature.class);
  }

  static NSMethodSignature signatureWithObjCTypes(String cTypes) {
    Pointer cTypesString = new Memory(cTypes.length() + 1);
    cTypesString.setString(0, cTypes);
    return ObjcToJava.invokeStatic(NSMethodSignature.class, "signatureWithObjCTypes:", cTypesString);
  }

  String methodReturnType();

  int methodReturnLength();

  int numberOfArguments();

  Pointer getArgumentTypeAtIndex(int idx);
}
