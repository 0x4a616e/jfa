# Java Foundation Access

[![Maven Central](https://img.shields.io/maven-central/v/de.jangassen/jfa.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.jangassen%22%20AND%20a:%22jfa%22)
![Test](https://github.com/0x4a616e/jfa/workflows/Test/badge.svg)
![CodeQL](https://github.com/0x4a616e/jfa/workflows/CodeQL/badge.svg)

Java Foundation Access is a pure Java library for accessing Apples Foundation framework.

Access is done purely using [JNA](https://github.com/java-native-access/jna). Foundation classes are based on classes from IntelliJ Community Edition https://github.com/JetBrains/intellij-community 

# Usage

Just include the latest version of JFA in your maven pom or build.gradle. You can find some examples [here](https://search.maven.org/search?q=g:%22de.jangassen%22%20AND%20a:%22jfa%22). 

## Calling native methods

The following example shows how to create a native menu and use it as the main menu for your current application.

```java
NSMenu menu = NSMenu.alloc().initWithTitle("Title");
// add more items...
NSApplication.sharedApplication().setMainMenu(menu);
```

You can find more information about the API in the [Apple Developer Documentation](https://developer.apple.com/documentation/objectivec?language=objc) and
the Appkit [App and Environment](https://developer.apple.com/documentation/appkit/app_and_environment?language=objc) documentation.

## Adding API support

JFA already provides a set of API classes and methods but it is far from complete. If there is something missing for your project,
you can simply define a Java interface according to the Apple documentation as in the following example.

```java
public interface NSData extends NSObject {
  /**
   * Create a new instance of NSData.
   */
  static NSData alloc() {
    return ObjcToJava.alloc(NSData.class);
  }

  /**
   * Initializes a data object filled with a given number of bytes copied from a given buffer.
   * 
   * - (instancetype)initWithBytes:(const void *)bytes 
   *                        length:(NSUInteger)length;
   */
  NSData initWithBytes(Memory bytes, @NamedArg("length") int length);

  /**
   * Creates a Base64 encoded string from the string using the given options.
   * 
   * - (NSString *)base64EncodedStringWithOptions:(NSDataBase64EncodingOptions)options;
   */
  String base64EncodedStringWithOptions(int options);
}
```

The Javadoc shows the original method signatures in Objective-C as [documented](https://developer.apple.com/documentation/foundation/nsdata?language=objc).
To create an instance of your interface, just call `ObjcToJava.alloc(NSData.class);`. For convenience, you can add a static `alloc`-method to your interface as shown in the example above.

When calling `ObjcToJava.alloc(NSData.class);`, JFA creates a new native object of type `NSData` and automatically binds that to your Java instance. Whenever a method on the Java object is called, it automatically forwarded to its native counterpart. All required data types are mapped automatically.

Also, you may consider creating a pull request containing your added API support :)

## Adding custom native Objects

If creating Java counterparts of existing native classes is not enough, you can also define custom native classes. Therefore, create a POJO and call `JavaToObjc.map(yourPojoInstance)`. JFA will return a pointer to the native object. Calls made to the native object will automatically be forwarded to you your POJO. A common use case is creating callbacks for actions happening within native code.

The following example shows the mapping of a Java object to Objective-c:

```java
// Create the POJO instance
MyPOJO pojo = new MyPOJO();
// Map it to a native object
ID instance = JavaToObjc.map(pojo);
// Manually invoke a method on the native object
Foundation.invoke(instance, "testMethod");
// testMethod is invoked on pojo
```

The invocation of `testMethod` on the Objective-C object will be forwarded to Java and `MyPOJO.testMethod()` will be called.

Keep in mind that support for mapping custom Java objects to native objets is still limited and JFA might not be able to map all methods to a native object. Also, the native object will _not_ have any properties of the POJO. 

# Getting help

If you need help or something is not working out, you can create an [issue](Issues
) or start a [discussion](https://github.com/0x4a616e/jfa/discussions).
