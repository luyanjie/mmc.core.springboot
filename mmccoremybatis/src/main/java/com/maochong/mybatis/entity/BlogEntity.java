package com.maochong.mybatis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * lesson数据库blog表对应的实体映射
 * @author jokin
 * */
@Data
@NoArgsConstructor
public class BlogEntity implements Serializable {
    private int id;
    private int uid;
    private String title;
    private String date;
    private String authorName;
    private String content;
    private String dataBase;
}