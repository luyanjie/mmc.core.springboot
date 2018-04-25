package com.core.zookeeper.curator;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用curator进行操作
 * @author jokin
 * */
@Service
public class CuratorOpratorDemo {

    @Autowired
    CuratorFramework curatorFramework;

    private final static String ListenPath = "/configuration";

    public void demo() throws Exception {

        String path = "/lock/lock-";
        String outPath =  (String)((ACLBackgroundPathAndBytesable)curatorFramework.create().creatingParentContainersIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)).forPath(path);
        System.out.println(outPath);

        // 使用链表风格变成 fluent风格
//
//        client.start();
//        PathChildrenCache cache = new PathChildrenCache(client, path, true);
//        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
//        cache.getListenable().addListener((client, event) -> {
//            switch (event.getType()) {
//                case CHILD_ADDED:
//                    System.out.println("CHILD_ADDED," + new String(event.getData().getData()));
//                    break;
//                case CHILD_UPDATED:
//                    System.out.println("CHILD_UPDATED," + new String(event.getData().getData()));
//                    break;
//                case CHILD_REMOVED:
//                    System.out.println("CHILD_REMOVED," + event.getData().getPath());
//                    break;
//                default:
//                    break;
//            }
//        });
//        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
//        Thread.sleep(1000);
//        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
//        Thread.sleep(1000);
//        client.delete().forPath(path + "/c1");
//        Thread.sleep(1000);
//        client.delete().forPath(path);
//        Thread.sleep(Integer.MAX_VALUE);


//        List<String> list = curatorFramework.getChildren().forPath(ListenPath);
//        System.out.println(JSON.toJSONString(list));


        /**
         NodeCache cache = new NodeCache(curatorFramework,"/node",false);
         cache.start(true);

         cache.getListenable().addListener(() ->
         System.out.println("节点发生变化："+new String(cache.getCurrentData().getData())));
         * */






        /**
         * 判断节点是否存在
         * */
//        Stat statExists = new Stat();
//        try {
//            statExists  = curatorFramework.checkExists().forPath("/node");
//            System.out.println(JSON.toJSONString(statExists));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        /**
         * 创建节点
         * creatingParentsIfNeeded：创建节点的时候如果不是跟节点，依据父节点依次创建
         * withMode：创建节点的方式
         *      1. CreateMode.EPHEMERAL 临时节点
         *      2. CreateNode.EPHEMERAL_SEQUENTIAL 临时有序节点
         *      3. CreateNode.PERSISTENT 固定节点
         *      4. CreateNode.PERSISTENT_SEQUENTIAL 固定有序节点
         * */


//        String path = null == statExists?"/node":"/node/node1";
//        try {
//            String result  = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,path.getBytes());
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }




        /**
         * watcher
         * */
//        try {
//            Stat stat =curatorFramework.checkExists().watched().forPath("/node");
//            System.out.println(JSON.toJSONString(stat));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 删除节点
         * */

//        try {
//            //默认情况下，version为-1
//            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 查询节点
         * */
//        Stat stat = new Stat();
//        try {
//            byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/node");
//            System.out.println(Arrays.toString(bytes));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 更新节点
         * */
//        try {
//            Stat stat1 =  curatorFramework.setData().withVersion(-1).forPath("/node","123".getBytes());
//            System.out.println(JSON.toJSONString(stat1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 异步操作
         * */

//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        try {
//            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(
//                    (curatorFramework, curatorEvent) -> {
//                        System.out.println(Thread.currentThread().getName()+"->resultCode"+curatorEvent.getResultCode()+"->"+curatorEvent.getType());
//                        countDownLatch.countDown();
//                    },executorService
//            ).forPath("/node");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        executorService.shutdown();

        /**
         * 事务操作
         * */

//        try {
//            Collection<CuratorTransactionResult> resultCollections = (Collection<CuratorTransactionResult>) curatorFramework.inTransaction().create().forPath("/node","luyanjie".getBytes()).
//                    and().setData().forPath("/node22","hao".getBytes());
//            for (CuratorTransactionResult result:resultCollections){
//                System.out.println(result.getForPath()+"->"+result.getType());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            TimeUnit.SECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
