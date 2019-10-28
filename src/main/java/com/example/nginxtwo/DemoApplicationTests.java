package com.example.nginxtwo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoApplicationTests {

    @Autowired
    RedisConfig redisConfig;

   @RequestMapping(value = "/send")


    public String publishData(String msg) throws IOException {


        //byte[] redisKey = "key".getBytes();

       Jedis jedis = redisConfig.getJedisPool().getResource();
       //广播
        //jedis.publish("channel1", msg);
        //LIST
       jedis.lpush("order:key",msg);

       System.out.println( "       jedis.rpop(\"order:key\")\n"+      jedis.rpop("order:key"));
       /*   for (int i = 0; i <10 ; i++) {
           jedis.lpush("test"+i,msg+i);
      }
*/
       return  "接受消息成功";
    }

}