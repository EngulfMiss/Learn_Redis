1. 配置文件 unit单位 对于大小写不敏感
```bash
# 1k => 1000 bytes
# 1kb => 1024 bytes
# 1m => 1000000 bytes
# 1mb => 1024*1024 bytes
# 1g => 1000000000 bytes
# 1gb => 1024*1024*1024 bytes
#
# units are case insensitive so 1GB 1Gb 1gB are all the same.
```

2. 包含 include  将其他配置文件包含进来
```bash
# include /path/to/local.conf
# include /path/to/other.conf
```
3. 网络 NETWORK
```bash
bind 127.0.0.1   #  绑定ip
protected-mode yes   #  保护模式
port 6379   #  端口设置
```
4. 通用 GENERAL
```bash
daemonize yes  # 以守护进程方式运行，默认是no，我们需要自己改为yes
pidfile /var/run/redis_6379.pid    #  如果以后台的方式运行，我们就需要指定一个pid文件


# 日志
# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)
# warning (only very important / critical messages are logged)
loglevel notice

# 日志文件位置名
logfile ""

# 数据库的数量
databases 16
```

5. 快照 SNAPSHOTTING
持久化，在规定的时间内，执行了多少次操作，则会持久化到文件  .rdb .aof  
redis是内存数据库，没有持久化，内存断电及失
```bash
save 900 1   # 如果900s内，如果至少有一个 key进行了修改，我们就进行持久化操作
save 300 10   # 如果300s内，如果至少有10个 key进行了修改，我们就进行持久化操作
save 60 10000   # 如果60s内，如果至少有10000个 key进行了修改，我们就进行持久化操作
# 之后学习持久化 可以自己设置

stop-writes-on-bgsave-error yes   # 当bgsave快照操作出错时停止写数据到磁盘

rdbcompression yes   # 是否压缩rdb文件，需要消耗一些cpu资源

rdbchecksum yes    #  保存rdb文件的时候，进行错误的检查

dir ./  # rdb文件保存的目录
```

6. 安全 SECURITY
```bash
requirepass foobared
```

7. 客户端设置 CLIENTS
```bash
maxclients 10000   # 设置能连接上redis的最大客户端数量
```

8. MEMORY MANAGEMENT
```bash
maxmemory <bytes>   # redis 配置最大的内存容量
maxmemory-policy noeviction  # 内存到达上限之后的处理策略
```
  1、volatile-lru：只对设置了过期时间的key进行LRU（默认值） 

  2、allkeys-lru ： 删除lru算法的key   

  3、volatile-random：随机删除即将过期key   

  4、allkeys-random：随机删除   

  5、volatile-ttl ： 删除即将过期的   

  6、noeviction ： 永不过期，返回错误

9. APPEND ONLY MODE  aof配置
```bash
appendonly no   # 默认是不开启aof模式的，默认使用rdb持久化的

appendfilename "appendonly.aof"   # 持久化的文件的名字  .rdb

# appendfsync always
appendfsync everysec       # 每秒执行一次同步
# appendfsync no
```
