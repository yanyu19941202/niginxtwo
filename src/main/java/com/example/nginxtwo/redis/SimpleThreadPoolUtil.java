package com.example.nginxtwo.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class SimpleThreadPoolUtil {
    private static final Map<PoolType, ExecutorService> POOLS = new HashMap<>();

    public enum PoolType{
        TEST_THREAD_POOL
    }

    static {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("threadFacotry-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        POOLS.put(PoolType.TEST_THREAD_POOL,pool);
    }

    /**
     * MethodName: getThreadPool
     * param: type 线程池类型
     * return:
     * describe: 获取线程池
     **/
    public static ExecutorService getThreadPool(PoolType type){
        return POOLS.get(type);
    }


    /**
     * MethodName: submit
     * param: task 异步任务，type 线程池类型
     * return:  futuretask
     * describe: 根据指定类型线程池运行一个异步任务
     **/
    public static <T> Future<T> submit(Callable<T> task,PoolType type){

        ExecutorService pool = POOLS.get(type);

        if (pool == null || pool.isShutdown()){
            throw new IllegalArgumentException("pool not exists or had been shut down type:"+type);
        }

        return  pool.submit(task);
    }
}
