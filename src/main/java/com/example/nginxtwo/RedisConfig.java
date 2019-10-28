package com.example.nginxtwo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisBean redisBean;

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisBean.getMaxIdle());
        config.setMaxTotal(redisBean.getMaxActive());
        config.setMaxWaitMillis(redisBean.getMaxWait());

        String password = redisBean.getPassword();
        if (password == null || "".equals(password)) {
            password = null;
        }
        //10000为超时时间
        JedisPool pool = new JedisPool(config, redisBean.getHost(), redisBean.getPort(),
                10000,
                password);
        System.out.println(">> redis初始化"+redisBean.getHost()+redisBean.getPort());
        return pool;
    }

}
