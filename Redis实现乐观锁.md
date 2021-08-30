# Redis实现乐观锁
**悲观锁**  
- 认为什么时候都可能出现并发问题，无论做什么都会加锁

**乐观锁**  
- 认为什么时候都不会出现并发问题，无论做什么都不会加锁。更新数据的时候会去判断，在此期间是否有人修改过这个数据

redis实现乐观锁  
- 获取version
- 更新的时候比较version

redis监视测试  
```bash
127.0.0.1:6379> set money 100
OK
127.0.0.1:6379> set out 0
OK
127.0.0.1:6379> watch money     # 监视money对象
OK
127.0.0.1:6379> multi           # 事务正常结束，在这期间数据没有发生改变，这时正常执行成功
OK
127.0.0.1:6379> decrby money 20
QUEUED
127.0.0.1:6379> incrby out 20
QUEUED
127.0.0.1:6379> exec
1) (integer) 80
2) (integer) 20


127.0.0.1:6379> watch money    # 监视money对象
OK
127.0.0.1:6379> multi          # 开启事务
OK
127.0.0.1:6379> decrby money 10
QUEUED
127.0.0.1:6379> incrby out 10
QUEUED
127.0.0.1:6379> exec           # 在执行事务之前，使用另一个redis客户端对money的值进行了修改
(nil)


127.0.0.1:6379> unwatch        #  版本不一致了，重新监视money
OK
127.0.0.1:6379> watch money
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379> decrby money 10
QUEUED
127.0.0.1:6379> incrby out 10
QUEUED
127.0.0.1:6379> exec
1) (integer) 140
2) (integer) 30
```
