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

- 首次安装默认用户名：docker，密码：tcuser