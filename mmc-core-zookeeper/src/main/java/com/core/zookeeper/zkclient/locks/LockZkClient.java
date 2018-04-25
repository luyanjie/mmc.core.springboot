package com.core.zookeeper.zkclient.locks;

import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * zkClient实现分布式锁
 * */
@Service
public class LockZkClient {
    private final static String CONNECTING = "192.168.40.155:2181";

    public void DistributedLock(String type, int num) {

        ZkClient zkClient = new ZkClient(CONNECTING,5000);
        ZkClientInterProcessMutex lock = new ZkClientInterProcessMutex(zkClient,"/lock");
        try {
            if(lock.acquire(100, TimeUnit.SECONDS)){
                // 做自己该做的事情......
                Thread.sleep(1*1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
