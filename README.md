springly [![Build Status](https://travis-ci.org/loki2302/springly.svg?branch=master)](https://travis-ci.org/loki2302/springly)
========

**Work in progress, not released yet!**

Springly is an abstract framework for building "convention over configuration" frameworks. Namely, it's all about Spring Framwork's fancy things like `@Controller`, `@RequestMapping`, etc. The general story is: as a developer, I want to write POJOs and seamlessly expose them to the consumers, no matter how weird the desired facade is. Take a look at example:
```java
public class Calculator {
    public int addNumbers(int a, int b) {
        return a + b;
    }
    
    public int subNumbers(int a, int b) {
        return a - b;
    }
}
...
// a POJO!
Calculator calculator = new Calculator();
assertEquals(5, calculator.addNumbers(2, 3));
```
Everything is just trivial. But what if we want to call `Calculator`'s methods dynamically in response to external requests? Here's what Springly does:
```java
public class Calculator {
    @DummyAction("addNumbers")
    public int addNumbers(
            @DummyParam("a") int a,
            @DummyParam("b") int b) {

        return a + b;
    }

    @DummyAction("subNumbers")
    public int subNumbers(
            @DummyParam("a") int a,
            @DummyParam("b") int b) {

        return a - b;
    }
}
...
// Still a POJO!
Calculator calculator = new Calculator();
assertEquals(5, calculator.addNumbers(2, 3));
...
// but not only a POJO...
RequestProcessor requestProcessor = Springly.makeRequestProcessor(calculator);
Object result = requestProcessor.processRequest(new HashMap<String, Object>() {{
    put("action", "addNumbers");
    put("a", 2);
    put("b", 3);
}});
assertEquals(5, result);
```
Note that Springly is not some sort of RPC: it only provides a set of abstract tools to read POJOs, build handlers based on metadata read, and then call these handlers based on request details.
```groovy
repositories {
  ...
  maven {
    url "http://dl.bintray.com/loki2302/maven/"
  }
}

dependencies {
  ...
  compile 'me.loki2302:springly:0.2'
}
```
