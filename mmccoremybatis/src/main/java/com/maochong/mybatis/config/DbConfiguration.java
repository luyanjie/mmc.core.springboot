package com.maochong.mybatis.config;


import com.maochong.mybatis.common.enums.DataBaseTypeEnum;
import com.maochong.mybatis.entity.DbConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jokin
 * @date 2018-03-30
 * */
@Repository
public class DbConfiguration {
    private final JdbcTemplate jdbcTemplate;

    private static final Map<Integer,JdbcTemplate> MAP_CONFIG = new HashMap<>();

    @Autowired
    public DbConfiguration(@Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getLesson() {
        return getJdbc("lesson", DataBaseTypeEnum.MYSQL,null);
    }

    public JdbcTemplate getWangHong(){ return getJdbc("wanghong",DataBaseTypeEnum.MYSQL, null);}

    public JdbcTemplate getGameJointDb(){ return getJdbc("gamejointDB",DataBaseTypeEnum.MYSQL, null);}

    private JdbcTemplate getJdbc(String dbName, DataBaseTypeEnum dataBaseTypeEnum, DbConfigEntity dbConfigEntity)
    {
        JdbcTemplate jdbcTemplate;
        Integer dbIdentity = dbName.hashCode() ^ dataBaseTypeEnum.hashCode();
        jdbcTemplate = MAP_CONFIG.getOrDefault(dbIdentity,null);
        if(jdbcTemplate != null)
        {  return jdbcTemplate;}

        synchronized (MAP_CONFIG){
            jdbcTemplate = MAP_CONFIG.getOrDefault(dbIdentity,null);
            if(jdbcTemplate != null) {
                return jdbcTemplate;
            }
            jdbcTemplate = dbConfigEntity != null
                    ? new JdbcTemplate(getDataSource(dbConfigEntity))
                    : new JdbcTemplate(getJdbcTemplate(dbName,dataBaseTypeEnum.getNum()));
            MAP_CONFIG.put(dbIdentity,jdbcTemplate);
            return jdbcTemplate;
        }
    }

    private DataSource getDataSource(DbConfigEntity dbConfigEntity) {
        return (dbConfigEntity == null)
                ? null
                : DataSourceBuilder.create()
                .url(dbConfigEntity.getUrl())
                .username(dbConfigEntity.getUsername())
                .password(dbConfigEntity.getPassword())
                .driverClassName(dbConfigEntity.getDriverClassName())
                .build();
    }

    private DataSource getJdbcTemplate(String dbName,int dbType)
    {
        final String sql = "select dbName,dbType,url,username,`password`,driverClassName from dbConfig where dbName=? and dbType=?";
        RowMapper<DbConfigEntity> rowMapper = new BeanPropertyRowMapper<>(DbConfigEntity.class);
        DbConfigEntity dbConfigEntity = jdbcTemplate.queryForObject(sql,rowMapper, dbName,dbType);
        return getDataSource(dbConfigEntity);
    }
}
