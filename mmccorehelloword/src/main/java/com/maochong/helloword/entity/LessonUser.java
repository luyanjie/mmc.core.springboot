package com.maochong.helloword.entity;

import java.io.Serializable;

public class LessonUser implements Serializable {
    private static final long serialVersionUID = -6471553328656915456L;
    private Integer id;
    private String userName;
    private Integer userAge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    private String userAddress;
}
