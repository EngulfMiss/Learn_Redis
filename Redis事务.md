# 事务
Redis事务本质：一组命令的集合  
Redis单条命令保证原子性的，但是事务不保证原子性  
Redis事务没有隔离级别概念  
redis事务：  
- 开启事务(multi)
- 命令入队(其他命令...)
- 执行事务(exec)
- 放弃事务(discard)

```bash
127.0.0.1:6379> multi   # 开启事务
OK
127.0.0.1:6379> set k1 v1    # 编写命令
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> get k2
QUEUED
127.0.0.1:6379> set k3 v3
QUEUED
127.0.0.1:6379> exec   # 执行事务
1) OK
2) OK
3) "v2"
4) OK 
127.0.0.1:6379> multi       # 开启事务
OK
127.0.0.1:6379> set k1 v1
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> set k4 v4
QUEUED
127.0.0.1:6379> discard    # 放弃事务  事务队列中的命令都不会执行了
OK
127.0.0.1:6379> get k4
(nil)
```

redis事务出现异常
- 编译型异常(代码有问题，命令有错),事务中的所有命令都不会执行
- 运行时异常(1/0)，如果事务队列中存在语法性错误，那么执行命令的时候，其他命令可以正常执行，错误命令抛出异常

```bash
# 编译器错误
127.0.0.1:6379> multi          # 开启事务
OK
127.0.0.1:6379> set k1 v1
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> set k3 v3
QUEUED
127.0.0.1:6379> getset k3      # 输入一个错误的命令
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379> set k4 v4
QUEUED
127.0.0.1:6379> set k5 v5
QUEUED
127.0.0.1:6379> exec           # 执行事务
(error) EXECABORT Transaction discarded because of previous errors.
127.0.0.1:6379> get k1         # 发现一个命令都没成功执行
(nil)


# 运行时错误
127.0.0.1:6379> multi          # 开启事务
OK
127.0.0.1:6379> set k1 v1
QUEUED
127.0.0.1:6379> incr k1        # 一条编译不报错的错误逻辑命令 - 给字符串自增
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> set k3 v3
QUEUED
127.0.0.1:6379> get k2
QUEUED
127.0.0.1:6379> exec          # 执行事务
1) OK
2) (error) ERR value is not an integer or out of range       # 发现只有那条错误命令没有被执行
3) OK
4) OK
5) "v2"
```
