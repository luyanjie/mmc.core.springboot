package com.core.zookeeper.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * watcher 的第三种 TreeCache：他是NodeCache+PathChildrenCache的合体
 *
 * 参考：https://my.oschina.net/roccn/blog/956654
 * */
public class TreeCacheDemo {
    @Autowired
    CuratorFramework curatorFramework;

    // 监控节点
    private static final String MASTER_PATH="/curator_master_path1";



    public void demo(){

        /**
         * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
         * */
        ExecutorService executorService = Executors.newFixedThreadPool(1);

       //  TreeCache tc = TreeCache.newBuilder(curatorFramework,MASTER_PATH).setCacheData(true).setDataIsCompressed(false).build();
        TreeCache treeCache = new TreeCache(curatorFramework,MASTER_PATH);
        treeCache.getListenable().addListener((CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) -> {
            TreeCacheEvent.Type type =  treeCacheEvent.getType();
            switch (type){
                // 新增节点
                case NODE_ADDED:
                    treeCacheEvent.getData().getData();
                    break;
                // 修改节点
                case NODE_UPDATED:
                    break;
                // 删除节点
                case NODE_REMOVED:
                    break;
                // 连接暂停
                case CONNECTION_SUSPENDED:
                    break;
                // 连接重新连接
                case CONNECTION_RECONNECTED:
                    break;
                // 连接丢失
                case CONNECTION_LOST:
                    break;
                // 初始化
                case INITIALIZED:
                    break;
                default:break;
            }
        },executorService);
        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Stat stat = curatorFramework.checkExists().forPath(MASTER_PATH);
            if(stat==null){
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(MASTER_PATH+"/start","master".getBytes());
            }
            else {
                curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(MASTER_PATH+"/start2","master2".getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
