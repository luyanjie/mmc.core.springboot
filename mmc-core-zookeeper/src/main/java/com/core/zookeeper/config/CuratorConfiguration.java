package com.core.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.StringUtils;

/**
 * 配置Curator
 * @author jokin
 * */
@Configuration
public class CuratorConfiguration {

    /**
     * zookeeper连接串配置(集群使用逗号隔开)
     * */
    private final static String CONSECRATING ="192.168.40.155:2181";

    /**
     * 监听目录（用于配置全局配置文件）
     */
    private final static String LISTEN_PATH = "/configuration";

    private final static String REPLACE_LISTEN_PATH = StringUtils.concat(LISTEN_PATH,"/");
    /**
     * PathChildrenCache 是否使用缓存data，必须使用true才有效，暂时不知道什么原因
     * */
    private final static boolean CACHE_DATA = true;

    /**
     * master选举目录
     * */
    private final static String MASTER_PATH = "/master_schedule";

    /**
     * 默认不是Master
     * */
    private static boolean isMaster = false;

    @Bean
    public CuratorFramework curatorFramework(){
        /**
         * 1. new ExponentialBackoffRetry(1000,3) // 间隔1s，最多重试3次
         2. RetryNTimes(int n, int sleepMsBetweenRetries) 指定最大重试次数
         3. RetryOneTime(int sleepMsBetweenRetry) 仅重试一次
         4. RetryUntilElapsed(5000, 1000) 会一直重试直到达到规定时间，第一个参数整个重试不能超过时间，第二个参数重试间隔
         * */
        // 设定链接失败重试方式(每隔1s重试一次，最多重试三次)
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                newClient(CONSECRATING,5000,5000,retryPolicy);

        curatorFramework.start();
        // fluent风格的链式编程
//        CuratorFramework curatorFramework1 = CuratorFrameworkFactory.builder().connectString(CONSECRATING)
//                .connectionTimeoutMs(5000).sessionTimeoutMs(5000).retryPolicy(retryPolicy).build();


        /**
         * 监听服务
         * */
        PathChildrenCache cache = new PathChildrenCache(curatorFramework,LISTEN_PATH,CACHE_DATA);
        cache.getListenable().addListener((CuratorFramework client, PathChildrenCacheEvent event)->{
            switch (event.getType()){
                // 新增节点，在启动的时候会自动循环执行把zookeeper上已有的配置导入到本地
                case CHILD_ADDED:
                   DataConfiguration.set(event.getData().getPath().replace(REPLACE_LISTEN_PATH,""),
                           new String(event.getData().getData()));
                    break;
                // 删除子节点
                case CHILD_REMOVED:
                    DataConfiguration.del(event.getData().getPath().replace(REPLACE_LISTEN_PATH,""));
                    break;
                // 修改子节点
                case CHILD_UPDATED:
                    DataConfiguration.set(event.getData().getPath().replace(REPLACE_LISTEN_PATH,""),
                            new String(event.getData().getData()));
                    break;
                default:break;
            }
        });

        try {
            cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Master选举：就是对某个节点去争抢资源，抢到了就是master
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                // 执行到这说明已经是Master了
                System.out.println("已经成功注册为master，你已经是老大了"+new String(curatorFramework.getData().forPath(MASTER_PATH)));
                // 已经成功注册为master，你已经是老大了
                isMaster = true;
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();

        return curatorFramework;

        // 监听/node 节点变化 NodeCache
        // dataIsCompressed：数据是否压缩
        //        NodeCache cache = new NodeCache(curatorFramework,"/node",false);
        //        try {
        //            cache.start(true);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        //        cache.getListenable().addListener(() -> {
        //            if(cache.getCurrentData()!=null){
        //                Stat stat = cache.getCurrentData().getStat();
        //                System.out.println(JSON.toJSONString(stat));
        //                System.out.println("节点位置："+cache.getCurrentData().getPath());
        //                System.out.println("节点发生变化："+new String(cache.getCurrentData().getData()));
        //            }
        //            else {
        //                System.out.println("节点被删除！");
        //                System.out.println(cache.getPath());
        //
        //            }
        //        });
//        三种监听：
//        PathChildrenCache 监视一个路径下子节点的创建、删除、节点数据更新
//        NodeCache   监视一个节点的创建、更新、删除
//        TreeCache   PathChildrenCache+NodeCache 的合体（监视路径下的创建、更新、删除事件），
    }
}
