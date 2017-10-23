# jQuery学习笔记

- 移动元素：appendTO
- 添加元素：append
- 选择第几个子元素：eq(3)
- 元素查找：find()

- 表单循环校验：需要jQuery validate插件
```javascript
$("form").each(function(){
    console.log($(this).valid());
})
```
- 表单不完整（少```<div/>```结尾标签时，校验会产生报错）