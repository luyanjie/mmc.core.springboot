package com.maochong.helloword.config;

import com.maochong.helloword.service.bean.MyBeanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Bean初始化方法和销毁方法设定
 * @author jokin
 * */
// 注解是告诉spring这个类是一个配置类，相当于我们的xml文件
@Configuration
// 则是指定需要spring来扫描的包，相当于xml中的context:component-scan属性。
@ComponentScan("com.maochong.helloword.service.bean")
public class MyBeanConfiguration {

    /**
     * initMethod：初始化是执行MyBeanService.init方法，等同于在init方法上添加@PostConstruct注解
     * destroyMethod：销毁是执行MyBeanService.destroy，等同于在destroy方法上添加@PreDestroy注解
     * */
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public MyBeanService myBeanService(){
        return new MyBeanService();
    }
}
