package com.maochong.mybatis.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 */
@Data
@NoArgsConstructor
public class LessonUser implements Serializable {
    private Integer id;
    //private Integer keyId;
    private String userName;
    private Integer userAge;
    private String userAddress;
}