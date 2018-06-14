# SWAPP Framework
[![](https://jitpack.io/v/kasra-sh/swapp.svg)](https://jitpack.io/#kasra-sh/swapp)
#### Simple-Lightweight-Fast Java Web Framework (using [PicoHTTPd](https://github.com/kasra-sh/picohttpd.git))
#### Who should use this?
1. Server/Website developers who hate C<u>()</u>m<s>PLE</s>x<i>IT</i><b>Y</b>!
2. Mobile developers trying to write a simple(or advanced) API for their apps!
3. Developers who cannot afford hundreds of GBs of RAM (and still want to use Java)!
4. Myself :)
#### How to Import :
##### (Gradle)
Add these in your project's build.gradle file
```java
repositories {
	...
	maven { url 'https://jitpack.io' }
}
```
```java
dependencies {
	...
	compile 'com.github.kasra-sh:swapp:1.0.8'
}
```

##### (Non-Gradle)
1. Clone or Download repository
2. Build jar with dependencies :
    ```bash
    $ gradle fatJar
    ```
3. Jar file is generated in build/libs/swapp-all-(VERSION).jar
4. Include jar file in your classpath
---
#### Usage :
```java
import ir.kasra_sh.swapp.Swapp;
import ir.kasra_sh.swapp.SwappModule;
import ir.kasra_sh.swapp.Responses;

// Extend SwappModule
public class Test extends SwappModule {
    // Define routes
    {
        // Responds to IP:PORT/hello
        get("/hello", ((r, ex) -> Responses.ok("Hi !\n")));

        // Responds to IP:PORT/api/echo and echoes request body
        post("/api/echo", ((r, ex) -> {
                return Responses.json(200, "{\"request_body\":\""+new String(r.getBody())+"\"}");
        }));

        // Finally glue modules together and start!
        new Swapp.addModule(this).start(8000);
    }
}
```
<i>Please refer to /examples for more.</i>