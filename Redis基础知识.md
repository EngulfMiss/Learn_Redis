# Redis基础知识
- redis默认有16个数据库，默认使用第0个数据库，可以使用select进行切换
```bash
127.0.0.1:6379> select 3  # 切换数据库
OK
127.0.0.1:6379[3]> DBSIZE  # 查看DB大小
(integer) 0
127.0.0.1:6379[3]> keys *  # 查看所有的key
1) "name"
127.0.0.1:6379[3]> flushdb # 清空当前数据库  FLUSHALL清除全部数据库的内容
```

- Redis是单线程的
Redis是基于内存操作的，CPU不是Redis性能瓶颈，Redis的瓶颈是根据机器的内存和网络带宽，单线程就可以实现  

Redis为什么单线程还这么快?  
Redis是C语言写的  
1. 误区1：高性能的服务器一定是多线程的  
2. 误区2：多线程(CPU上下文会切换)一定比单线程效率高  
核心：redis是将所有的数据全部放在内存中的，所以说使用单线程去操作效率就是最高的，多线程(CPU上下文会切换很耗时)  
对内存系统来说，没有上下文切换效率就是最高的
