package com.maochong.mybatis.dao;

import com.maochong.mybatis.common.constant.MyDataSource;
import com.maochong.mybatis.common.enums.DataSourceType;
import com.maochong.mybatis.entity.BlogEntity;
import com.maochong.mybatis.entity.LessonUserBlogEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 第二种 映射器接口调用SqlBuilder生成的SQL进行执行映射器接口
 * @author jokin
 * */
@Mapper
// @CacheNamespace(size=100)
@MyDataSource(DataSourceType.Master)
public interface BlogMapper {

    @SelectProvider(type = BlogSqlProvider.class,method = "getSql")
//    @Results(value = {
//            @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
//            @Result(property="title",column="title",javaType=String.class,jdbcType=JdbcType.VARCHAR),
//            @Result(property="date",column="date",javaType=String.class,jdbcType=JdbcType.VARCHAR),
//            @Result(property="authorName",column="authorName",javaType=String.class,jdbcType=JdbcType.VARCHAR),
//            @Result(property="content",column="content",javaType=String.class,jdbcType=JdbcType.VARCHAR),
//    })
    BlogEntity getBlog(@Param("id") Integer id);

    @SelectProvider(type = BlogSqlProvider.class, method = "getAllSql")
    @Results(value ={
            @Result(id=true, property="id",column="id",javaType=Integer.class,jdbcType= JdbcType.INTEGER),
            @Result(property="title",column="title",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property="date",column="date",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property="authorName",column="authorName",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property="content",column="content",javaType=String.class,jdbcType=JdbcType.VARCHAR),
    })
    List<BlogEntity> getAllBlog();

    @Select("select * from blog where uid=#{uid}")
    List<BlogEntity> getUserBlog(@Param("uid") Integer uid);

    @Select("select * from blog where uid=#{uid} limit 1")
    BlogEntity getUserBlogOne(@Param("uid") Integer uid);

    @Select("SELECT * FROM `user` where id=#{id}")
    @Results(value = {
            @Result(property = "id",column = "id",javaType=Integer.class,jdbcType=JdbcType.INTEGER),
            @Result(property = "userName",column = "userName",javaType = String.class),
            @Result(property = "userAge",column = "userAge",javaType = Integer.class),
            @Result(property = "userAddress",column = "userAddress",javaType = String.class),
            @Result(property = "blogs",javaType = List.class,column = "id",many = @Many(select = "com.maochong.mybatis.dao.BlogMapper.getUserBlog")), // 一对多
            @Result(property = "blog",javaType = BlogEntity.class,column = "id",one = @One(select = "com.maochong.mybatis.dao.BlogMapper.getUserBlogOne")), // 一对一
    })
    LessonUserBlogEntity getUsetBlogs(@Param("id") Integer id);

    /**
     * 实现多对多
     * */
    @Select("SELECT * FROM `user` where id>#{id}")
    @Results(value = {
            @Result(property = "id",column = "id",javaType=Integer.class,jdbcType=JdbcType.INTEGER),
            @Result(property = "userName",column = "userName",javaType = String.class),
            @Result(property = "userAge",column = "userAge",javaType = Integer.class),
            @Result(property = "userAddress",column = "userAddress",javaType = String.class),
            @Result(property = "blogs",javaType = List.class,column = "id",many = @Many(select = "com.maochong.mybatis.dao.BlogMapper.getUserBlog")), // 一对多
            @Result(property = "blog",javaType = BlogEntity.class,column = "id",one = @One(select = "com.maochong.mybatis.dao.BlogMapper.getUserBlogOne")), // 一对一
    })
    List<LessonUserBlogEntity> getUsetBlogList(@Param("id") Integer id);
    /**
     * 这里调用resultMap，这个是SQL配置文件中的,必须该SQL配置文件与本接口有相同的全限定名
     * 注意文件中的namespace路径必须是使用@resultMap的类路径
     * */
    @SelectProvider(type = BlogSqlProvider.class, method = "getSqlByTitle")
    // @ResultMap(value = "sqlBlogsMap")
    List<BlogEntity> getBlogByTitle(@Param("title") String title);

    @InsertProvider(type = BlogSqlProvider.class, method = "insertSql")
    int insertBlog(BlogEntity blog);

    @UpdateProvider(type = BlogSqlProvider.class, method = "updateSql")
    int updateBlog(BlogEntity blog);

    @DeleteProvider(type = BlogSqlProvider.class, method = "deleteSql")
    @Options(useCache = true,flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    int deleteBlog(int id);

}