package com.maochong.mybatis.config;

import com.maochong.mybatis.common.enums.DataSourceType;

/**
 * 动态数据源的上下文 threadlocal
 * JdbcContextHolder
 *  TODO
 * @author jokin
 * @date 2018年2月27日 上午11:43:34
 */
public class JdbcContextHolder {

    private final static ThreadLocal<String> local = new ThreadLocal<>();

    public static void putDataSource(String name) {
        local.set(name);
    }

    public static String getDataSource() {
        String dbName = local.get();
        return dbName!=null?dbName:DataSourceType.Master.getName();
    }

}
