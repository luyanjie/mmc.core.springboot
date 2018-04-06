package com.maochong.thread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author jokin
 * */
public class CountDownLatchTwoService {
    private final static Logger logger = LoggerFactory.getLogger(CountDownLatchTwoService.class);
    public static class WorkingThread extends Thread {
        private final String mThreadName;
        private final int mSleepTime;
        private static CountDownLatch countDownLatch;

        public WorkingThread(String mThreadName,int mSleepTime,CountDownLatch countDownLatch)
        {
            this.mSleepTime = mSleepTime;
            this.mThreadName = mThreadName;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println(String.format(new Date().toString()+"   %s Start ",mThreadName));
            try {
                Thread.sleep(mSleepTime);
            }
            catch (InterruptedException ex)
            {
                logger.error(ex.getMessage());
            }
            countDownLatch.countDown();// 计数器减一，
            System.out.println(countDownLatch.getCount());
            System.out.println(String.format(new Date().toString()+"  %s End ",mThreadName));
        }
    }

    public static class SampleThread extends Thread{

        private  String mThreadName = "mThreadName";
        public static CountDownLatch countDownLatch;

        @Override
        public void run(){
            System.out.println(String.format(new Date().toString()+"   %s Start ",mThreadName));
            try {
                System.out.println(countDownLatch.getCount());
                countDownLatch.await();// 程序等待在这，知道countDownLatch计数器减为 0
            }
            catch (InterruptedException ex)
            {
                logger.error(" SamplieThread" + ex.getMessage());
            }
            System.out.println(String.format(new Date().toString()+"   %s End ",mThreadName));
        }
    }
}
