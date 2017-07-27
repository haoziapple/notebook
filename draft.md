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