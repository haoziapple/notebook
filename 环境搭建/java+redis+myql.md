# java+Redis+Mysql
## java1.8
去除老版本
```
yum remove java-1.7.0-openjdk
yum remove java-1.6.0-openjdk
wget http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-x64.rpm
rpm -ivh jdk-8u161-linux-x64.rpm
vim .bashrc
```
文件末尾添加
```
export JAVA_HOME=/usr/java/jdk1.8.0_161
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```
```
source .bashrc
```

启动脚本
```
nohup java -jar auth-server-0.0.3-SNAPSHOT.jar --server.port=${1} --eureka.client.serviceUrl.defaultZone=http://localhost:9001/eureka/ > /dev/null &
```

## Redis4.0
安装
```
wget http://download.redis.io/redis-stable.tar.gz
tar -zxvf redis-stable.tar.gz
cd redis-stable
make install
vim redis.conf
redis-server redis.conf (启动)
redis-server -p 7021 shutdown (关闭)
```
修改bind配置，关闭protected-mode
```
bind 127.0.0.1 192.168.1.75 192.168.1.184 192.168.1.48
protected-mode no
daemonize yes
pidfile /var/run/redis_7021.pid
logfile /root/install-files/redis/log/redis_7021.log
dbfilename redis_7021_dump.rdb
dir /root/install-files/redis/data
```

scp传文件，-r传文件夹
```
scp redis-stable.tar.gz root@192.168.1.48:/root/
```

## Mysql5.7
安装
```
wget https://cdn.mysql.com//Downloads/MySQL-5.7/mysql-5.7.21-1.el6.x86_64.rpm-bundle.tar
rpm -qa|grep -i mysql
yum remove mysql-libs (删除旧版本lib)
rpm -ivh mysql-community-libs-compat-5.7.21-1.el6.x86_64.rpm 
rpm -ivh mysql-community-libs-5.7.21-1.el6.x86_64.rpm 
rpm -ivh mysql-community-common-5.7.21-1.el6.x86_64.rpm 
rpm -ivh mysql-community-client-5.7.21-1.el6.x86_64.rpm 
rpm -ivh mysql-community-server-5.7.21-1.el6.x86_64.rpm 
```
忘记密码修改
```
mysqld_safe  --skip-grant-tables
set password=password('密码不能太简单-5.7版本要求')
UPDATE user SET Password = PASSWORD('newpass') WHERE user = 'root';
FLUSH PRIVILEGES;
mysql -hlocalhost -uroot -p[Your-password](登陆)
```
导脚本
```
source /root/myscript.sql
```
数据库表名大小写问题，mysql默认是大小写敏感的：lower_case_table_names
- 为0时（Linux默认），大小写敏感，创建和查询都是区分大小写；
- 为1时，创建表以小写，查询表也是以小写；
- 为2时，创建表区分大小写，查询表以小写。

```
vi /etc/my.cnf
lower_case_table_names=1  ([mysqld]中添加)
service mysqld restart
```
SSL:Mysql5.7默认开启了SSL
```
show variables like '%ssl%';
spring.datasource.url = jdbc:mysql://192.168.1.48:3306/test?characterEncoding=UTF-8&useSSL=false  (TODO:useSSL=true的时候会报错，如何配置待研究)
```