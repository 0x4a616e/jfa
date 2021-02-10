package de.jangassen.jfa;

import com.sun.jna.Pointer;
import de.jangassen.jfa.foundation.ID;

public class FoundationCallback {
  private final ID target;
  private final Pointer selector;

  public FoundationCallback(ID target, Pointer selector) {
    this.target = target;
    this.selector = selector;
  }

  public ID getTarget() {
    return target;
  }

  public Pointer getSelector() {
    return selector;
  }
}
