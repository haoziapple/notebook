# 安装rabbitMq--CentOS6.5

### 安装Erlang
> wget https://package.erlang-solutions.com/erlang-solutions-1.0.1.noarch.rpm

> rpm -Uvh erlang-solutions-1.0.1.noarch.rpm

> sudo yum install erlang(不要安装esl-erlang)

### 安装socat(rabbitmq依赖)
- 必须使用rpm安装： 下载socat的rpm包进行安装
> sodu yum install socat-1.7.1.3-1.el6.art.x86_64.rpm

### 安装rabbitmq
> yum install rabbitmq-server-3.6.6-1.el6.noarch.rpm

### 启动rabbitmq
> sudo service rabbitmq-server start
 
### 错误--“badarg (unknown POSIX error)”修改方法
1. 将hostname修改为英文字母
> hostname test

2. 修改 /etc/hosts 域名解析文件
> vim /etc/hosts

> 127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4 test
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6


### 日志路径
> /var/log/rabbitmq

### 配置全权限
1. 创建用户
> rabbitmqctl add_user testname testpwd

> 创建一个用户名为testname，密码为testpwd的用户

2. 查看用户
> rabbitmqctl list_users

3. 更改用户密码
> rabbitmqctl change_password testname newpwd

> 将用户testname的密码更改为newpwd

4. 设置权限
> rabbitmqctl set_permissions -p / testname ".*" ".*" ".*"

> / 是默认的vhost，授予testname用户完全的访问权限（配置、写和读权限）

5. 查看某个vhost的权限
> rabbitmqctl list_permissions -p /

6. 查看某个用户的权限
> rabbitmqctl list_user_permissions testname

### 使用管理台
1.启用管理台插件
> rabbitmq-plugins enable rabbitmq_management

> 默认管理台端口：15672

2. 设置用户管理角色
> rabbitmqctl set_user_tags testname administrator

> 将testname用户设置为管理员