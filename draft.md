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

## 基本数据类型与包装数据类型
> POJO类属性必须使用包装数据类型，null值能够显示额外信息-阿里java规范

## 集合转数组
```java
String[] array = new String[list.size()];
array = list.toArray(array);
```