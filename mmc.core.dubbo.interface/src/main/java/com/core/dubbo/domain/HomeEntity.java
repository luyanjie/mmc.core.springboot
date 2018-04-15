package com.core.dubbo.domain;

import java.io.Serializable;

/**
 * home 实体类
 * */
public class HomeEntity  implements Serializable {

    private static final long serialVersionUID = -2380372009508125190L;
    private int id;
    private String address;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
