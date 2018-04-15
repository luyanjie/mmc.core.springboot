package com.maochong.thread.service.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLockService {

    // volatile 多线程时保证数据的可见性---直接修改的是共享内存数据，但是无法保证原子性
    private volatile  int a  = 1;

    // Atomic用来保证在多线程环境下的原子性
    private AtomicInteger b = new AtomicInteger(1);

    public void volatileAdd()
    {
        for (int i=0;i<20;i++)
        {
            a++;
            System.out.println(a);
        }
    }

    public  void atomicAdd()
    {
        for (int i=0;i<20;i++)
        {
            System.out.println(b.getAndIncrement());
        }
    }


    List<String> list = new ArrayList<>();
    private static int size;

    private static Object lock = new Object();
    public ThreadLockService(int size)
    {
        this.size = size;
    }

    public void put(String data)
    {
        synchronized (lock)
        {
            if(list.size()>=size)
            {
                System.out.println(Thread.currentThread().getName() + "：线程已满！");
                try {
                    // 当前线程等待在这
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(data);
            System.out.println(Thread.currentThread().getName() + "：线程写入"+data+"成功！");
            // 去唤醒其他等待的线程
            lock.notifyAll();
        }
    }

    public String get()
    {
        synchronized (lock)
        {
            if(list.size()==0)
            {
                System.out.println(Thread.currentThread().getName() + "：线程为空无法获取数据！");
                try {
                    // 当前线程等待在这
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            String  data =  list.get(0);
            System.out.println(Thread.currentThread().getName() + "：线程获取数据成功！数据为:"+data);
            // 去唤醒其他线程进行操作
            lock.notifyAll();
            return data;
        }
    }

    public static void main(String[] args){
        ThreadLockService threadLockService = new ThreadLockService(5);
        // volatile 验证
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++)
        {
            executorService.execute(()->{
                //threadLockService.volatileAdd();
                threadLockService.atomicAdd();
            });
        }

        executorService.shutdown();


//        // synchronized 验证
//        new Thread(()->{
//            threadLockService.put("jokin1");
//            threadLockService.put("jokin2");
//            threadLockService.put("jokin3");
//            threadLockService.put("jokin4");
//            threadLockService.put("jokin5");
//            threadLockService.put("jokin6");
//            threadLockService.put("jokin7");
//        },"PUT线程T1").start();
//
//        new Thread(()->{
//            threadLockService.put("jokin111");
//            threadLockService.put("jokin222");
//            threadLockService.put("jokin333");
//            threadLockService.put("jokin444");
//            threadLockService.put("jokin555");
//            threadLockService.put("jokin666");
//            threadLockService.put("jokin777");
//        },"PUT线程T2").start();
//
//        new Thread(()->{
//            threadLockService.get();
//            threadLockService.get();
//        },"GET线程T1").start();
    }
}
