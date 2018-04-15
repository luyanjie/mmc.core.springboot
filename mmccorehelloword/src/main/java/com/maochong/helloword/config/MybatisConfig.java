package com.maochong.helloword.config;

import com.maochong.helloword.plugins.LessonPlugin;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


//@Configuration
//@MapperScan(basePackages = "com.maochong.helloword.dao")
//@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfig {
//    @Autowired
//    @Qualifier("dataSource")
//    public DataSource dataSource;
//
//    @Lazy(false)
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory localSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
////        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{new TestTypeHandle()});
////        sqlSessionFactoryBean.setTypeHandlersPackage("com.gupao.dal.typehandles");
//        // 配置拦截器Plugin
//        Properties properties = new Properties();
//        properties.setProperty("aaa","bbbb");
//        Interceptor interceptor = new LessonPlugin();
//        interceptor.setProperties(properties);
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{interceptor});
//        //sqlSessionFactoryBean.setPlugins(new Interceptor[]{new LessonPlugin()});
//
//       // sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor()});
//        SqlSessionFactory factory = sqlSessionFactoryBean.getObject();
//        //lazy loading switch
//        factory.getConfiguration().setLazyLoadingEnabled(true);
//        factory.getConfiguration().setAggressiveLazyLoading(false);
//        //factory.getConfiguration().setProxyFactory(new CglibProxyFactory());
//        return factory;
//    }
//
//
//    @Primary
//    @Lazy(false)
//    @Bean(name = "sqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//        return new SqlSessionTemplate(localSessionFactoryBean(), ExecutorType.SIMPLE);
//    }
//
//    @Lazy(false)
//    @Bean(name = "batchSst")
//    public SqlSessionTemplate batchSst() throws Exception {
//        return new SqlSessionTemplate(localSessionFactoryBean(), ExecutorType.BATCH);
//    }
//
//    @Bean(name = "txManager")
//    public DataSourceTransactionManager dataSourceTransactionManager() {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(dataSource);
//        return dataSourceTransactionManager;
//    }

}
