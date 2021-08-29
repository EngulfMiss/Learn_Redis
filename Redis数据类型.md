# String
- 追加字符串 append
- 获取字符串长度 strlen

```bash
127.0.0.1:6379> set key1 v1
OK
127.0.0.1:6379> get key1
"v1"
127.0.0.1:6379> keys *
1) "key1"
127.0.0.1:6379> 
127.0.0.1:6379> EXISTS key1  # 是否存在key
(integer) 1
127.0.0.1:6379> APPEND key1 "hello"  # 追加内容
(integer) 7
127.0.0.1:6379> get key1
"v1hello"
127.0.0.1:6379> strlen key1  # 获取字符串长度
(integer) 7

```

- 增加 incr key
- 减少 decr key
- 增加指定步长 incrby key 步长
- 减少指定步长 decrby key 步长

```bash
127.0.0.1:6379> set views 0
OK
127.0.0.1:6379> get views
"0"
127.0.0.1:6379> incr views
(integer) 1
127.0.0.1:6379> incr views
(integer) 2
127.0.0.1:6379> decr views
(integer) 1
127.0.0.1:6379> decr views
(integer) 0
127.0.0.1:6379> incrby views 20
(integer) 20
127.0.0.1:6379> decrby views 10
(integer) 10
```

- 截取字符串 getrange key start end
- 截取替换字符串 setrange key start newString     从哪开始 替换成什么
```bash
127.0.0.1:6379> set key1 "hello,kindred"
OK
127.0.0.1:6379> GETRANGE key1 6 13
"kindred"
127.0.0.1:6379> GETRANGE key1 6 -1
"kindred"
127.0.0.1:6379> set key2 gnar
OK
127.0.0.1:6379> setrange key2 1 uard
(integer) 5
127.0.0.1:6379> get key2
"guard"
```

- 设置过期时间  setex
- 当前值不存在才设置  setnx

```bash
127.0.0.1:6379> setex key3 20 "gnardada"  # 设置key3 的值为 gnardada ，20秒后过期
OK
127.0.0.1:6379> ttl key3
(integer) 13
127.0.0.1:6379> ttl key3
(integer) 8
127.0.0.1:6379> ttl key3
(integer) 7
127.0.0.1:6379> ttl key3
(integer) 7
127.0.0.1:6379> ttl key3
(integer) 5
127.0.0.1:6379> get key3
(nil)
127.0.0.1:6379> ttl key3
(integer) -2
127.0.0.1:6379> setnx mykey "redis"  # 如果mykey 不存在，创建mykey
(integer) 1
127.0.0.1:6379> keys *
1) "key2"
2) "key1"
3) "mykey"
127.0.0.1:6379> setnx mykey "MongoDB"  # 如果mykey存在，创建失败
(integer) 0
```

- 批量设置值 mset
- 批量获取值 mget
```bash
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3  # 同时设置多个值
OK
127.0.0.1:6379> keys *
1) "k2"
2) "k3"
3) "k1"
127.0.0.1:6379> msetnx k1 v1 k4 v4  #msetnx 是一个原子性操作，要么一起成功，要么一起失败
(integer) 0
127.0.0.1:6379> keys *
1) "k2"
2) "k3"
3) "k1"
127.0.0.1:6379> mget k1 k2 k3   # 同时获取多个值
1) "v1"
2) "v2"
3) "v3"
127.0.0.1:6379> set user:1 {name:kindred,age:1500}   # redis存放对象的常用方式  存一个json字符串
OK
127.0.0.1:6379> mset user:1:name gnar user:1:age 8   # key的巧妙设计 {ObjectName}:{id}:{filed}
OK
127.0.0.1:6379> mget user:1:name user:1:age
1) "gnar"
2) "8"
```

- 先获取一次再设置 getset key value
```bash
127.0.0.1:6379> getset db "database"  # 不存在返回nil，再设置新值
(nil)
127.0.0.1:6379> getset db "mysql"     # 有值先返回值，再设置值
"database"
127.0.0.1:6379> get db
"mysql"
```
