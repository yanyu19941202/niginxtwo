package com.example.nginxtwo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.*;


@Component
public class ThreadPoolUtil {

    @Autowired
    JedisPool jedisPool;

    private static ThreadPoolUtil threadPool;
    private ThreadPoolExecutor executor;

    private TimeUnit unit = TimeUnit.SECONDS;

    public ThreadPoolUtil() {

        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        int corePoolSize = 10;//核心线程数
        long keepAliveTime = 60;//闲置过期时间
        int maximumPoolSize = 15;//最大线程数

        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("hbase-thread-%d").build();

        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("线程池初始化成功");
    }

    public static ThreadPoolUtil init() {
        if (threadPool == null) {
            threadPool = new ThreadPoolUtil();
        }
        return threadPool;
    }

    public void awaitTermination() throws InterruptedException {
        long timeout = 10;
        executor.awaitTermination(timeout, unit);
    }

    public void execute(Runnable t) {
        executor.execute(t);
    }


    public void execute(Thread t) {
        executor.execute(t);
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }

    public void shutdown() {

        System.out.println("shutdown--------------------");
        getExecutor().shutdown();
    }

    private ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public Future<?> submit(Runnable t) {
        return executor.submit(t);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Future<?> submit(Callable t) {
        return getExecutor().submit(t);
    }


}
