package com.example.nginxtwo.redis;

import com.example.nginxtwo.MqThread;
import com.example.nginxtwo.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
public class MyRedisTest {

    @Autowired
    RedisConfig redisConfig;
    @Autowired
    JedisPool jedisPool;


    private static BuyQueue<BuyRequest> buyqueue =null;//线程安全的请求队列

    @RequestMapping(value = "/addorder")
    public  Object addorder(BuyRequest buyrequest) throws Exception {
        Map<String, Object> results = new HashMap<>();
        Jedis jedis = jedisPool.getResource();
        //jedis.set("rw", "1001");
        //jedis.decrBy("rw", 100);
        //jedisPool.close();
        //下订单之前，先获取商品的剩余数量
            int residue = Integer.valueOf(jedis.get("rw"));
        System.out.println("商品的剩余数量"+residue);
            if (residue < 1) {  //如果剩余数量不足，直接响应客户端“卖完了”
                results.put("msg", "卖完了");
                results.put("done", false);
                return results;
            }

        //如果还有剩余商品,就准备将请求放到请求队列中
       if(buyqueue==null){//第一次初始化请求队列,队列的容量为当前的商品剩余数量
            buyqueue=new BuyQueue<BuyRequest>(residue);
        }
        System.out.println("buyqueue.remainingCapacity()"+buyqueue.remainingCapacity());
        if(buyqueue.remainingCapacity()>0){//当队列的可用容量大于0时,将请求放到请求队列中
            buyqueue.put(buyrequest);
        }

      else{//当请求队列已满,本次请求不能处理,直接响应客户端提示请求队列已满
            results.put("msg", "抢购队列已满，请稍候重试！");
            results.put("done", false);
            return results;
        }

        if(!DealQueueThread.excute){//如果线程类的当前执行标志为未执行,即空闲状态,通过线程池启动线程
            DealQueueThread dealQueue = new DealQueueThread(buyqueue);
            Future<?> submit = new ThreadPoolUtil().submit(dealQueue);
             System.out.println("future--------------"+submit.get());
            //threadPoolUtil.submit(dealQueue);
        }
        //请求放入到队列中，即完成下单请求
        results.put("done", true);
        results.put("msg", "下订单成功");

        //jedisPool.returnResource(jedis);
        jedis.close();
        return results;
}


}




