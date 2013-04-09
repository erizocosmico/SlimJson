SlimJson
=====

**Current status:** [![Build Status](https://travis-ci.org/mvader/SlimJson.png?branch=master)](https://travis-ci.org/mvader/SlimJson)

SlimJson is a lightweight Java library to parse JSON.

Example 
====

```java
package example;

import me.mvader.slimjson.*;
import java.util.Map;

public class Example {
  public static void main(String[] args) {
    String exampleJson = "{\"test\" : \"value\"}";
  	Map<String, JsonValue> parsedJson = SlimJson.parse(exampleJson);
    System.out.println(parsedJson.get("test").value());
    // Outputs 'value'
  }
}
```

To do
====

* Support for nested objects.
