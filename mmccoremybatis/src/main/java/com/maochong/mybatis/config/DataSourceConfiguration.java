package com.maochong.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.maochong.mybatis.common.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author jokin
 * */
@Configuration
public class DataSourceConfiguration {

    /**
     * 多数据源配置查看：https://blog.csdn.net/ichsonx/article/details/52053003
     * https://blog.csdn.net/ichsonx/article/details/52061608
     * https://blog.csdn.net/xiazai353503200/article/details/79390879  为主
     * */
    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    /**
     * \@Primary和@Qualifier这两个注解的意思:
     *      \@Primary：  意思是在众多相同的bean中，优先使用用@Primary注解的bean.
     *      \@Qualifier ： 这个注解则指定某个bean有没有资格进行注入。
     * */
    @Bean(name = "master")
    public DataSource primaryDataSource() {
        System.out.println("主配");
        return druidDataSource("jdbc:mysql://127.0.0.1:3306/lesson?useSSL=false&serverTimezone=UTC","root","jie88854859","com.mysql.cj.jdbc.Driver");
    }

    @Bean(name = "slave")
    public DataSource secondaryDataSource() {
        System.out.println("从配");
        return druidDataSource("jdbc:mysql://127.0.0.1:3306/wanghong?useSSL=false&serverTimezone=UTC","root","jie88854859","com.mysql.cj.jdbc.Driver");
    }

    @Bean(name="dynamicDataSource")
    @Qualifier("dynamicDataSource")
    @Primary
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DataSource master = primaryDataSource();
        DataSource slave = secondaryDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(master);
        //配置多数据源
        Map<Object,Object> map = new HashMap<>();
        // key需要跟ThreadLocal中的值对应
        map.put(DataSourceType.Master.getName(), master);
        map.put(DataSourceType.Slave.getName(), slave);
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }


    private DruidDataSource druidDataSource(String url,String userName,String password,String driverClassName)
    {
        // 使用DruidDataSource连接串配置
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        // 初始化大小，最小，最大
        datasource.setInitialSize(5);
        datasource.setMinIdle(5);
        datasource.setMaxActive(20);
        // 配置获取连接等待超时的时间
        datasource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(300000);
        datasource.setValidationQuery("SELECT 1");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        // 打开PSCache，并且指定每个连接上PSCache的大小
        datasource.setPoolPreparedStatements(true);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        Properties properties = new Properties();
        properties.setProperty("druid.stat.mergeSql","true");
        properties.setProperty("druid.stat.slowSqlMillis","5000");
        datasource.setConnectProperties(properties);
        try {
            // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            datasource.setFilters("stat,wall,log4j");
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }

        return datasource;
        //        return DataSourceBuilder.create()
//                .url("jdbc:mysql://127.0.0.1:3306/lesson?useSSL=false&serverTimezone=UTC")
//                .username("root")
//                .password("jie88854859")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                //.type(DruidDataSource.class)
//                .build();
    }

}