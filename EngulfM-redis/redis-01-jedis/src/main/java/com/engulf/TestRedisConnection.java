package com.engulf;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class TestRedisConnection {
    public static void main(String[] args) {
        // 1. new Jedis 对象
        Jedis jedis = new Jedis("123.57.8.252", 6379);

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
    }
}
