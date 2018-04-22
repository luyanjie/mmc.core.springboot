package com.core.zookeeper.javaapi;


import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class ApiOperatorDemo {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        WatcherDemo watcherDemo = new WatcherDemo();
        watcherDemo.exector();
    }
}
