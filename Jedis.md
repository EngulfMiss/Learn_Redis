# Jedis 使用java操作redis
什么是jedis?  
是Redis官方推荐的java连接开发工具 使用java操作redis的中间件  
步骤：  
1. 导入对应的依赖
```xml
<!-- jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.3.0</version>
</dependency>

<!-- fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.62</version>
</dependency>
```
2. 编码测试：
  - 连接redis数据库
  - 操作命令
  - 断开连接


**jedis连接redis出现这个错误  **
```java
Exception in thread "main" redis.clients.jedis.exceptions.JedisConnectionException: Failed connecting to host XXXX:6379
    at redis.clients.jedis.Connection.connect(Connection.java:204)
    at redis.clients.jedis.BinaryClient.connect(BinaryClient.java:100)
    at redis.clients.jedis.Connection.sendCommand(Connection.java:125)
    at redis.clients.jedis.Connection.sendCommand(Connection.java:120)
    at redis.clients.jedis.BinaryClient.multi(BinaryClient.java:523)
    at redis.clients.jedis.BinaryJedis.multi(BinaryJedis.java:1877)
    at com.redis.TestTX.main(TestTX.java:19)
Caused by: java.net.SocketTimeoutException: connect timed out
    at java.net.DualStackPlainSocketImpl.waitForConnect(Native Method)
    at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:85)
    at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
    at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
    at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
    at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:172)
    at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
    at java.net.Socket.connect(Socket.java:589)
    at redis.clients.jedis.Connection.connect(Connection.java:181)
    ... 6 more
```

解决方案：修改redis配置文件  redis.conf(我的是备份的)  
- 注释掉bind 127.0.0.1  
- 修改protected-mode为no


  

## 常用API  (与客户端命令基本相同，只是用对象调用方法)
- String
```java
// 2.操作命令
System.out.println("清空数据:" + jedis.flushDB());
System.out.println("判断某个键是否存在:" + jedis.exists("username"));
System.out.println("新增<'username','kindred'>的键值对" + jedis.set("username","kindred"));
System.out.println("新增<'password','12345678'>的键值对" + jedis.set("password","12345678"));
System.out.println("系统中所有的键如下:");
Set<String> keys = jedis.keys("*");
System.out.println(keys);
System.out.println("删除键password:" + jedis.del("password"));
System.out.println("判断password还是否存在:" + jedis.exists("password"));
System.out.println("查看键username所存储的值的类型:" + jedis.type("username"));
System.out.println("随机返回key空间中的一个:" + jedis.randomKey());
System.out.println("重命名key:" + jedis.rename("username","name"));
System.out.println("用新名字name取值:" + jedis.get("name"));
System.out.println("切换数据库:" + jedis.select(0));
System.out.println("删除当前数据库中所有的key:" + jedis.flushDB());
System.out.println("返回当前数据库中key的个数:" + jedis.dbSize());
System.out.println("删除所有数据库中的所有key:" + jedis.flushAll());
```

- List
- Set
- Hash
- Zset

  
jedis事务操作
```java
    public static void main(String[] args) {

        // fastjson
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello","world");
        jsonObject.put("name","kindred");

        // 1. new Jedis 对象
        Jedis jedis = new Jedis("123.57.8.252", 6379);

        jedis.flushDB();

        //开启事务
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();

        try {
            multi.set("user1",result);
            multi.set("user2",result);
            multi.incrBy("user1",20);   //redis运行期错误

//            int i = 1/0;  //抛出异常

            //如果成功执行事务
            multi.exec();
        } catch (Exception e) {
            //出现异常放弃事务
            multi.discard();   //放弃事务
            e.printStackTrace();
        }finally {
            // 关闭连接
            jedis.close();
        }
        System.out.println(jedis.get("user1"));
        System.out.println(jedis.get("user2"));
    }
```
