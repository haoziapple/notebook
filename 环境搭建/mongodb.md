# mongodb搭建

配置：
```
port=27017 
dbpath=/home/wanghao/mongodb/db
logpath=/home/wanghao/mongodb/logs/mongodb.log
fork=true
logappend=true
bind_ip=0.0.0.0
#开启认证
#auth = true
```

启动：
```
./bin/mongod --config /home/wanghao/mongodb/mongodb.conf
```