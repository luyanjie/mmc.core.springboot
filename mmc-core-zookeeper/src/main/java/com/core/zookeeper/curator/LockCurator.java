package com.core.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 进程互斥，使用curator实现分布式锁
 * @author jokin
 * @date 2018-04-24
 * */
@Service
public class LockCurator implements  ILockCurator {
    private static Logger log  = LoggerFactory.getLogger(LockCurator.class);

    @Autowired
    CuratorFramework curatorFramework;

    /**
     * 固定节点lock
     * */
    private final static  String lockPath = "/lock";


    @Override
    public void DistributedLock(String type, int num) {
        // 定义子节点格式
        String path = StringUtils.concat(lockPath,type==null?"":type);
        log.info("try do job for "+type + " num:"+num);

        InterProcessMutex lock = new InterProcessMutex(curatorFramework,path);
        try {
            if(lock.acquire(100, TimeUnit.SECONDS)){
                // 设置100s 超时
                // 执行你要执行的内容
                Thread.sleep(1*1000);
                log.info(StringUtils.concat("do job ",type," done"," num:",num));
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
