- [ELK环境的搭建](#elk环境的搭建)
    - [elasticsearch](#elasticsearch)
    - [logstash](#logstash)
    - [kibana](#kibana)
    - [elasticsearch.yml](#elasticsearch-yml)
    - [kibana.yml](#kibana-yml)
    - [log4j_to_es.conf](#log4j_to_es-conf)
# ELK环境的搭建

## elasticsearch
- /root/Downloads/ELK/elasticsearch-2.1.0
- 启动cmd:nohup ./bin/elasticsearch -Des.insecure.allow.root=true &

## logstash
- /root/Downloads/ELK/logstash-5.3.1
- 启动cmd:nohup ./bin/logstash -f config/log4j_to_es.conf &

## kibana
- /root/Downloads/ELK/kibana-4.3.0-linux-x64
- 启动cmd:nohup ./bin/kibana &

## elasticsearch.yml
```yml
# ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
#       Before you set out to tweak and tune the configuration, make sure you
#       understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please see the documentation for further information on configuration options:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/setup-configuration.html>
#
# ---------------------------------- Cluster -----------------------------------
#
# Use a descriptive name for your cluster:
#
 cluster.name: my-application
#
# ------------------------------------ Node ------------------------------------
#
# Use a descriptive name for the node:
#
 node.name: node-1
#
# Add custom attributes to the node:
#
# node.rack: r1
#
# ----------------------------------- Paths ------------------------------------
#
# Path to directory where to store the data (separate multiple locations by comma):
#
 path.data: /root/Documents/elasticsearch/data
#
# Path to log files:
#
 path.logs: /root/Documents/elasticsearch/logs
#
# ----------------------------------- Memory -----------------------------------
#
# Lock the memory on startup:
#
# bootstrap.mlockall: true
#
# Make sure that the `ES_HEAP_SIZE` environment variable is set to about half the memory
# available on the system and that the owner of the process is allowed to use this limit.
#
# Elasticsearch performs poorly when the system is swapping the memory.
#
# ---------------------------------- Network -----------------------------------
#
# Set the bind address to a specific IP (IPv4 or IPv6):
#
 network.host: 10.108.26.101
#
# Set a custom port for HTTP:
#
 http.port: 9200
#
# For more information, see the documentation at:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/modules-network.html>
#
# ---------------------------------- Gateway -----------------------------------
#
# Block initial recovery after a full cluster restart until N nodes are started:
#
# gateway.recover_after_nodes: 3
#
# For more information, see the documentation at:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/modules-gateway.html>
#
# --------------------------------- Discovery ----------------------------------
#
# Elasticsearch nodes will find each other via unicast, by default.
#
# Pass an initial list of hosts to perform discovery when new node is started:
# The default list of hosts is ["127.0.0.1", "[::1]"]
#
# discovery.zen.ping.unicast.hosts: ["host1", "host2"]
#
# Prevent the "split brain" by configuring the majority of nodes (total number of nodes / 2 + 1):
#
# discovery.zen.minimum_master_nodes: 3
#
# For more information, see the documentation at:
# <http://www.elastic.co/guide/en/elasticsearch/reference/current/modules-discovery.html>
#
# ---------------------------------- Various -----------------------------------
#
# Disable starting multiple nodes on a single system:
#
# node.max_local_storage_nodes: 1
#
# Require explicit names when deleting indices:
#
# action.destructive_requires_name: true
```
## kibana.yml
```yml
# Kibana is served by a back end server. This controls which port to use.
 server.port: 5601

# The host to bind the server to.
 server.host: "10.108.26.101"

# A value to use as a XSRF token. This token is sent back to the server on each request
# and required if you want to execute requests from other clients (like curl).
# server.xsrf.token: ""

# If you are running kibana behind a proxy, and want to mount it at a path,
# specify that path here. The basePath can't end in a slash.
# server.basePath: ""

# The Elasticsearch instance to use for all your queries.
 elasticsearch.url: "http://10.108.26.101:9200"

# preserve_elasticsearch_host true will send the hostname specified in `elasticsearch`. If you set it to false,
# then the host you use to connect to *this* Kibana instance will be sent.
# elasticsearch.preserveHost: true

# Kibana uses an index in Elasticsearch to store saved searches, visualizations
# and dashboards. It will create a new index if it doesn't already exist.
 kibana.index: ".kibana"

# The default application to load.
# kibana.defaultAppId: "discover"

# If your Elasticsearch is protected with basic auth, these are the user credentials
# used by the Kibana server to perform maintenance on the kibana_index at startup. Your Kibana
# users will still need to authenticate with Elasticsearch (which is proxied through
# the Kibana server)
# elasticsearch.username: "user"
# elasticsearch.password: "pass"

# SSL for outgoing requests from the Kibana Server to the browser (PEM formatted)
# server.ssl.cert: /path/to/your/server.crt
# server.ssl.key: /path/to/your/server.key

# Optional setting to validate that your Elasticsearch backend uses the same key files (PEM formatted)
# elasticsearch.ssl.cert: /path/to/your/client.crt
# elasticsearch.ssl.key: /path/to/your/client.key

# If you need to provide a CA certificate for your Elasticsearch instance, put
# the path of the pem file here.
# elasticsearch.ssl.ca: /path/to/your/CA.pem

# Set to false to have a complete disregard for the validity of the SSL
# certificate.
# elasticsearch.ssl.verify: true

# Time in milliseconds to wait for elasticsearch to respond to pings, defaults to
# request_timeout setting
# elasticsearch.pingTimeout: 1500

# Time in milliseconds to wait for responses from the back end or elasticsearch.
# This must be > 0
# elasticsearch.requestTimeout: 300000

# Time in milliseconds for Elasticsearch to wait for responses from shards.
# Set to 0 to disable.
# elasticsearch.shardTimeout: 0

# Time in milliseconds to wait for Elasticsearch at Kibana startup before retrying
# elasticsearch.startupTimeout: 5000

# Set the path to where you would like the process id file to be created.
# pid.file: /var/run/kibana.pid

# If you would like to send the log output to a file you can set the path below.
# logging.dest: stdout

# Set this to true to suppress all logging output.
# logging.silent: false

# Set this to true to suppress all logging output except for error messages.
# logging.quiet: false

# Set this to true to log all events, including system usage information and all requests.
# logging.verbose: false
```
## log4j_to_es.conf
```json
input {
  log4j {
    mode => "server"
    host => "10.108.26.101"
    port => 4567
    type => "log4j"
  }
  redis {
    host => "10.108.26.220"
    port => 7007
    data_type => "list" 
    key => "logstash"
    type => "logback"
  }
  tcp {
    mode => "server"
    host => "10.108.26.101"
    port => 4560
    type => "log4j2"
  }
}
filter {
}
output {
  elasticsearch {
    action => "index"          
    hosts  => "10.108.26.101:9200"   
    index  => "applog"         
  }
}
```

# docker搭建ELK
git clone https://github.com/deviantony/docker-elk.git
```
CentOS7 下好像不起作用：
$ chcon -R system_u:object_r:admin_home_t:s0 docker-elk/
```

参考文章：https://www.cnblogs.com/ivictor/p/4834864.html
挂载宿主机已存在目录后，在容器内对其进行操作，报“Permission denied”。

可通过两种方式解决：

1> 关闭selinux。

临时关闭：# setenforce 0

永久关闭：修改/etc/sysconfig/selinux文件，将SELINUX的值设置为disabled。

2> 以特权方式启动容器 

指定--privileged参数

如：# docker run -it --privileged=true -v /test:/soft centos /bin/bash

修改logstash.conf
```json
input {
	tcp {
		port => 5000
	}
## 需要redis中转，filebeat直接吐到logstash有乱码问题，原因待查
redis {
        host => "192.168.1.88"
        port => 7021
        key => "elk:test"
        data_type => "list"
    }
}

filter {
	grok {
		match=>{ "message"=>"%{DATA:service}\|%{DATA:host}\|%{DATA:time}\|\[%{DATA:traceId},%{DATA:parentSpanId},%{DATA:spanId},%{DATA:export}\]\|%{DATA:level}\|%{DATA:threadName}\|%{DATA:logger}\|%{DATA:caller}\|%{DATA:msg}\|"}
	}
  // 覆盖掉@timestamp字段，按照日志打印时间排序
  date {
        match => ["time", "yyyy-MM-dd HH:mm:ss.SSS"]
        target => "@timestamp"
        timezone => "Asia/Shanghai"
    }

}

## Add your filters / logstash plugins configuration here

output {
	elasticsearch {
		hosts => "elasticsearch:9200"
	}
}
```
grok debugger: http://grokdebug.herokuapp.com
grok-patterns：https://github.com/logstash-plugins/logstash-patterns-core/blob/master/patterns/grok-patterns

安装filebeat，并配置filebeat.yml
```yml
filebeat.prospectors:
- type: log
  enabled: true
  paths:
    - /root/publish/startup/*.log
  encoding: utf-8
  # 多行合并的配置，日志必须以“|”结尾
  multiline.pattern: \|$
  multiline.negate: true
  multiline.match: after
#-------------- Redis output --------------
output.redis:
  hosts: ["192.168.1.88"]
  port: 7021
  key: "elk:ztjtest"
```