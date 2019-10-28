package com.example.nginxtwo;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class testRedis {

    @Autowired
    RedisConfig redisConfig;


    public   void publishData() {
        Jedis jedis = redisConfig.getJedisPool().getResource();
        jedis.publish("channel1", "message!");

    }

    public static  void main(String[] args) {
           new testRedis().publishData();
    }
}
