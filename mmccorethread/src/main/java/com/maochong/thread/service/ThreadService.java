package com.maochong.thread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 线程Thread Callable Runnable
 * */
public class ThreadService implements MyThread {

    private final Logger logger = LoggerFactory.getLogger(ThreadService.class);
    @Override
    public void executor() {
        // Thread Runnable
        Thread thread = new Thread(()->{
            String message = UUID.randomUUID().toString();
            System.out.println(message);
        });
        thread.start();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        // Callable
        try {
            FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    // do something
                    return 0;
                }
            });
            Thread thread1 = new Thread(futureTask);
            thread1.start();
            // 获取返回值
            Integer result = futureTask.get();
            System.out.println(result);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }


    }
}
