package com.example.nginxtwo.redis;

import com.example.nginxtwo.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Component
public class DealQueueThread implements Runnable {

    private static DealQueueThread dealQueueThread;
    @Autowired
    private ThreadPoolUtil threadPoolUtil;
    @Autowired
    JedisPool jedisPool;

    private Jedis jedis;

    private BuyQueue<BuyRequest> buyqueue;

    public static boolean excute = false;//线程的默认执行标志为未执行,即空闲状态

    public DealQueueThread() {

    }

    public DealQueueThread(BuyQueue<BuyRequest> buyqueue) {
        this.buyqueue = buyqueue;
        jedis = dealQueueThread.jedisPool.getResource();
    }

    @PostConstruct
    public void init() {
        dealQueueThread = this;

        dealQueueThread.jedisPool = this.jedisPool;
    }

    @Override
    public void run() {
        try {

            System.out.println("dealQueueThread-------------------run");
            excute = true;//修改线程的默认执行标志为执行状态
            //开始处理请求队列中的请求,按照队列的FIFO的规则,先处理先放入到队列中的请求
            while (buyqueue != null && buyqueue.size() > 0) {
                BuyRequest buyreq = buyqueue.take();//取出队列中的请求
                dealWithQueue(buyreq);//处理请求
            }
        } catch (InterruptedException e) {
        } finally {
           excute = false;
        }
    }

    public synchronized void dealWithQueue(BuyRequest buyreq) {
        try {
            //为了尽量确保数据的一致性,处理之前先从redis中获取当前抢购商品的剩余数量
            int residue = Integer.valueOf(jedis.get("rw"));

            System.out.println("dealWithQueue"+residue);
            if (residue < 1) {//如果没有剩余商品,就直接返回
                buyreq.setResponse_status(3);
                return;
            }
            //如果有剩余商品,先在redis中将剩余数量减一,再开始下订单
            jedis.decrBy("rw",1);
            int num = Integer.valueOf(jedis.get("rw"));
            jedis.close();
            System.out.println("减少之后的"+num);
            //将数据库中将剩余数量减一,这一步处理可以在队列处理完成之后一次性更新剩余数量
          /*  if(num<0){
                System.out.println("close....................");
                jedis.close();
             }*/
            //jedis.close();

            //处理请求,下订单
            //threadPoolUtil.shutdown();
        } catch (Exception e) {
            buyreq.setResponse_status(2);//异常状态

        }
    }



}
