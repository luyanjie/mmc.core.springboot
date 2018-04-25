package com.core.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * zkClient 连接测试
 * 简单，但是很多使用比较暴力，一般我们不用它来作为zookeeper的第三方框架，比较多的使用curator来处理
 * @author jokin
 * */
public class SessionDemo {
    public static void main(String[] args) {
        ZkClient zkClient = ZkClientUtils.getInstance();
        System.out.println(zkClient+" - > success");
    }
}
