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

## spring配置bean之间有依赖关系的解决办法
- 使用注解 @AutoConfigureAfter 或 @AutoConfigureBefore

## IDEA对于provided的依赖的bug
- [Idea下运行Spring Boot关于provided依赖不加入classpath的bug与解决方案](http://blog.csdn.net/neosmith/article/details/50924681)
- [解决intellij中spring boot工程 无法用mainApplication启动问题](http://blog.csdn.net/u012263647/article/details/55504840)

## maven部署接口到私服的问题
> 暴露接口的时候，需要把接口intf模块部署到私服，如果父模块没有部署的话会导致引入依赖报错

**解决方法：**
1. 在父模块执行mvn deploy，但要把modules里不想部署的模块暂时去除
2. intf模块独立出来，不要与其他模块共享父模块，单独执行mvn deploy

## 启动和禁用mysql外键约束
禁用外键约束，我们可以使用:
> SET FOREIGN_KEY_CHECKS=0;
启动外键约束，我们可以使用:
> SET FOREIGN_KEY_CHECKS=1;
查看当前FOREIGN_KEY_CHECKS的值，可用如下命令：
> SELECT  @@FOREIGN_KEY_CHECKS; 


## trello地址
https://trello.com/b/7hK5ZTH2/haozi-workboard

CAP定理（C-数据一致性；A-服务可用性；P-服务对网络分区故障的容错性，这三个特性在任何分布式系统中不能同时满足，最多同时满足两个)

- [springfox项目](https://github.com/springfox/springfox)

1. 熟悉jeecms后台代码
2. 尝试修改jeecms前端代码，调用fast平台登陆接口
3. java内存工具的学习--jmap，jstat
4. spock测试框架的学习
6. flowable工作流引擎的学习
7. angular1的学习