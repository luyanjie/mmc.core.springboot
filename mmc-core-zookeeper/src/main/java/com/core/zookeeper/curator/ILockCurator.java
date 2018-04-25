package com.core.zookeeper.curator;

/**
 * 进程互斥，使用curator实现分布式锁
 * @author jokin
 * @date 2018-04-24 *
 * */
public interface ILockCurator {

    void DistributedLock(String type,int num);
}
