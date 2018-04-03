package com.maochong.mybatis.dao;

import com.maochong.mybatis.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * WangHong数据库操作
 * @author jokin
 * */
@Mapper
public interface UserMapper {
    /**
     * 根据玩家名称获取数据
     * @param name  玩家名称
     *              @return 实体类
     * */
    @Select("SELECT * FROM `USER` WHERE `NAME` = #{name}")
    User findByName(@Param("name") String name);

    /**
     * 添加数据
     * @param name 玩家名称
     * @param order  排序
     *               @return 返回影响行数
     */
    @Insert("INSERT INTO `user`(`name`, `order`) VALUES(#{name}, #{order})")
    int insert(@Param("name") String name, @Param("order") Integer order);
}

