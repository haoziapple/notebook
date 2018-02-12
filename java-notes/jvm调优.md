- 使用Jprofiler, JConsole, VisualVM
- IDEA下安装Jprofiler插件

垃圾回收顺序：Eden space-->Survivor space-->Old Gen
-Xss：调整每个线程的栈大小，防止栈溢出
-Xms:初始堆大小
-Xmx:最大堆大小

eureka服务调优参数--undertow：
```
-Xmx32m -Xms32m -Xmn16m
```

一般服务调优参数--undertow:
```
-Xmx64m -Xms64m -Xmn32m
```