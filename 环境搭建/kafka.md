## 创建一个名为test的topic
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

## 查看已经创建的topic
> bin/kafka-topics.sh --list --zookeeper localhost:2181

## 创建一个消息消费者
> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

## 创建一个消息生产者
> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test


## 消息确认方式(offset更新)
1. 自动提交，设置enable.auto.commit=true，更新的频率根据参数【auto.commit.interval.ms】来定。这种方式也被称为【at most once】，fetch到消息后就可以更新offset，无论是否消费成功。
2. 手动提交，设置enable.auto.commit=false，这种方式称为【at least once】。fetch到消息后，等消费完成再调用方法【consumer.commitSync()】，手动更新offset；如果消费失败，则offset也不会更新，此条消息会被重复消费一次。