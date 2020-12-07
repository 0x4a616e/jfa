package de.jangassen.jfa;

public class NamedType {
  private final Class<?> type;
  private final String name;

  public NamedType(Class<?> type, String name) {
    this.type = type;
    this.name = name;
  }

  public Class<?> getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
