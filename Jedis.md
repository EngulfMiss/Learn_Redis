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
出现这个错误  
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


  - 操作命令
  - 断开连接
