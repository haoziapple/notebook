## mysql5.7新增Json格式数据

## 查看mysql版本
- 命令行使用status
- ``` select version ```

## 使用匿名类初始化
```java
historyList.add(new BuyData()
{
	{
		setGoodsName("coke");
		setNum(13);
	}
});
```
## 使用Gson序列化bean时，使用匿名内部类的问题：下面的代码将返回null
```java
Gson gson = new Gson();
String jsonStr = gson.toJson(new SubmitOrderInfo() {
    {
    	setId("id");
    	setToken("token");
    	setUserId("userId");
    	setTotalPrice("totalPrice");
	}
});
```
- 修改方法，添加类型信息
```java
Gson gson = new Gson();
String jsonStr = gson.toJson(new SubmitOrderInfo() {
    {
    	setId("id");
    	setToken("token");
    	setUserId("userId");
    	setTotalPrice("totalPrice");
	}
}, SubmitOrderInfo.class);
```

## 基本数据类型与包装数据类型
> POJO类属性必须使用包装数据类型，null值能够显示额外信息-阿里java规范

## 集合转数组
```java
String[] array = new String[list.size()];
array = list.toArray(array);
```

## 配置bean里无法注入Environment
- 实现EnvironmentAware，参考StackOverflow上的回答:
- [Spring Boot - Environment @Autowired throws NullPointerException](https://stackoverflow.com/questions/19454289/spring-boot-environment-autowired-throws-nullpointerexception)

## IDEA对于provided的依赖的bug
- [Idea下运行Spring Boot关于provided依赖不加入classpath的bug与解决方案](http://blog.csdn.net/neosmith/article/details/50924681)
- [解决intellij中spring boot工程 无法用mainApplication启动问题](http://blog.csdn.net/u012263647/article/details/55504840)