# bug记录

- 管饱项目定时器执行时间总是提早了12小时(始终纠结在时区问题上)
> ibatis映射文件中jdbcType设置为DATE，导致时分秒信息被截取掉，修改为TIMESTAMP后问题解决
```xml
<parameterMap id="settleMap" class="java.util.HashMap">
    <parameter property="startTime" jdbcType="DATE" javaType="java.util.Date" mode="IN"/>
    <parameter property="endTime" jdbcType="DATE" javaType="java.util.Date" mode="IN"/>
</parameterMap>
```

- 输入输出流的问题，下载图片偶现无法正常下载的问题
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