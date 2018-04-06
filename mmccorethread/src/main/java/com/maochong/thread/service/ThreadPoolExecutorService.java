package com.maochong.thread.service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 手动线程池
 * @author jokin
 * */
public class ThreadPoolExecutorService implements MyThread {

    @Override
    public void executor() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 1; i <= 10; i++) {
            try {
                threadPool.execute(()->{
                    String message = UUID.randomUUID().toString();
                    System.out.println(message);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
