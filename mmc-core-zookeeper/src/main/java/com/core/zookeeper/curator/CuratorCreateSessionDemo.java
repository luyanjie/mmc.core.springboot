package com.core.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;

/**
 * Curator客户端
 * @author  jokin
 * @Date 2018-04-22
 *
 * */
public class CuratorCreateSessionDemo {
    // zookeeper服务器，这是单台，集群的话 用逗号隔开，如：192.168.40.155:2181,192.168.40.156:2181,192.168.40.157:2181
    private final static String CONNECTING = "192.168.40.155:2181";

    public static void main(String[] args) {
        // 创建两种会话方式   normal
        /*
         RetryPolicy参数是链接不上的重试策略
         1. new ExponentialBackoffRetry(1000,3) // 间隔1s，最多重试3次
         2. RetryNTimes(int n, int sleepMsBetweenRetries) 指定最大重试次数
         3. RetryOneTime(int sleepMsBetweenRetry) 仅重试一次
         4. RetryUntilElapsed(5000, 1000) 会一直重试直到达到规定时间，第一个参数整个重试不能超过时间，第二个参数重试间隔
        * */

        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                newClient(CONNECTING,5000,5000,new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        // fluent 风格,链式编程
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory.builder().connectString(CONNECTING)
                .sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000,3) ).connectionTimeoutMs(5000).namespace("/jokin").build();
        // namespace 是指定一个跟节点，所有的节点操作都在这执行
        curatorFramework1.start();
        System.out.println("success");
    }
}
