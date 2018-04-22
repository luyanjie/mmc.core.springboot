package com.core.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * zkClient 连接测试
 * 简单，但是很多使用比较暴力，一般我们不用它来作为zookeeper的第三方框架，比较多的使用curator来处理
 * */
public class SessionDemo {

    // zookeeper服务器，这是单台，集群的话 用逗号隔开，如：192.168.40.155:2181,192.168.40.156:2181,192.168.40.157:2181
    private final static String CONNECTING = "192.168.40.155:2181";
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(CONNECTING,5000);
        System.out.println(zkClient+" - > success");
    }
}
