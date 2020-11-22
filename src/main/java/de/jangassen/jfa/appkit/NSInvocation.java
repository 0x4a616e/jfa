package de.jangassen.jfa.appkit;

public interface NSInvocation extends NSObject{
  NSObject target();

  void setTarget(NSObject target);

  void invoke();

  void invokeWithTarget(NSObject target);
}
