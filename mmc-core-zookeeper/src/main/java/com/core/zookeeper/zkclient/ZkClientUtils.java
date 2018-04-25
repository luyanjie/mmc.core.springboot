package com.core.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 配置zkClient
 * @author jokin
 * */
public class ZkClientUtils {

    /**
     zookeeper服务器，这是单台，集群的话 用逗号隔开，如：192.168.40.155:2181,192.168.40.156:2181,192.168.40.157:2181
    */
    private final static String CONNECTING = "192.168.40.155:2181";

    public static ZkClient getInstance(){
        return new ZkClient(CONNECTING,5000);
    }

    public static ZkClient getInstanceSer(){
        return new ZkClient(CONNECTING, 5000, 5000, new SerializableSerializer());
    }
}
