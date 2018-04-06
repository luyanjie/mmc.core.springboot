package com.maochong.thread.service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 系统提供的四种线程池
 * @author jokin
 * @date 2018-04-06
 * */
public class ExecutorsService implements MyThread {
    @Override
    public void executor() {
        //使用Executors创建线程池（最后使用ThreadPoolExecutor 手动创建线程池）
        ExecutorService cachedThreadPool = newFixedThreadPool(10);
        //ThreadFactory threadFactory = new ThreadFactoryBuilder()


        for (int i = 0; i < 10; i++) {
            cachedThreadPool.execute(() ->{
                String message = UUID.randomUUID().toString();
                System.out.println(message);
            });
        }
        cachedThreadPool.shutdown();

    }

    /**
     * 创建固定数目线程的线程池。
     * */
    public static ExecutorService newFixedThreadPool(int nThreads)
    {
        return Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 创建一个可缓存的线程池，调用execute将重用以前构造的线程（如果线程可用）。
     * 如果现有线程没有可用的，则创建一个新线 程并添加到池中。
     * 终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
     * */
    public static ExecutorService newCachedThreadPool()
    {
        return Executors.newCachedThreadPool();
    }

    /**
     * 创建一个单线程化的Executor。
     * */
    public static ExecutorService newSingleThreadExecutor()
    {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * 创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类。
     * */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
    {
        return Executors.newScheduledThreadPool(corePoolSize);
    }
}
