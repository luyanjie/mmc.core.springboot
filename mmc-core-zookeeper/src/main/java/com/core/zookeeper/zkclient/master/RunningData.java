package com.core.zookeeper.zkclient.master;

import java.io.Serializable;

/**
 * @author jokin
 */
public class RunningData implements Serializable {

    private static final long serialVersionUID = 3653508404593847538L;

    //服务器id
    private long cid;
    //服务器名称
    private String name;


    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

