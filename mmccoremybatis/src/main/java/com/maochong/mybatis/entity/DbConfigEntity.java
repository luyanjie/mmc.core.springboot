package com.maochong.mybatis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DbConfigEntity {
    private String dbName;
    private Integer dbType;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
