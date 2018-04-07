package com.maochong.thread.service.lock;

import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 共享排他锁
 * */
public class ReentrantReadWriteLockService {

    public class Main{
        public void blockByWriteLock()
        {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            // 添加一个write的排他锁
            lock.writeLock().lock();
            TestThread[] testThreads = new TestThread[10];
            for (int i = 0; i < 10; i++) {
                boolean isWriter = (i + 1) % 4 == 0;
                TestThread testThread = new TestThread(lock,"threadName"+i,isWriter);
                testThreads[i] = testThread;
            }

            for (int i = 0;i<10;i++)
            {
                testThreads[i].start();
            }
            System. out.println(new Date().toString() + ": block by main write lock start");
            try
            {
                Thread.sleep(5*1000);
            }
            catch (InterruptedException ex)
            {

            }
            finally {

            }
            System. out.println(new Date().toString() + ": block by main write lock end");
            lock.writeLock().unlock();
        }
    }

    public class TestThread extends Thread{
        private ReentrantReadWriteLock lock;
        private String threadName;
        /**
         * 是否写锁
         * */
        private boolean isWriter ;

        public TestThread(ReentrantReadWriteLock lock,String threadName,boolean isWriter)
        {
            this.lock = lock;
            this.threadName = threadName;
            this.isWriter = isWriter;
        }

        @Override
        public void  run()
        {
            try {
                if (isWriter) {
                    // 设定是写锁（排他锁），执行写锁，并等待3s
                    lock.writeLock().lock();
                    Thread.sleep(3 * 1000);
                    System. out.println("-------------TestThread write start---------------" );
                } else {
                    // 共享锁
                    lock.readLock().lock();
                }
                System.out.println(new Date().toString() + "   线程执行：" + threadName);
                if (isWriter ) {
                    Thread. sleep(3* 1000);
                    System. out.println("--------------TestThread write end---------------" );
                }
            }
            catch (Exception ex)
            {

            }
            finally {
                if(isWriter)
                {
                    lock.writeLock().unlock();
                }
                else {
                    lock.readLock().unlock();
                }
            }
        }
    }
}
