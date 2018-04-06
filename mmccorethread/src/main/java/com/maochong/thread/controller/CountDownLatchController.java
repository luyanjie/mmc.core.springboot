package com.maochong.thread.controller;

import com.maochong.thread.service.CountDownLatchService;
import com.maochong.thread.service.CountDownLatchTwoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/thread")
public class CountDownLatchController {

    @RequestMapping("/count/down/latch")
    public String countDownLatchOne()
    {
        System.out.println("--------------------------------");
        // 执行会进入awit,打印 "SampleThread Start"
        new CountDownLatchService.SampleThread(2).start();
        // 执行一次 打印  "countDownLatch1 Start"
        new CountDownLatchService.WorkingThread("countDownLatch1",5*1000).start();
        // 执行一次 打印  "countDownLatch2 Start"
        new CountDownLatchService.WorkingThread("countDownLatch2",10*1000).start();
        // 等待两秒后抛出 "countDownLatch1 End" "countDownLatch2 End" " SampleThread Enf"
        return "success";
    }

    @RequestMapping("/count/down/latch/two")
    public String countDownLatchTwo()
    {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        CountDownLatchTwoService.SampleThread ss = new CountDownLatchTwoService.SampleThread();
        CountDownLatchTwoService.SampleThread.countDownLatch = countDownLatch;
        ss.start();

        new CountDownLatchTwoService.WorkingThread("countDownLatch1",5*1000,countDownLatch).start();
        new CountDownLatchTwoService.WorkingThread("countDownLatch2",10*1000,countDownLatch).start();
        return "success";
    }
}
