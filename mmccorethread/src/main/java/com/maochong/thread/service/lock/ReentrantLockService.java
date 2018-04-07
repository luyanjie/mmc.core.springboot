package com.maochong.thread.service.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的公平锁与非公平锁
 * */
public class ReentrantLockService {

    private ReentrantLock lock;
    /**
     * 非公平锁
     * */
    public class NoFair{

        public NoFair()
        {
            lock = new ReentrantLock(false);
        }

        public void serviceMethod(){
            try{
                lock.lock();
                System.out.println("获得线程锁的名称：" + Thread.currentThread().getName());
            }finally {
                lock.unlock();
            }
        }
    }

    /**
     * 公平锁
     * */
    public class Fair{
        public Fair()
        {
            lock = new ReentrantLock(true);
        }
        public void serviceMethod(){
            try{
                lock.lock();
                System.out.println("获得线程锁的名称：" + Thread.currentThread().getName());
            }finally {
                lock.unlock();
            }
        }
    }
}
