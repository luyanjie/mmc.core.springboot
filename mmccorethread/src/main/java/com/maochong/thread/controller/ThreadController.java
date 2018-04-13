package com.maochong.thread.controller;

import com.maochong.thread.service.lock.ReentrantLockService;
import com.maochong.thread.service.lock.ReentrantReadWriteLockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jokin
 * @date 2018-04-06
 * */
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @RequestMapping("/reentrant/lock")
    public String reentrantReadWriteLock()
    {
        /**
         * 输出结果
         * Sat Apr 07 10:36:15 CST 2018: block by main write lock start
         Sat Apr 07 10:36:20 CST 2018: block by main write lock end
         Sat Apr 07 10:36:20 CST 2018   线程执行：threadName0
         Sat Apr 07 10:36:20 CST 2018   线程执行：threadName1
         Sat Apr 07 10:36:20 CST 2018   线程执行：threadName2
         -------------TestThread write start---------------
         Sat Apr 07 10:36:23 CST 2018   线程执行：threadName3
         --------------TestThread write end---------------
         Sat Apr 07 10:36:26 CST 2018   线程执行：threadName4
         Sat Apr 07 10:36:26 CST 2018   线程执行：threadName5
         Sat Apr 07 10:36:26 CST 2018   线程执行：threadName6
         -------------TestThread write start---------------
         Sat Apr 07 10:36:29 CST 2018   线程执行：threadName7
         --------------TestThread write end---------------
         Sat Apr 07 10:36:32 CST 2018   线程执行：threadName8
         Sat Apr 07 10:36:32 CST 2018   线程执行：threadName9
         * */
       new ReentrantReadWriteLockService().new Main().blockByWriteLock();
       return "success";
    }

    @RequestMapping("/reentrant/lock/fair")
    public String reentrantLock()
    {
        //https://blog.csdn.net/mrsyf/article/details/78279178
        System.out.println("-------------公平锁----------");
        ReentrantLockService.Fair fair = new ReentrantLockService().new Fair();
        // 公平锁
        Runnable runnable = () -> {
            System.out.println("开始运行的线程名称：" + Thread.currentThread().getName());
            fair.serviceMethod();
        };
        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
        return "success";
    }

    @RequestMapping("/reentrant/lock/fair/no")
    public String reentrantLockNoFair()
    {
        System.out.println("-------------非公平锁----------");
        ReentrantLockService.NoFair noFair = new ReentrantLockService().new NoFair();

        // 非公平锁
        Runnable runnable = () -> {
            System.out.println("开始运行的线程名称：" + Thread.currentThread().getName());
            noFair.serviceMethod();
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++)
        {
            executorService.submit(runnable);
        }
        executorService.shutdown();
//        Thread[] threadArray = new Thread[10];
//        for (int i = 0; i < 10; i++) {
//            threadArray[i] = new Thread(runnable);
//        }
//
//        for (int i = 0; i < 10; i++) {
//            threadArray[i].start();
//        }
        return "success";
    }
}
