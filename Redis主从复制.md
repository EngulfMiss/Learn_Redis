# 概念
主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称为主节点(masterleader)，  
后者称为从节点(slave/follower);数据的复制是单向的，只能由主节点到从节点。Master以写为主，Slave以读为主。  

默认情况下，每台Redis服务器都是一个主节点;  
且一个主节点可以有多个从节点，但一个从节点只能有一个主节点

默认情况下，每台Redis服务器都是主节点;且一个主节点可以有多个从节点(或没有从节点)，但一个从节点只能有一个主节点。主从复制的作用主要包括:  

1. 数据冗余︰主从复制实现了数据的热备份，是持久化之外的一种数据冗余方式。
2. 故障恢复∶当主节点出现问题时，可以由从节点提供服务，实现快速的故障恢复;实际上是一种服务的冗余。
3. 负载均衡︰在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务(即写Redis数据时应用连接主节点，读Redis数据时应用  
连接从节点），分担服务器负载﹔尤其是在写少读多的场景下，通过多个从节点分担读负载，可以大大提高Redis服务器的并发量。
4. 高可用基石∶除了上述作用以外，主从复制还是哨兵和集群能够实施的基础，因此说主从复制是Redis高可用的基础。


### 环境配置
只需要配置从库，不需要配置主库  
```bash
127.0.0.1:6379> info replication   # 查看当前库信息
# Replication
role:master          #  角色 master
connected_slaves:0   #  从机数量
master_replid:73f102c1a43323b769c444463fb7e64bcf5bc9b2
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

```

- 复制三个配置文件，然后修改对应的信息
  - 端口
  - pidfile名字
  - logfile名字
  - dump.rdb
修改完毕后，使用三个不同的配置文件启动三个不同的服务，可以通过进程信息查看

### 一主二从
默认情况下，每台Redis服务器都是主节点：我们一般情况下只用配置从机就好了
从机配置
```bash
127.0.0.1:6380> slaveof 127.0.0.1 6379    #  找 哪个ip的哪个端口当主机
OK
127.0.0.1:6380> info replication          # 再次查看 角色变了
# Replication
role:slave
master_host:127.0.0.1
master_port:6379
master_link_status:up
master_last_io_seconds_ago:4
master_sync_in_progress:0
slave_repl_offset:56
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:5d1377e6abaf75701b8beb3c90587da20075689b
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:56
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:56
```

主机中查看 从机
```bash
127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:1
slave0:ip=127.0.0.1,port=6380,state=online,offset=84,lag=0
master_replid:5d1377e6abaf75701b8beb3c90587da20075689b
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:84
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:84
```

真实的主从配置是在配置文件中配置，这样的话是持久的，我们这里使用的是命令，暂时的

**细节**  
主机可以写，从机不能写只能读。主机中的所有信息和数据，都会自动被从机保存
```bash
127.0.0.1:6380> set k1 v1
(error) READONLY You can't write against a read only replica.
```

测试：主机断开连接，从机依旧连接到主机的，从机依旧没有写操作，如果主机重新连接，从机依旧可以获取主机新写入的信息  
如果是使用命令行(slaveof)来配置的主从关系，这个时候从机如果重启了就会变成主机。如果重新变为主机的从机任然可以获取数据  
### 复制原理
Slave启动成功连接到master后会发送一个sync同步命令  
Master接到命令，启动后台的存盘进程，同时收集所有收到的用于修改数据的命令，在后台进程执行完毕后，master将传送整个数据文件  
到slave，并完成一次完全同步  
- 全量复制：slave服务在收到数据集库文件数据后，将其存盘并加载到内存中  
- 增量复制：Master继续将新的所有收集到的修改命令依次传给slave，完成同步  
- 只要是重新连接master，就会自动执行一次完全同步(全量复制)
