package com.maochong.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DataSourceConfiguration {

    /**
     * 多数据源配置查看：https://blog.csdn.net/ichsonx/article/details/52053003
     * https://blog.csdn.net/ichsonx/article/details/52061608
     * */
    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    //@ConfigurationProperties(prefix="spring.datasourcejdbc.primary")
    public DataSource primaryDataSource() {
        // 使用DruidDataSource连接串配置
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl("jdbc:mysql://127.0.0.1:3306/lesson?useSSL=false&serverTimezone=UTC");
        datasource.setUsername("root");
        datasource.setPassword("jie88854859");
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
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



//    @Bean(name = "secondaryDataSource")
//    @Qualifier("secondaryDataSource")
//    @Primary
//    // @ConfigurationProperties(prefix="spring.datasourcejdbc.secondary")
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://127.0.0.1:3306/wanghong?useSSL=false&serverTimezone=UTC")
//                .username("root")
//                .password("jie88854859")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    @Bean(name = "secondaryJdbcTemplate")
//    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}