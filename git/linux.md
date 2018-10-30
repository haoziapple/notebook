# linux相关

- [bash-guide](https://github.com/Idnan/bash-guide)


```
创建独立的screen命令:
screen -dmS authscreen
连接已创建的authscreen
screen -r authscreen
断开
Ctrl+A+D
杀掉窗口
Ctrl+A k
清除dead会话
screen -wipe
```

## 查看日志
> cat /var/log/messages


## 启动脚本
```bash
#!/bin/bash
app="eureka-server"
if [ "$1" == "start" ]
then
  if [ -f "$app.pid" ]
  then
    echo "$app already stated at PID: `cat $app.pid`"
  else
    nohup java -jar $app-0.0.3-SNAPSHOT.jar --server.port=8015  > $app.log 2>&1 &
    echo "$!" > $app.pid
    echo "$app started at PID: $!"
    echo "PID of this script: $$"
    echo "PPID of this script: $PPID"
    echo "UID of this script: $UID"
  fi
elif [ "$1" == "stop" ]
then
  kill `cat $app.pid`
  rm -f $app.pid
else
  echo "Use: ./eureka.sh {start} | {stop}"
fi
```

```bash
#!/bin/bash
app="eureka-server"
case $1 in
start)
  if [ -f "$app.pid" ]
  then
    echo "$app maybe already started at PID: `cat $app.pid`"
    pid=`cat $app.pid`
    process=`ps aux | grep $pid | grep -v grep`
  else
    pid=""
    process=""
  fi
    
  while true;do
    process=`ps aux | grep $pid | grep -v grep`
    echo "$process"

    if [ "$pid" -a "$process" ]
    then
      echo "$app running at PID: $pid"
    else
      nohup java -jar $app-0.0.3-SNAPSHOT.jar --server.port=8015  > $app.log 2>&1 &
      echo "$!" > $app.pid
      echo "$app started at PID: $!"
      pid=$!
      echo "PID of this script: $$"
      echo "PPID of this script: $PPID"
      echo "UID of this script: $UID"
    fi
    sleep 60s
  done
;;
stop)
  kill `cat $app.pid`
  rm -f $app.pid
;;
*)
  echo "Use: ./eureka.sh {start} | {stop}"
esac
```