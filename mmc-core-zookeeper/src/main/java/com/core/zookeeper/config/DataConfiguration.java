package com.core.zookeeper.config;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据配置文件
 * @author jokin
 * */
public class DataConfiguration {
    private static Map<String,String> dataMap = new HashMap<>();


    /**
     * 获取数据
     * */
    public static String get(String key)
    {
        return dataMap.get(key);
    }

    /**
     * 更新数据
     * */
    public static void set (String key,String value)
    {
        synchronized (dataMap){
            dataMap.put(key,value);
        }
    }

    /**
     * 删除节点
     * */
    public static void del(String key)
    {
        dataMap.remove(key);
    }

    /**
     * 获取所有的数据
     * */
    public static String  mapGet()
    {
        return JSON.toJSONString(dataMap);
    }
}
