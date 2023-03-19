package com.myf.wind.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestRedis {
    @Autowired
    RedisTemplate redisTemplate;

    public void testRedis(){
        redisTemplate.opsForValue().set("hello","hello China");
        System.out.println(redisTemplate.opsForValue().get("hello"));
    }
}
