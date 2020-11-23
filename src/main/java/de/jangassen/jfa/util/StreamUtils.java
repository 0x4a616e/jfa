package de.jangassen.jfa.util;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class StreamUtils {
  private StreamUtils() {
  }

  public static <T> Stream<IndexedValue<T>> zipWithIndex(T[] values) {
    return IntStream.range(0, values.length).mapToObj(i -> new IndexedValue<>(i, values[i]));
  }

  public static class IndexedValue<T> {
    private final int index;
    private final T value;

    public IndexedValue(int index, T value) {
      this.index = index;
      this.value = value;
    }

    public int getIndex() {
      return index;
    }

    public T getValue() {
      return value;
    }
  }
}
