```java
public class IPLogConfig extends ClassicConverter {
    public IPLogConfig() {
    }

    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
```

```java
public class FastCallerDataConverter extends CallerDataConverter {
    public String convert(ILoggingEvent le) {
        String result = super.convert(le);
        if (result.endsWith(CoreConstants.LINE_SEPARATOR))
            return result.substring(0, result.length() - CoreConstants.LINE_SEPARATOR_LEN);
        return result;
    }
}
```

依赖
```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
<dependency>
	<groupId>org.codehaus.janino</groupId>
	<artifactId>janino</artifactId>
	<version>3.0.7</version>
</dependency>
```