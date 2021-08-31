# Redis发布订阅
Redis发布订阅(pub/sub)是一种消息通信模式：发送者(pub)发送消息，订阅者(sub)接收消息.  
Redis客户端可以订阅任意数量的频道   
订阅/发布消息图：  
第一个：消息发送者，第二个：频道，第三个：消息订阅者
![image](https://user-images.githubusercontent.com/61497283/131473560-011da4b9-b86e-4d6a-9a77-904c96826b95.png)

- psubscribe [pattern] : 订阅一个或多个符合给定模式的频道
- pubsub subcommand [argument...]: 查看订阅与发布系统状态
- publish channel message : 向一个频道发送一个消息
- punsubscribe [pattern] : 退订所有给定模式的频道
- subscribe [channel...] : 订阅一个或多个频道的信息
- unsubscribe [channel...] : 退订给定的频道 

```bash
127.0.0.1:6379> subscribe kindred              #  订阅一个频道 subscribe [channel...]
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "kindred"
3) (integer) 1
1) "message"
2) "kindred"
3) "hello,kindred"
1) "message"
2) "kindred"
3) "hello,gnar"
```

```bash
127.0.0.1:6379> publish kindred "hello,kindred"    #  发布端发送消息
(integer) 1
127.0.0.1:6379> publish kindred "hello,gnar"
(integer) 1
```
