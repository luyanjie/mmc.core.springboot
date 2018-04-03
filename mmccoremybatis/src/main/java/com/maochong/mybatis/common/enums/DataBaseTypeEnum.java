package com.maochong.mybatis.common.enums;

import lombok.Getter;

/**
 * @author jokin
 * */
@Getter
public enum  DataBaseTypeEnum {

    SQLSERVER(1),MYSQL(2),MYSQL_READ(21),ORACLE(3);

    private int num;

    DataBaseTypeEnum(int num){
        this.num = num;
    }
}
