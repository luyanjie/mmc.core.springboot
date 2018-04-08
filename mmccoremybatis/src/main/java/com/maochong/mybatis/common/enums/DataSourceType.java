package com.maochong.mybatis.common.enums;

/**
 * @author jokin
 * */
public enum DataSourceType {

    // 默认库
    Master("master"),
    // 第二个数据库
    Slave("slave");

    private String name;

    private DataSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
