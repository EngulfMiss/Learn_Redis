# Redis-Key
```bash
127.0.0.1:6379> keys *   # 查询所有的key
(empty list or set)
127.0.0.1:6379> set name kindred  # 设置一个key
OK
127.0.0.1:6379> set age 1500
OK
127.0.0.1:6379> keys *
1) "age"
2) "name"
127.0.0.1:6379> exists name   # 是否存在key
(integer) 1
127.0.0.1:6379> move name 1   # 移动一个key到另一个库
(integer) 1
127.0.0.1:6379> set name kindred
OK
127.0.0.1:6379> keys *
1) "age"
2) "name"
127.0.0.1:6379> get name
"kindred"
127.0.0.1:6379> expire name 5  # 设置key的过期时间
(integer) 1
127.0.0.1:6379> ttl name
(integer) 1
127.0.0.1:6379> ttl name
(integer) -2
127.0.0.1:6379> del name    # 删除key
127.0.0.1:6379> type age    # 查看key的数据类型
```
