- [rednaxelafx：jvm大牛](http://rednaxelafx.iteye.com/)
- 使用Jprofiler, JConsole, VisualVM，YourKit Java Profiler
- IDEA下安装Jprofiler插件
- jdk/bin下的jvisualvm.exe，jconsole.exe

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

编译源文件，-g：生成调试信息，方便调试，-d 生成包路径
```
javac -g -d destdir srcFile
// 当前目录下编译Hello并生成包路径
javac -g -d . Hello.java
```

运行，注意包路径的匹配
```
java com/wanghao/Hello
```

查看字节码, 使用-verbose查看详细信息
```
javap -c com/wanghao/Hello
```

命令行调试java：jdb
```
查看命令帮助：help
在方法上打断点,方法前要加上包名：stop in com.wanghao.Hello.main
运行：run com.wanghao.Hello
```

查看java进程：jps命令，记录pid

启动HSDB，加双引号是为了路径中有空格时识别错误
```
java -cp .;"%JAVA_HOME%/lib/sa-jdi.jar" sun.jvm.hotspot.HSDB
```

报异常，将jdk/bin下的sawindbg.dll文件复制过去：
Exception in thread "Thread-1" java.lang.UnsatisfiedLinkError: Can't load library: D:\Program Files\Java\jre8\bin\sawindbg.dll

jstat,jinfo


## jvm问题排查
> 参考链接：[java8](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/)
- 查找活跃进程
```
ps -ef|grep java
ps -aux|grep java
```
- 查找java进程
```
jps -v
```
- 查看存活的对象情况
```
jmap -histo:live [pid]
```
- 查看java启动信息：jinfo
- 查看JVM运行时GC统计信息
```
jstat -gcutil [pid]
```

- jmap -histo:live 10794 | head -n 100
- jstat -gcutil 10794 2000 10
- top -p pid  -H  查看针对每一个线程占用CPU情况
- jstack 10794|grep -A  100 0x2a2f 查看某一个线程
- jmap -heap 10794


- java -server -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal -version | grep -i heapsize
- java -client -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal -version | grep -i heapsize

- [Java内存溢出(OOM)异常完全指南](https://www.jianshu.com/p/2fdee831ed03)


## 使用jprofiler

- wget http://download-keycdn.ej-technologies.com/jprofiler/jprofiler_linux_9_2.rpm
- rpm -ivh jprofiler_linux_9_2.rpm
- /opt/jprofiler9
- /usr/local/bin

## 使用greys-anatomy

https://github.com/oldmanpushcart/greys-anatomy


## 查看端口占用

- netstat
- t (tcp) 仅显示tcp相关选项
- u (udp)仅显示udp相关选项
- n 拒绝显示别名，能显示数字的全部转化为数字
- l 仅列出在Listen(监听)的服务状态
- p 显示建立相关链接的程序名

## 性能检测工具
- Jprofiler
- YourKit Java Profiler
- JConsole
- VisualVM
- JMC

## 调优参数
- -XX:NewRatio=2 (老年代是新生代的2倍)
- -XX:SurvivorRatio=8 (eden区是一个survivor的8倍，survivor区两个同样大)
- -XX:+DisableExplicitGC (System.gc()调用无效化)
- -XX:+UseParNewGC (:设置年轻代为多线程收集)
- -XX:CMSInitiatingOccupancyFraction=70 (触发CMS收集器的内存比例。比如60%的意思就是说，当内存达到60%，就会开始进行CMS并发收集)
- -XX:+PrintGC (输出GC日志)
- -XX:+PrintGCDetails (输出GC的详细日志)
- -XX:+PrintHeapAtGC (在进行GC的前后打印出堆的信息)
- -XX:+PrintGCDateStamps (输出GC的时间戳，以日期的形式，如2013-05-04T21:54)
- -XX:+PrintGCTimeStamps (输出GC的时间戳，以基准时间的形式，与上面的二选一)
- -XX:+PrintTenuringDistribution (输出显示在survivor空间里面有效的对象的岁数情况)
- -Xloggc:~/logs/eureka-server-jvm.log