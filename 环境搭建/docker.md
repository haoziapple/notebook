# Docker学习笔记
命令行:docker, docker-machine
- 测试是否安装成功
```
docker --version
docker run hello-world
```
- 切换阿里镜像加速器
```
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [""]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```
- [docker 安装sonarqube](https://hub.docker.com/_/sonarqube/)
```bash
$ docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
```

## Win中使用docker
- 创建一台docker主机：
```
docker-machine create --driver virtualbox dev
```

- 在Win中使用docker时，使用virtualbox创建了一个linux虚拟机，然后在虚拟机上再运行docker
    - 虚拟机使用boot2docker.iso
    - 虚拟机用户名：docker，密码：tcuser
    - 也可以使用Docker box下的start.sh脚本进入(Git Bash,MINGW64)

## Docker命令
- 查看所有容器
```
$ docker ps -a
```

- 后台运行一个容器
```
$ docker run -d ubuntu:15.10 /bin/sh -c "while true; do echo hello world; sleep 1; done"
```

- 构建镜像
编写Dockerfile：
```
FROM    centos:6.7
MAINTAINER      Fisher "haozixiaowang@163.com"

RUN     /bin/echo 'root:123456' |chpasswd
RUN     useradd wanghao
RUN     /bin/echo 'wanghao:123456' |chpasswd
RUN     /bin/echo -e "LANG=\"en_US.UTF-8\"" >/etc/default/local
EXPOSE  22
EXPOSE  80
CMD     /usr/sbin/sshd -D
```
运行命令：
```
~$ docker build -t wanghao/centos:6.7 .
```

查看镜像：docker images
查看正在运行容器：docker container ls
删除容器：docker rm acontainer-name


## 其他安装
- Win上安装choco，以管理员运行cmd
```bash
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
```

## docker安装mysql
```
sudo docker pull mysql
sudo docker run --name first-mysql -p 3306:3306 -e MYSQL\_ROOT\_PASSWORD=123456 -d mysql
```

## 使用docker-compose
```
后台启动
docker-compose up -d
查看版本
docker-compose version
列出所有服务
docker-compose ps
停止服务
docker-compose stop
重启服务
docker-compose restart
```