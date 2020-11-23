package de.jangassen.jfa.annotation;

import de.jangassen.jfa.appkit.NSObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Superclass {
  Class<? extends NSObject> value();
}
