# springboot配置多个404页面

- [SpringBoot Web如何针对不同的客户端定制errorPage](https://www.jianshu.com/p/ae42c12f2304)

按上面的配置了以后，springcloud zuul使用时会报NPE，原因是RequestContextHolder.getRequestAttributes()返回null。

通过添加注册RequestContextListener解决：
```java
@Bean
public RequestContextListener requestContextListener(){
    return new RequestContextListener();
} 
```

- [RequestContextHolder.getRequestAttributes()空指针问题](https://blog.csdn.net/qq_38846242/article/details/83382969)