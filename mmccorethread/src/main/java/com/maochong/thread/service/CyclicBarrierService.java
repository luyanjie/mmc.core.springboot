package com.maochong.thread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierService {
    private final static Logger logger = LoggerFactory.getLogger(CyclicBarrierService.class);
    public class WorkingThread implements Runnable {
        private final String mThreadName;
        private final int mSleepTime;

        public WorkingThread(String mThreadName,int mSleepTime)
        {
            this.mSleepTime = mSleepTime;
            this.mThreadName = mThreadName;
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
            System.out.println(String.format(new Date().toString()+"  %s End ",mThreadName));
        }
    }

    public class SampleThread implements Runnable{

        private  String mThreadName = "mThreadName";
        private CyclicBarrier cyclicBarrier = null;
        public SampleThread( CyclicBarrier cyclicBarrier,String mThreadName){
            this.cyclicBarrier = cyclicBarrier;
            this.mThreadName = mThreadName;
        }
        @Override
        public void run(){
            System.out.println(String.format(new Date().toString()+"   %s Start ",mThreadName));
            try {
                // System.out.println(cyclicBarrier.getParties());
                cyclicBarrier.await();// 程序等待在这，知道countDownLatch计数器减为 0
            }
            catch (BrokenBarrierException | InterruptedException ex)
            {
                logger.error(" SamplieThread" + ex.getMessage());
            }
            System.out.println(String.format(new Date().toString()+"   %s End ",mThreadName));
        }
    }
}
