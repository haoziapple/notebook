# 面试笔记
友邦软件
- interface中的修饰符只可以有public和abstract
- 类的方法名可以与类名相同（但不建议这样做）
- 斐波那契数列的计算：最好不使用递归、以及考虑数值过大的问题(超过int或long的最大值)
- 怎样写好代码？
```
入参的校验：防御性编程
代码的可度量：日志，统计
接口的约定与设计
我没考虑到的：代码的命名规范，方法的提取
```


车300
- 算法题：对权重的理解错了，这里要求的可能是类似于邮局选址问题的中心点算法
```
给定一组数据，每个数据都有一定权重，请写算法计算中位数 
数据[1,2,3,4,5]  
权重[3,2,3,3,8] 
```
车置宝
- java cocurrent包:atomic包，locks包，Condition，可重入的概念，读写锁
- java 的位运算 补码机制
- 多线程 interrupt的机制
- 构造器的继承 与static代码块的关系

联想-懂的通信(问的很多、很细)
- tomcat启动方式 3种:webapp下部署,Context.xml,localhost中新建xml文件(当时一时没想起来，其实自己是知道的)
- solr collection replica tier的概念
- java容器有哪些
- hashmap rehash机制
- mysql索引原理 B+tree、还有什么？
- bash脚本 python脚本
- geo grid
- 多线程
- 连接池的概念，作用
- js的事件传递 两种？ 冒泡机制
- vue.js的原理
- jvm 调优

算法学习
- [Implementation of Algorithms and Data Structures, Problems and Solutions](https://github.com/sherxon/AlgoDS)

面试准备
- [Everything you need to know to get the job.](https://github.com/kdn251/interviews)

## 2018-12-03

福特汽车
- redis操作，事务原理，管道
- 分布式事务
- restful规范，POST和PUT方法的区别

汇通达
- sql取交集、并集
- 设计一个秒杀系统，令牌桶怎么设计
- tomcat并发有多少

富士康
- hashtable和hashmap的区别
- hashmap线程安全
- hashmap的key，value是否可以为null，为什么
- java字节流和字符流的区别
- stringbuffer，stringbuilder区别，为什么
- 创建线程的几种方式
- 线程池的使用
- 简单工厂与抽象工厂
- oom如何排查
- 对象创建到销毁，jvm内存模型
- jvm内存回收，除了引用计数法还有什么？
- 一千万大表分页
- mybatis查询返回自增id
- mybatis#与$的区别，为什么
- mysql索引 b+tree原理
- mysql最左匹配原理
- shell 查看占用端口
- shell 查看是否有文件存在
- 存过的优缺点