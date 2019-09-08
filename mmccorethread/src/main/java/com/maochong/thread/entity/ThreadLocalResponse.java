package com.maochong.thread.entity;

import java.io.Serializable;

public class ThreadLocalResponse implements Serializable {
    private static final long serialVersionUID = -1252485978194767761L;

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
}
