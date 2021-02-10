package de.jangassen.jfa.foundation;

import java.util.Arrays;
import java.util.Collection;

public class VarArgs<T> {
  private final Collection<T> args;

  private VarArgs(Collection<T> args) {
    this.args = args;
  }

  public static <A> VarArgs<A> of(A... values) {
    return of(Arrays.asList(values));
  }

  public static <A> VarArgs<A> of(Collection<A> values) {
    return new VarArgs<>(values);
  }

  public Collection<T> getArgs() {
    return args;
  }
}
