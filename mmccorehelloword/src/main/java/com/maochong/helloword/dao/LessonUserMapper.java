package com.maochong.helloword.dao;

import com.maochong.helloword.entity.LessonUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LessonUserMapper {

    /**
     * 根据Id获取数据
     * @param id 玩家Id,指定jdbc的类型 jdbcType = INTEGER
     *           @return LessonUser实体类
     * */
    @Select({"SELECT * FROM lesson.`user` WHERE  id= #{id,jdbcType=INTEGER}"})
    LessonUser findById(@Param("id") Integer id);

    /**
     * 返回大于id的列表
     * @param id 玩家id
     *           @return List
     * */
    @Select("SELECT * FROM lesson.`user` WHERE  id> #{id}")
    List<LessonUser> findByIdList(@Param("id") Integer id);
}
