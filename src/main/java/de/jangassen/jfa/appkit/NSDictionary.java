package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.foundation.ID;

import java.util.Map;

@SuppressWarnings("unused")
public interface NSDictionary<K, V> extends NSObject {
  @SuppressWarnings("unchecked")
  static <A, B> NSDictionary<A, B> alloc() {
    return ObjcToJava.alloc(NSDictionary.class);
  }

  @SuppressWarnings("unchecked")
  static <A, B> NSDictionary<A, B> of(Map<A, B> map) {
    return ObjcToJava.alloc(NSDictionary.class).initWithObjects(NSArray.of(map.values()), NSArray.of(map.keySet()));
  }

  int count();

  ID objectForKey(K aKey);

  NSDictionary<K, V> initWithObjects(NSArray<V> objects, @NamedArg("forKeys") NSArray<K> keys);
}
