package com.core.zookeeper.javaapi;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WatcherDemo implements Watcher {

    // zookeeper服务器，这是单台，集群的话 用逗号隔开，如：192.168.40.155:2181,192.168.40.156:2181,192.168.40.157:2181
    private final static String CONNECTING = "192.168.40.155:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zookeeper;
    private static Stat stat = new Stat();

    public void exector() throws IOException, KeeperException, InterruptedException {
        // 创建zookeeper，sessionTimeOut：是指session的过期时间，单位毫秒
        zookeeper = new ZooKeeper(CONNECTING, 5000, new WatcherDemo());

//        ACL acl=new ACL(ZooDefs.Perms.ALL,new Id("ip","192.168.11.129"));
//        List<ACL> acls=new ArrayList<>();
//        acls.add(acl);
//        zookeeper.exists("/authTest",true);
//        zookeeper.create("/authTest","111".getBytes(),acls,CreateMode.PERSISTENT);
//        System.in.read();


//        //创建节点
//        String result=zookeeper.create("/node1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//        zookeeper.getData("/node1",true,stat); //增加一个
//        System.out.println("创建成功："+result);
//
//        //修改数据
//        zookeeper.setData("/node1","luyanjie123".getBytes(),-1);
//        Thread.sleep(2000);
//        //修改数据
//        zookeeper.setData("/node1","luyanjie345".getBytes(),-1);
//        Thread.sleep(2000);

        //创建节点 jokin然后删除
        String nodej = "/jokin";
        stat = zookeeper.exists(nodej, true);
        if (stat == null) {
            /**
             * CreateMode.
             *      PERSISTENT:永久节点
             *      PERSISTENT_SEQUENTIAL：永久顺序节点
             *      EPHEMERAL：临时节点
             *      EPHEMERAL_SEQUENTIAL：零时顺序节点
             * */
            zookeeper.create(nodej, "luyanjie1234".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            Thread.sleep(2000);
        } else {
            System.out.println("存在数据返回：stat= " + JSON.toJSONString(stat));
            //删除节点(这里直接删除 只有一层，如果是有子节点需要递归先删除子节点)
            zookeeper.delete(nodej, -1);
            Thread.sleep(2000);
            System.out.println("节点：" + nodej + " 删除成功");
        }


//
//        //创建节点和子节点
//        String path="/node11";
//
//        zookeeper.create(path,"123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//        TimeUnit.SECONDS.sleep(1);
//
//        Stat stat=zookeeper.exists(path+"/node1",true);
//        if(stat==null){//表示节点不存在
//            zookeeper.create(path+"/node1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//            TimeUnit.SECONDS.sleep(1);
//        }
//        //修改子路径
//        zookeeper.setData(path+"/node1","mic123".getBytes(),-1);
//        TimeUnit.SECONDS.sleep(1);


        //获取指定节点下的子节点
       /* List<String> childrens=zookeeper.getChildren("/node",true);
        System.out.println(childrens);*/

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 当前zookeeper是链接状态
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            switch (watchedEvent.getType()) {
                case None:// 没做任何操作
                    if (null == watchedEvent.getPath()) {
                        countDownLatch.countDown();
                        System.out.println(watchedEvent.getState() + "-->" + watchedEvent.getType());
                    }
                    break;
                case NodeCreated:// 创建节点
                    try {
                        System.out.println("创建了节点：" + watchedEvent.getPath() + "节点数据为：" + zookeeper.getData(watchedEvent.getPath(), true, new Stat()));
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case NodeDeleted:// 删除节点
                    System.out.println("删除的节点：" + watchedEvent.getPath());
                    break;
                case NodeDataChanged: // 节点数据变更
                    try {
                        System.out.println("节点创建路径：" + watchedEvent.getPath() + "->节点的值：" +
                                zookeeper.getData(watchedEvent.getPath(), true, stat));
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                case NodeChildrenChanged: // 子节点变更
                    try {
                        System.out.println("子节点数据变更路径：" + watchedEvent.getPath() + "->节点的值：" +
                                zookeeper.getData(watchedEvent.getPath(), true, stat));
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }

        }
    }
}
