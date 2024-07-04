package com.example.taihovue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testSet(){
        // 往redis中存储一个键值对 StringRedisTemplate
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("username","zhangchenxu");
    }
    @Test
    public void testGet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
    }
}
