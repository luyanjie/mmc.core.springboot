package com.maochong.thread.controller;

import com.maochong.thread.entity.ThreadLocalResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/thread/local")
public class ThreadLocalController {

    @RequestMapping("/hello")
    public void threadLocalHello()
    {
        ThreadLocalResponse threadLocalResponse = new ThreadLocalResponse();

        System.out.println(threadLocalResponse);
        final int count = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i=0;i<count;i++)
        {
            executorService.submit(()->{
                System.out.println(Thread.currentThread().getName());
               ThreadLocal<ThreadLocalResponse> tl = new ThreadLocal<>();
                System.out.println("tl:"+tl);
                tl.set(threadLocalResponse);
                System.out.println("tl.get():"+tl.get());
                tl.remove();
            });
        }
        executorService.shutdown();
    }
}
