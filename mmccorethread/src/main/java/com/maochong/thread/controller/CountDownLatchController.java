package com.maochong.thread.controller;

import com.maochong.thread.service.CountDownLatchService;
import com.maochong.thread.service.CyclicBarrierService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/thread")
public class CountDownLatchController {

    @RequestMapping("/count/down/latch")
    public String countDownLatchOne()
    {
        System.out.println("==============start============");
        // 执行会进入awit,打印 "SampleThread Start"
        new CountDownLatchService.SampleThread(2).start();
        // 执行一次 打印  "countDownLatch1 Start"
        new CountDownLatchService.WorkingThread("countDownLatch1",5*1000).start();
        // 执行一次 打印  "countDownLatch2 Start"
        new CountDownLatchService.WorkingThread("countDownLatch2",10*1000).start();
        // 等待5s后抛出 "countDownLatch1 End" 等待10s后抛出 "countDownLatch2 End" " SampleThread Enf"
        System.out.println("==============end============");
        return "success";
    }

    @RequestMapping("/cyclic/barrier")
    public String cyclicBarrier(String sign)
    {
        System.out.println("==============start============");
        int count = 5;
        // 定义5个，都达到wait后执行WorkingThread线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count,new CyclicBarrierService().new WorkingThread("cyclicBarrier"+sign,10*1000));
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i=0;i<count;i++)
        {
            executorService.execute(new CyclicBarrierService().new SampleThread(cyclicBarrier,"mThreadName"+sign));
        }
        executorService.shutdown(); // 关闭线程池

        System.out.println("==============end============");
        return "success";
    }
}
