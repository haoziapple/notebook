# springboot配置浏览器缓存

- 缓存时间配置项：spring.resources.cache-period

- 添加Etag过滤器，有可能影响性能:
```java
/**
* 浏览器缓存配置，添加Etag Header信息，用以判断服务端文件是否更新
* @return
*/
@Bean
public Filter shallowEtagHeaderFilter() {
    return new ShallowEtagHeaderFilter();
}
```