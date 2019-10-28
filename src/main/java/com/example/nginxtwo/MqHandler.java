package com.example.nginxtwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Component
public class MqHandler extends JedisPubSub {
    @Autowired
    RedisConfig redisConfig;
    @Override
    public void onMessage(String channel, String message) {
        //广播
        System.out.println(">> 接收到了来自 " + channel + " 的消息： " + message);

        Jedis jedis = redisConfig.getJedisPool().getResource();

        System.out.println("第二主机"+ jedis.rpop("order:key"+message));

    }

}