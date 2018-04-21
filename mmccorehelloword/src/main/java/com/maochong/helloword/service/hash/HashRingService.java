package com.maochong.helloword.service.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Hash一致性算法，实现Hash环，可以应用于数据库分表，redis分库多节点
 * @author jokin
 * */
public class HashRingService {
    /**
     * 待加入Hash环的服务器列表
     * */
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111","192.168.0.3:111", "192.168.0.4:111"};

    /**
     * key 表示服务器的hash值，value表示服务器的名称
     * */
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    /**
     * 程序初始化，将所有的服务器放入sortedMap
     * */
    static
    {
        for (String server : servers) {
            sortedMap.put(getHash(server), server);
        }
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     * */
    public static int getHash(String str)
    {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++)
        {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0){
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 得到应当路由到的结点
     * */
    public static String getServer(String node)
    {
        // 得到带路由的结点的Hash值
        int hash = getHash(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap =
                sortedMap.tailMap(hash);
        // 第一个Key就是顺时针过去离node最近的那个结点
        Integer i = subMap.size()>0?subMap.firstKey():sortedMap.firstKey();
        // 返回对应的服务器名称
        return subMap.get(i);
    }
}
