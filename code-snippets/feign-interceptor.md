# springboot,feignClient自定义拦截器

## 使用okhttp

1. 添加依赖
```xml
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-okhttp</artifactId>
    <version>9.5.0</version>
</dependency>
```

2. bootstrap添加配置
```
feign.httpclient.enabled=false
feign.okhttp.enabled=true
```

3. 添加代码:
```java
@Bean
public okhttp3.OkHttpClient okHttpClient() {
    okhttp3.OkHttpClient.Builder ClientBuilder = new okhttp3.OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(10, 3, TimeUnit.MINUTES))
            .addInterceptor(okHttpInterceptor());
    return ClientBuilder.build();
}

public Interceptor okHttpInterceptor() {
    return new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();

            // 添加x-user-id请求头
            requestBuilder.addHeader("x-user-id", "testUserId");
            Response response = chain.proceed(requestBuilder.build());
            return response;
        }
    };
}
```

## 添加feign拦截器(Finchley版本)
```java
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes!=null){

            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request!=null){
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames!=null){
                    while (headerNames.hasMoreElements()){
                        String headerName = headerNames.nextElement();
                        if (headerName.equals("authorization")){
                            String headerValue = request.getHeader(headerName);
                            requestTemplate.header(headerName,headerValue);
                        }
                    }
                }
            }
        }
    }
}
```