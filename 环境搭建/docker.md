# Docker学习笔记
命令行:docker, docker-machine
- 测试是否安装成功
```
docker --version
docker run hello-world
```

- 创建一台docker主机：
```
docker-machine create --driver virtualbox dev
```

- 在Win中使用docker时，使用virtualbox创建了一个linux虚拟机，然后在虚拟机上再运行docker
    - 虚拟机使用boot2docker.iso
    - 虚拟机用户名：docker，密码：tcuser
    - 也可以使用Docker box下的start.sh脚本进入(Git Bash,MINGW64)