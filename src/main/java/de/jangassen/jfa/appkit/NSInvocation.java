package de.jangassen.jfa.appkit;

import com.sun.jna.Pointer;
import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.foundation.ID;

public interface NSInvocation extends NSObject {
  ID selector();

  NSObject target();

  void setTarget(NSObject target);

  void invoke();

  void invokeWithTarget(NSObject target);

  void setReturnValue(Pointer retLoc);

  void getArgument(ID argumentLocation, @NamedArg("atIndex") int idx);
}
