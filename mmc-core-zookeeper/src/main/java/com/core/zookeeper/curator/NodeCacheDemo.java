package com.core.zookeeper.curator;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * NodeCacheDemo
 * @author jokin
 * */
@Service
public class NodeCacheDemo {

    @Autowired
    CuratorFramework curatorFramework;

    private final static String path = "/node";

    /**
     * 监听/node 节点变化 NodeCache
     * dataIsCompressed：数据是否压缩
     * */
    public void demo() throws Exception {
        NodeCache cache = new NodeCache(curatorFramework,path,false);
        try {
            cache.start(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cache.getListenable().addListener(() -> {
            if(cache.getCurrentData()!=null){
                Stat stat = cache.getCurrentData().getStat();
                System.out.println(JSON.toJSONString(stat));
                System.out.println("节点位置："+cache.getCurrentData().getPath());
                System.out.println("节点发生变化："+new String(cache.getCurrentData().getData()));
            }
            else {
                System.out.println("节点被删除！");

            }
        });

        int forCount = 3;
        for (int i=0;i<forCount;i++)
        {
            Stat existsStat = curatorFramework.checkExists().forPath(path);
            if(existsStat!=null){
                curatorFramework.setData().forPath(path, UUID.randomUUID().toString().getBytes());
            }
            else{
                curatorFramework.create().creatingParentsIfNeeded().forPath(path,"start".getBytes());
            }
            TimeUnit.SECONDS.sleep(5);
        }

        /*
        三次输出结果：
        {"aversion":0,"ctime":1524538813114,"cversion":0,"czxid":1516,"dataLength":5,"ephemeralOwner":0,"mtime":1524538813114,"mzxid":1516,"numChildren":0,"pzxid":1516,"version":0}
        节点位置：/node
        节点发生变化：start
        {"aversion":0,"ctime":1524538813114,"cversion":0,"czxid":1516,"dataLength":36,"ephemeralOwner":0,"mtime":1524538818144,"mzxid":1517,"numChildren":0,"pzxid":1516,"version":1}
        节点位置：/node
        节点发生变化：7f3e3f92-e702-4c26-8456-592dc9b07570
        {"aversion":0,"ctime":1524538813114,"cversion":0,"czxid":1516,"dataLength":36,"ephemeralOwner":0,"mtime":1524538823174,"mzxid":1518,"numChildren":0,"pzxid":1516,"version":2}
        节点位置：/node
        节点发生变化：0c331959-3ca1-4308-a500-9bdd7ee2f395
        * */
    }
}
