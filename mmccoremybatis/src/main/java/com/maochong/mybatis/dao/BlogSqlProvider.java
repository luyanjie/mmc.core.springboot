package com.maochong.mybatis.dao;

import com.maochong.mybatis.entity.LessonUser;
import org.apache.ibatis.jdbc.SQL;
import org.thymeleaf.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class BlogSqlProvider {
    private final static String TABLE_NAME = "blog";
    public String getSql() {
        //Integer id =  (Integer)parameter.get("id");
        String sql = new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("id=#{id}");
            ORDER_BY("id desc");
        }}.toString();
        System.out.println(sql);
        return sql;
        // 可以直接写sql 返回： select * from blog where id = #{id} order by id desc 一样的效果
    }

    public String getAllSql() {
        String sql = new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            ORDER_BY("id");
        }}.toString();
        System.out.println(sql);
        return sql;
    }

    public String getSqlByTitle(Map<String, Object> parameter) {
        String title = (String) parameter.get("title");

        String sql = new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            if(!StringUtils.isEmptyOrWhitespace(title))
            {
                WHERE("title like #{title}");
            }
            ORDER_BY("id");
        }}.toString();
        System.out.println(sql);
        return sql;
    }

    public String insertSql() {

        String sql = new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("title", "#{title}");
            VALUES("date", "#{date}");
            VALUES("authorName", "#{authorName}");
            VALUES("content", "#{content}");
        }}.toString();
        System.out.println(sql);
        return sql;
    }

    public String insertSqlBatch(Map<String, Object> parameter)
    {
        List<LessonUser> list = (List<LessonUser>)parameter.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO lesson.`user` ");
        sb.append("(`userName`, `userAge`,userAddress) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].userName'}',#'{'list[{0}].userAge'}', #'{'list[{0}].userAddress'}')");
        for (int i = 0; i < list.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String deleteSql() {
        String[] conditions = {"id=#{id}"};
        String sql = new SQL(){{
            DELETE_FROM(TABLE_NAME);
            WHERE(conditions);
        }}.toString();
        System.out.println(sql);
        return sql;
    }

    public String updateSql() {
        return new SQL(){{
            UPDATE(TABLE_NAME);
            SET("content = #{content}");
            WHERE("id = #{id}");

        }}.toString();
    }
}

