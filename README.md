# Java Foundation Access

[![Maven Central](https://img.shields.io/maven-central/v/de.jangassen/jfa.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.jangassen%22%20AND%20a:%22jfa%22)
![Test](https://github.com/0x4a616e/jfa/workflows/Test/badge.svg)

Java Foundation Access is a pure Java library for accessing Apples Foundation framework.

Access is done purely using JNA. Foundation classes are based on classes from IntelliJ Community Edition https://github.com/JetBrains/intellij-community 

# Usage

Add maven dependency

```xml
<dependency>
  <groupId>de.jangassen</groupId>
  <artifactId>jfa</artifactId>
  <version>1.1.1</version>
</dependency>
```

Simple example of accessing NSApplication:

```java
NSMenu menu = NSMenu.alloc().initWithTitle("Title");
// add more items...
NSApplication.sharedApplication().setMainMenu(menu);
```
