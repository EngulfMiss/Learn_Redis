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

# List
在redis里面，我们可以把list玩成，栈，队列，阻塞队列  
**所有的list命令都是用l/r开头的(头/尾插法)**  
```bash
127.0.0.1:6379> clear
127.0.0.1:6379> LPUSH list one     # 将一个值或者多个值，插入到列表头(左)部
(integer) 1
127.0.0.1:6379> LPUSH list two
(integer) 2
127.0.0.1:6379> LPUSH list three
(integer) 3
127.0.0.1:6379> LRANGE list 0 -1   # 获取list中的值
1) "three"
2) "two"
3) "one"


127.0.0.1:6379> RPUSH list2 1       # 将一个值或者多个值，插入到列表尾(右)部
(integer) 1
127.0.0.1:6379> RPUSH list2 2
(integer) 2
127.0.0.1:6379> rpush list2 3
(integer) 3
127.0.0.1:6379> lrange list2 0 -1
1) "1"
2) "2"
3) "3"


127.0.0.1:6379> lpop list    #  移除list的第一个元素
"three"
127.0.0.1:6379> rpop list2   #  移除list2的最后一个元素
"3"
127.0.0.1:6379> lindex list 0   # 获取指定索引的值
"two"
127.0.0.1:6379> lindex list2 0  # 获取指定索引的值
"1"
127.0.0.1:6379> llen list    # 查看list的长度
(integer) 2


127.0.0.1:6379> rpush list kindred gnar neeko
(integer) 3
127.0.0.1:6379> rpush list kindred
(integer) 4
127.0.0.1:6379> lrange list 0 -1
1) "kindred"
2) "gnar"
3) "neeko"
4) "kindred"
127.0.0.1:6379> lrem list 1 neeko       # list 中移除指定的值  数字代表移除多少个
(integer) 1
127.0.0.1:6379> lrem list 2 kindred
(integer) 2
127.0.0.1:6379> lrange list 0 -1
1) "gnar"


127.0.0.1:6379> rpush list gnar kindred neeko
(integer) 3
127.0.0.1:6379> ltrim list 1 2        # 通过下标截取指定元素
OK
127.0.0.1:6379> lrange list 0 -1
1) "kindred"
2) "neeko"



127.0.0.1:6379> lrange list 0 -1
1) "gnar"
2) "kindred"
3) "neeko"
127.0.0.1:6379> rpoplpush list hero    # 移除列表的最后一个元素，将他移动到新的列表中
"neeko"
127.0.0.1:6379> lrange list 0 -1
1) "gnar"
2) "kindred"
127.0.0.1:6379> lrange hero 0 -1
1) "neeko"



127.0.0.1:6379> exists list    # 判断是否存在指定键  
(integer) 0
127.0.0.1:6379> lset list 0 kindred   # 不存在列表就去更新就会报错
(error) ERR no such key
127.0.0.1:6379> rpush gnar kindred neeko
(integer) 2
127.0.0.1:6379> lrange gnar 0 0
1) "kindred"
127.0.0.1:6379> lset gnar 0 Kindred    # 通过指定索引替换值
OK
127.0.0.1:6379> lrange gnar 0 -1
1) "Kindred"
2) "neeko"



127.0.0.1:6379> rpush list gnar kindred neeko
(integer) 3
127.0.0.1:6379> linsert list before kindred qsj    # 将某个具体的value插入到指定元素的前面或者后面  before/after
(integer) 4
127.0.0.1:6379> lrange list 0 -1   
1) "gnar"
2) "qsj"
3) "kindred"
4) "neeko"
127.0.0.1:6379> linsert list after kindred test
(integer) 5
```

# Set(不允许有重复元素)
```bash
127.0.0.1:6379> sadd myset "hello"    # set集合中添加元素
(integer) 1
127.0.0.1:6379> sadd myset "world"
(integer) 1
127.0.0.1:6379> sadd myset "kindred"
(integer) 1


127.0.0.1:6379> smembers myset        #  查看指定set的所有值
1) "kindred"
2) "hello"
3) "world"
127.0.0.1:6379> sismember myset kindred     #   判断某一个值是不是在set集合中
(integer) 1
127.0.0.1:6379> sismember myset gnar
(integer) 0


127.0.0.1:6379> scard myset         #   获取set集合中的内容元素个数
(integer) 3


127.0.0.1:6379> srem myset kindred    #  从set集合中移除指定元素
(integer) 1
127.0.0.1:6379> scard myset
(integer) 2
127.0.0.1:6379> smembers myset
1) "hello"
2) "world"


127.0.0.1:6379> sadd myset kindred gnar neeko
(integer) 3
127.0.0.1:6379> scard myset
(integer) 5
127.0.0.1:6379> srandmember myset    # 从set集合中随机抽出一个元素  可以在后面再加一个个数参数
"kindred"


127.0.0.1:6379> spop myset      # 随机删除一个set集合中的元素
"kindred"


127.0.0.1:6379> sadd myset hello world kindred gnar neeko
(integer) 5
127.0.0.1:6379> sadd myset2 set2
(integer) 1
127.0.0.1:6379> smove myset myset2 kindred        #  将一个指定的值从一个集合移动到另一个集合
(integer) 1
127.0.0.1:6379> smembers myset
1) "gnar"
2) "hello"
3) "world"
4) "neeko"
127.0.0.1:6379> smembers myset2
1) "kindred"
2) "set2"
```
