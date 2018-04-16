package com.maochong.mybatis.dao;


import com.maochong.mybatis.common.constant.MyDataSource;
import com.maochong.mybatis.common.enums.DataSourceType;
import com.maochong.mybatis.entity.LessonUser;
import com.maochong.mybatis.entity.typehandler.BlogTypeHandlerEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 操作Lesson数据库User表数据
 * 第一种 映射器接口中写SQL语句,映射器接口注解
 * @author jokin
 * */
@Mapper
public interface LessonUserMapper {
    /**
     * 根据name获取数据
     * @param name 玩家姓名
     *             @return LessonUser实体类
     * */
    @Select("SELECT * FROM lesson.`user` WHERE `userName` = #{name}")
    LessonUser findByName(@Param("name") String name);

    /**
     * 模糊查询
     * 方法一：需要数据库支持 CONCAT方法，可以直接传需要查的内容
     * ("SELECT * FROM lesson.`user` WHERE `userName` like CONCAT('%',#{name},'%')")
     * 方法二：传入值就是模糊范例，如：%name%
     *  ("SELECT * FROM lesson.`user` WHERE `userName` like #{name}")
     *  @param name 传入值
     *              @return list
     * */
    @Select("SELECT * FROM lesson.`user` WHERE `userName` like #{name}")
    List<LessonUser> findByNameList(@Param("name") String name);

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

    /**
     * select 数组是可以拼接语句的
     * SELECT * FROM lesson.`user` WHERE  id= #{id} or id= 7
     * @param id 玩家id
     *           @return list
     * */
    @Select({"SELECT * FROM lesson.`user` WHERE  id= #{id}"," or id= 7"})
    List<LessonUser> findByIdListOr(@Param("id") Integer id);

    /**
     * 调用存储过程
     * @param id 传入id参数
     *           @return List<LessonUser>
     * */
    @Select({"call BlogGetProc(#{id,mode=IN,jdbcType=INTEGER})"})
    @Options(statementType = StatementType.CALLABLE)
    List<LessonUser> findByIdListProc(@Param("id") Integer id);

    /**
     * 插入数据
     * @param name 玩家名称
     * @param age 玩家年龄
     * @param userAddress 住址
     *            @return int 返回成功条数
     * */
    @Insert("INSERT INTO lesson.`user`(`userName`, `userAge`,userAddress) VALUES(#{name}, #{age},#{userAddress})")
    int insert(@Param("name") String name, @Param("age") Integer age,@Param("userAddress") String userAddress);

    /**
     * 使用自定义类型加载器添加数据
     * @param blogEntity 实体类
     *           @return 执行条数
     * */
    @Insert("insert into blog(uid,title,date,authorName,content,`dataBase`) " +
            "VALUES(#{uid},#{title},#{date}," +
            "#{authorName},#{content,javaType=string,jdbcType=UNDEFINED,typeHandler=com.maochong.mybatis.common.typehandler.ContentTypeHandler}," +
            "#{dataBase,typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})")
    int insertBlogTypeHandler(BlogTypeHandlerEntity blogEntity);

    /**
     * 指定某一列使用自定义类型加载器
     * @param id 编号
     *           @return 实体类信息
     * */
    @Select("select * from blog where id=#{id}")
    @Results(value = {
            @Result(property = "content",column = "content",javaType = String.class,typeHandler = com.maochong.mybatis.common.typehandler.ContentTypeHandler.class),
            @Result(property = "dataBase",column = "dataBase",typeHandler = org.apache.ibatis.type.EnumOrdinalTypeHandler.class)
    })
    BlogTypeHandlerEntity selectBlog(@Param("id") Integer id);

    /**
     * 插入数据，使用@Options返回主键存入到lessonUser 的Id字段
     * @param lessonUser  User实体类
     *                    @return int 返回成功条数
     * */
    @Insert("INSERT INTO lesson.`user`(`userName`, `userAge`,userAddress) VALUES(#{userName}, #{userAge},#{userAddress})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertKey(LessonUser lessonUser);

    /**
     * 插入数据，使用@SelectKey返回主键存入到lessonUser 的Id字段
     * 如果@Options 和 @SelectKey 同时存在，看源码会使用@SelectKey的方式返回
     * @param lessonUser  User实体类
     *                    @return int 返回成功条数
     * */
    @Insert("INSERT INTO lesson.`user`(`userName`, `userAge`,userAddress) VALUES(#{userName}, #{userAge},#{userAddress})")
    //@Options(useGeneratedKeys = true,keyProperty = "id")
    @SelectKey(keyProperty = "id",before = false,resultType = Integer.class,statement ="SELECT LAST_INSERT_ID()",keyColumn = "id")
    int insertReturnGeneratedKeys(LessonUser lessonUser);

    /**
     * 批量插入数据
     * @param list  数据集合
     *              @return int
     * */
    @InsertProvider(type = BlogSqlProvider.class, method = "insertSqlBatch")
    int insertBatch(@Param("list") List<LessonUser> list);

    /**
     * 根据Id删除数据
     * @param id 传入的Id
     *           @return int
     * */
    @Delete("delete from lesson.`user` where id=#{id}")
    int deleteUserFromId(int id);

    /**
     * 根据Id修改数据
     * @param user 传入的数据
     *             @return int
     * */
    @Update("update lesson.`user` set userName=#{userName},userAge=#{userAge},userAddress=#{userAddress} where id=#{id}")
    int updateUserFromId(LessonUser user);


}

