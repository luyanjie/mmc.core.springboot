package com.maochong.mybatis.service;


import org.apache.ibatis.jdbc.SqlRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 使用MyBatis 提供的SqlRunner 来进行数据库操作
 * @author jokin
 * */
@Service
public class MyBatisSqlRunnerService {
    @Autowired()
    @Qualifier("dynamicDataSource")
    DataSource dataSource;

    /**
     * 根据Id 获取一条数据
     * @param id 传入编号
     * */

    public void  selectOne(int id) throws Exception
    {
        SqlRunner sqlRunner = new SqlRunner(dataSource.getConnection());
        Map<String, Object> map = sqlRunner.selectOne("select * from blog where id=?",new Object[]{id});
        if(map!=null && map.size()>0) {
            // key 会所有默认成大写
            System.out.println(map.get("title".toUpperCase()));
        }
        else {
            System.out.println("data is null");
        }

        List<Map<String,Object>> list = sqlRunner.selectAll("select * from blog where id>=?", id);
        if(list !=null){
            list.forEach(x->{
                // key 会所有默认成大写
                System.out.println(x.get("title".toUpperCase()));
                // key 会所有默认成大写
                System.out.println(x.get("date".toUpperCase()));
                // key 会所有默认成大写
                System.out.println(x.get("authorName".toUpperCase()));
                // key 会所有默认成大写
                System.out.println(x.get("content".toUpperCase()));
            });
        }

        // 插入数据 设置这个返回的是自增量id大小
        sqlRunner.setUseGeneratedKeySupport(true);
        int count = sqlRunner.insert("insert into blog(title,date,authorName,content) values(?,?,?,?)", "no","2018-03-31","jokin","hi");
        System.out.println(StringUtils.concat("插入数据","--","返回结果",count));

        // 更新数据
        sqlRunner.update("update blog set title=? where id=?","无所谓",2);
        // 删除数据
        sqlRunner.delete("delete from blog where id=?",1);
        // 执行一条sql
        sqlRunner.run("update blog set content='你想知道吗' where id=1 ");
    }
}
