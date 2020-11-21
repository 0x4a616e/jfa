# Java Foundation Access

Java Foundation Access is a pure Java library for accessing Apples Foundation framework.

Access is done purely using JNA. Foundation classes are based on classes from IntelliJ Community Edition https://github.com/JetBrains/intellij-community 

# Usage

Add maven dependency

```xml
<dependency>
  <groupId>de.jangassen</groupId>
  <artifactId>jfa</artifactId>
  <version>1.0</version>
</dependency>
```

Simple example of accessing NSApplication:

```java
NSMenu menu = NSMenu.alloc().initWithTitle("Title");
// add more items...
NSApplication.sharedApplication().setMainMenu(menu);
```
