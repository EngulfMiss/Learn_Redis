package com.engulf;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTx {
    public static void main(String[] args) {

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
}
