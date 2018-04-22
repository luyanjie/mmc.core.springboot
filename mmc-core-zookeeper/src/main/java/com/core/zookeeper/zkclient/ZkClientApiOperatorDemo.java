package com.core.zookeeper.zkclient;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * zkClient基本方法
 * */
public class ZkClientApiOperatorDemo {
    // zookeeper服务器，这是单台，集群的话 用逗号隔开，如：192.168.40.155:2181,192.168.40.156:2181,192.168.40.157:2181
    private final static String CONNECTING = "192.168.40.155:2181";

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(CONNECTING,5000);
        String path = "/jokin";
        boolean bl = zkClient.exists(path);
        System.out.println("判断节点是否存在："+bl);
        if(!bl){
            // 创建零时节点
            //zkClient.createEphemeral(path+"E");
            // 创建永久节点
            zkClient.createPersistent(path,"luyanjie");
            // 创建有序永久节点
            //zkClient.createPersistentSequential(path+"S","aks");
        }
        else{
            Stat stat = new Stat();
            // 获取数据
            String data = zkClient.readData(path,true);
            System.out.println(data);

            zkClient.readData(path,stat);

            System.out.println(JSON.toJSONString(stat));
            // 获取子节点
            //zkClient.getChildren(path);

            // 删除节点
            //zkClient.delete(path);// 这个时候版本默认是-1 等同于 zkClient.delete(path,-1);
            // 有子节点删除的时候
            // zkClient.deleteRecursive(path);
        }
        // 递归创建节点
        //zkClient.createPersistent("/zkclient/zkclient1/zkclient1-1/zkclient1-1-1",true);


        /*
        订阅监听数据变化
        zkClient.subscribeDataChanges("/node", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点名称："+s+"->节点修改后的值"+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        // 写入数据
        zkClient.writeData("/node","node");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */


        /*
        订阅监听子节点变化
        zkClient.subscribeChildChanges("/node", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println(StringUtils.concat("key",s,"  value:",list));
            }
        });
        * */
    }
}
