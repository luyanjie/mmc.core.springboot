package com.maochong.thread.service.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * 简单的可重入锁实现，使用一个计数器记录当前线程重入锁的次数，获得锁时计数器加1，释放锁时计数器减1，当计数器等于0时表示释放了锁
 * @author jokin
 * */
public class SimpleReentrantLockService implements Lock {
    /**
     * 指向已经获得锁的线程
     * */
    private volatile Thread exclusiveOwnerThread;

    /**
     * 记录获取了同一个锁的次数
     * */
    private volatile int holdCount;

    private final Lock lock;

    /**
     * 是否是自己获得锁的条件
     * */
    private final Condition isCountZero;

    public SimpleReentrantLockService(){
        lock = new ReentrantLock();
        isCountZero = lock.newCondition();
        holdCount = 0;
    }

    @Override
    public void lock() {
        lock.lock();
        try{
            // 当前线程的引用
            Thread currentThread = Thread.currentThread();
            // 如果获得锁的线程是自己，那么计数器加1，直接返回
            if(exclusiveOwnerThread == currentThread){
                holdCount ++;
                return;
            }

            while(holdCount != 0){
                try {
                    isCountZero.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted");
                }
            }
            // 将exclusiveOwnerThread设置为自己
            exclusiveOwnerThread = currentThread;
            holdCount ++;
        }finally{
            lock.unlock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return holdCount>0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        lock.lock();
        try{
            holdCount --;
            if(holdCount == 0){
                isCountZero.signalAll();
            }
        }finally{
            lock.unlock();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
