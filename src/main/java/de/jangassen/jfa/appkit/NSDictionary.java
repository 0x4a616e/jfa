package de.jangassen.jfa.appkit;

import de.jangassen.jfa.ObjcToJava;
import de.jangassen.jfa.annotation.NamedArg;
import de.jangassen.jfa.foundation.ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public interface NSDictionary<K, V> extends NSObject {
  @SuppressWarnings("unchecked")
  static <A, B> NSDictionary<A, B> alloc() {
    return ObjcToJava.alloc(NSDictionary.class);
  }

  @SuppressWarnings("unchecked")
  static <A, B> NSDictionary<A, B> of(Map<A, B> map) {
    List<Map.Entry<A, B>> entries = new ArrayList<>(map.entrySet());
    List<A> keys = entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    List<B> values = entries.stream().map(Map.Entry::getValue).collect(Collectors.toList());

    return ObjcToJava.alloc(NSDictionary.class).initWithObjects(NSArray.of(values), NSArray.of(keys));
  }

  int count();

  ID objectForKey(K aKey);

  NSDictionary<K, V> initWithObjects(NSArray<V> objects, @NamedArg("forKeys") NSArray<K> keys);
}
