package com.maochong.mybatis.entity.typehandler;

import com.maochong.mybatis.common.enums.DataBaseTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * */
@Data
public class BlogTypeHandlerEntity implements Serializable {
    private int id;
    private int uid;
    private String title;
    private String date;
    private String authorName;
    /**
     * 自定义类型，需要指定MyBatis类型转换
     * */
    private Content content;
    /**
     * 枚举类型，需要指定EnumOrdinalTypeHandler 类型转换
     * */
    private DataBaseTypeEnum dataBase;
}

