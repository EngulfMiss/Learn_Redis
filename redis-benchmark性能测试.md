# benchmark
```bash
# 测试：50个并发连接 10000请求
[root@EngulfMissing /]# cd /usr/local/bin
[root@EngulfMissing bin]# redis-benchmark -h localhost -p 6379 -c 50 -n 10000

# set测试为例
====== SET ======
  10000 requests completed in 0.14 seconds  # 10000个请求
  50 parallel clients   #  50个并发客户端
  3 bytes payload    # 每次写入3个字节
  keep alive: 1      # 只有一台服务器来处理这些请求，单机性能

99.51% <= 1 milliseconds
100.00% <= 1 milliseconds
73529.41 requests per second    # 每秒处理量
```
