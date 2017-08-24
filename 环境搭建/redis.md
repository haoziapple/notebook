# 安装Redis(在CentOS下）
```
yum install ruby
yum install rubygems
wget http://rubygems.global.ssl.fastly.net/gems/redis-3.2.1.gem
gem install -l ./redis-3.2.1.gem   （可以使用gem sources -a https://ruby.taobao.org/更换淘宝源）
gem install redis（我更换淘宝源之后使用的安装命令）
```
=====================================需要redis这个gem执行redis-trib.rb（操作cluster的ruby脚本======================================

## 开始安装Redis
```
tar -zxvf redis-3.0.0.tar.gz
mv redis-3.0.0 redis3.0
cd /usr/local/redis3.0
make & make install
```

## 配置集群
```
mkdir -p /usr/local/cluster
mkdir -p /usr/local/cluster/12001
mkdir -p /usr/local/cluster/12002
mkdir -p /usr/local/cluster/12003
mkdir -p /usr/local/cluster/12004
mkdir -p /usr/local/cluster/12005
mkdir -p /usr/local/cluster/12006
mkdir -p /usr/local/cluster/12007
mkdir -p /usr/local/cluster/12008
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12001/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12002/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12003/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12004/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12005/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12006/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12007/
cp /usr/local/redis-3.0.5/redis.conf /usr/local/cluster/12008/
```

## 单个节点配置
```
vi /usr/local/cluster/12001/redis.conf
port 7000
daemonize yes
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
bind localhost(不要设置这个）
cd /usr/local/cluster/12001/
redis-server redis.conf
```

## 组建集群：
> /usr/local/redis-3.0.5/src/redis-trib.rb create --replicas 1 192.168.102.10:12001 192.168.102.10:12002 192.168.102.10:12003 192.168.102.10:12004 192.168.102.11:12005 192.168.102.11:12006 192.168.102.11:12007 192.168.102.11:12008

## 删除集群：
> 删除配置集群时生成的nodes-*.conf文件，重启node

## 停止节点：
> redis-cli -h 192.168.102.11 -p 12007 shutdown

## 接入cluster：
> redis-cli -c -h 127.0.0.1 -p 7000
(接入集群模式时需要使用-c参数）

## 检查cluster状态：
> redis-trib.rb check 127.0.0.1:7000