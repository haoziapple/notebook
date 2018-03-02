- [rednaxelafx：jvm大牛](http://rednaxelafx.iteye.com/)
- 使用Jprofiler, JConsole, VisualVM
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