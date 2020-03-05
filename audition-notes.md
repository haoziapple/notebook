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

中软/软通/华为
- mysql如何监控，如何优化，有什么可视化工具
- java命令行，jps，jstat，jmap，如何查看内存溢出，如何dump出堆内存，如何查看加载了哪些class类
- jprofiler如何连接远程的java进程
- jvm垃圾回收器有哪几种类型，如何指定使用某种垃圾回收器
- jvm调优，jvm的内存模型
- 垃圾回收stop the world是怎么一回事，是不是所有回收器都有这种机制
- sleep和wait的区别
- 设计模式和使用的场景（我说出来的：工厂、建造者、门面、装饰器、适配器、命令）
- 分库分表的策略,分表后查前10的数据如何查
- 消息队列的使用，rabbitmq，kafka，exactly once是否支持（kafka已成为业界标准？支持事务，exactly once）
- 算法是否有了解
- 容器docker，镜像里面打了哪些东西
- 安全性，哪些请求头要过滤，前端安全，sql注入问题-mybatis，接口加密、验签
- 网络支持，tcp三次握手，四次挥手，抓包
- 数据库mysql，oracle，nosql
- 服务如何扩容、缩容
- mysql如何在代码中显式的加锁、释放锁
- 如何查询定位mysql当中的死锁问题

- zk和eureka的区别，zk的机制-paxos协议？
- dubbo的工作机制
- maven的生命周期
- 算法：时间复杂度、空间复杂度如何计算
- 垃圾回收：标记算法、可达性分析
- redis集群的搭建方式、原理，分布式锁的使用
- servlet,listener,filter的关系和作用
- Tomcat启动是如何启动spring的，Springboot是如何启动内嵌容器的（没答上来）

- 一道算法题：太阳帆问题，所有杆子长度不等都是整数，间隔1m站立，太阳帆的高度取决于最短的杆子，寻找最大可能的太阳帆面积