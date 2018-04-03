package com.maochong.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jokin
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LessonUserBlogEntity extends LessonUser {
    private List<BlogEntity> blogs;

    private BlogEntity blog;
}
