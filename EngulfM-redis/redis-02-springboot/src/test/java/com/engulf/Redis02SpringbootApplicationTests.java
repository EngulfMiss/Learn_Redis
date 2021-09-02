package com.engulf;

import com.engulf.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
        // redisTemplate  操作不同的数据类型
        // opsForValue  操作字符串  类似String
        // opsForList   操作字符串  类似List
        // opsForXxx    类似Xxx
        redisTemplate.opsForValue().set("name","千珏");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);


        // 除了基本操作，常用的方法都可以直接通过redisTemplate操作，比如事务
        // redisTemplate.multi();
//        redisTemplate.multi();

        //获取redis连接对象，操作数据库，比如flushDb
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();

    }

    @Test
    public void test() throws JsonProcessingException {
        User user = new User("永猎双子", 1500);
//        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
