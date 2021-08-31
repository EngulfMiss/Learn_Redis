# RDB
rdb保存的文件是 dump.rdb  
触发机制：
1. save的规则满足的情况下，会自动触发rdb规则
2. 执行flushall命令，也会触发我们的rdb规则
3. 退出redis服务也会执行save

如何恢复rdb文件  
1. 只需要将rdb文件放在wmredis启动目录就可以，redis启动的时候会自动检查dump.rdb恢复其中数据
2. 查看需要存放的位置

优点：  
1. 适合大规模的数据恢复
2. 如果队数据完整性要求不高

缺点：  
1. 需要一定的时间间隔进行操作，如果redis意外宕机，会丢失最后一次修改的数据
2. fork进程的时候，会占用一定的内存空间


# AOF(Append Only File)
将我们的所有命令都记录下来，history，恢复的时候就把这个文件再执行一遍  
aof保存的文件是  appendonly.aof  
redis.conf 中默认是不开启的，需要手动开启   appendonly yes  
如果aof文件有错误，这时候redis是启动不了的，我们需要修复aof文件  
redis 给我们提供了一个工具  redis-check-aof
```bash
[root@iZ2zedwea74ejj95zb9sh0Z bin]# redis-check-aof --fix appendonle.aof
```

优点：  
1. 每一次修改都同步，文件完整性会更好
2. 默认每秒同步一次

缺点：  
1. 相对于数据rdb来说，aof大小远大于人多不，修复速度也比rdb慢
2. aof运行效率比rdb慢，所以redis默认的配置也是rdb，aof需要去手动开启
