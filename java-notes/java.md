# java笔记
输入输出流的问题
```java
//读取文件 
InputStream inStream = conn.getInputStream();
//读取目标文件，通过response将目标文件写到客户端
OutputStream out = response.getOutputStream();
byte[] buffer = new byte[1024];
int len=0;
while ((len = inStream.read(buffer)) != -1) {
        // 注意这里不要使用out.write(buffer)!!!,buffer里可能有为空的byte
        out.write(buffer, 0, len);
        System.out.println(len);
}
```

格式化输出字串
> 使用MessageFormat类