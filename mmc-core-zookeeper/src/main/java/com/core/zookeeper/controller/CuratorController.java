package com.core.zookeeper.controller;

import com.core.zookeeper.config.DataConfiguration;
import com.core.zookeeper.curator.CuratorOpratorDemo;
import com.core.zookeeper.curator.ILockCurator;
import com.core.zookeeper.curator.NodeCacheDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/zookeeper")
public class CuratorController {

    @Autowired
    CuratorOpratorDemo curatorOpratorDemo;

    @Autowired
    NodeCacheDemo nodeCacheDemo;

    @Autowired
    ILockCurator iLockCurator;

    @RequestMapping("/curator")
    public String curator() throws Exception {
        curatorOpratorDemo.demo();

        return "success";
    }

    /**
     * http://localhost:8084/zookeeper/curator/data
     * */
    @RequestMapping("/curator/data")
    public String curatorData()
    {
        return DataConfiguration.mapGet();
    }

    /**
     * http://localhost:8084/zookeeper/curator/node/cache
     * */
    @RequestMapping("/curator/node/cache")
    public String nodeCache() throws Exception {
        nodeCacheDemo.demo();
        return "success";
    }

    @RequestMapping("/curator/lock")
    public String lock() throws Exception {
//        ThreadPoolExecutor threads = new ThreadPoolExecutor(1, 5, 3,
//                TimeUnit.SECONDS, new ArrayBlockingQueue<>(5),
//                new ThreadPoolExecutor.DiscardOldestPolicy());

        iLockCurator.DistributedLock("",1);
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        final int count =10;
//        //  "/book"
//        final String type = "";
//        for (int i=0;i<count;i++)
//        {
//            final int x= i;
//            executorService.execute(()-> iLockCurator.DistributedLock(type,x));
//        }
//        executorService.shutdown();


        return "success";
    }
}
