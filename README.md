# Learn_Redis
学习Redis

### 什么是Redis?
Redis(Remote Dictionary Server),即远程字典服务,是C语言编写开源的，可基于内存亦可持久化的日志型，key-value数据库。

### Redis能干嘛?
1. 内存存储，持久化，内存中是断电即失，所以需要持久化(rdb,aof)
2. 效率高，可以用于高速缓存
3. 发布订阅系统
4. 地图信息分析
5. 计时器，计数器
6. ...

### Redis特性
1. 多样的数据类型
2. 持久化
3. 集群
4. 事务

### Redis安装
1. 下载安装包
2. 解压Redis的安装包  建议移动到opt目录下  mv redis-5.0.13.tar.gz /opt
3. 进入解压后的文件，可以看到redis的配置文件
4. 基本环境安装  要进入redis文件中
```bash
[root@EngulfMissing redis-5.0.13]# yum install gcc-c++

[root@EngulfMissing redis-5.0.13]# make

[root@EngulfMissing redis-5.0.13]# make install
```
5. redis的默认安装路径 /usr/local/bin
6. 将redis配置文件，复制到我们当前目录下
```bash
[root@EngulfMissing bin]# mkdir rconfig
[root@EngulfMissing bin]# cp /opt/redis-5.0.13/redis.conf rconfig   # 这里的bin是/usr/local/bin
```
7. redis默认不是后台启动的，修改配置文件,将redis.conf中的  daemonize改为yes
```bash
[root@EngulfMissing rconfig]# vim redis.conf
```
8. 启动redis服务，在/usr/local/bin目录下启动redis服务
```bash
[root@EngulfMissing bin]# pwd
/usr/local/bin
[root@EngulfMissing bin]# redis-server rconfig/redis.conf
```
9. 使用redis-cli 连接指定端口号测试
```bash
[root@EngulfMissing bin]# redis-cli -p 6379
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> set name Mild.Lamb
OK
127.0.0.1:6379> get name
"Mild.Lamb"
127.0.0.1:6379> keys *
1) "name"
```
10. 查看redis进程是否开启
```bash
[root@EngulfMissing /]# ps -ef|grep redis
```
11. 关闭redis服务
```bash
127.0.0.1:6379> shutdown
not connected> exit
```
