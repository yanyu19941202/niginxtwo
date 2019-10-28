package com.example.nginxtwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class MqThread extends  Thread{

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MqHandler mqHandler;


    @Override
    public void run() {
        Jedis jedis = jedisPool.getResource();

        System.out.println("channel1...");
        jedis.subscribe(mqHandler, "channel1", "channel2");

    }

}
