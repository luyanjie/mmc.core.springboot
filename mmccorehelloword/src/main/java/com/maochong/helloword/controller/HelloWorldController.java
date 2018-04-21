package com.maochong.helloword.controller;

import com.maochong.helloword.config.MyBeanConfiguration;
import com.maochong.helloword.config.ScopeConfig;
import com.maochong.helloword.service.bean.MyBeanService;
import com.maochong.helloword.service.scope.PrototypeBeanService;
import com.maochong.helloword.service.scope.SingletonBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/")
public class HelloWorldController {

    @Autowired
    SingletonBeanService singletonBeanService;

    @Autowired
    PrototypeBeanService prototypeBeanService;


    @Value("${server.port}")
    String port;

    @RequestMapping("/hello.html")
    public String hello()
    {

        String a = "18250756961";

        System.out.println(a.hashCode());

        String b = "13695442393";
        System.out.println(b.hashCode());
        // boolean bl = true;
        // 断言，需要JVM开始，在VM options添加：-ea 即可，只有bl为true才会往下执行，不然就会报错
        // assert bl;
        // System.out.println("ssss");
//        String  a = new String("aaa");
//        String b = new String("aaa");
//        System.out.println(a==b);
//        System.out.println(a.equals(b));
//        System.out.println(a.hashCode()==b.hashCode());

//        int a  = 1;
//        int b = 1;
//        int c = 1;
//        // 4
//        System.out.println(++a+b+++c++);
//        // 2
//        System.out.println("a="+a);
//        // 2
//        System.out.println("b="+b);
//        // 2
//        System.out.println("c="+c);

        return "hello world "+port;
    }

    @RequestMapping("/bean/singleton")
    public String singletonBean()
    {
        final int count = 3;
        ExecutorService executorService =  Executors.newFixedThreadPool(count);
        for (int i=0;i<count;i++)
        {
            executorService.submit(()->{
                singletonBeanService.getName();
            });
        }
        executorService.shutdown();
        return "success";
    }

    @RequestMapping("/bean/prototype")
    public String prototypeBean()
    {
        final int count = 5;
        ExecutorService executorService =  Executors.newFixedThreadPool(count);
        for (int i=0;i<count;i++)
        {
            executorService.submit(()->{
                prototypeBeanService.getName();
            });
        }
        executorService.shutdown();
        return "success";
    }

    @RequestMapping("/bean")
    public String bean()
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ScopeConfig.class);
        SingletonBeanService s1 = context.getBean(SingletonBeanService.class);
        SingletonBeanService s2 = context.getBean(SingletonBeanService.class);
        SingletonBeanService s3 = context.getBean(SingletonBeanService.class);

        /**
         * 依次输出：
         name值为：aaa
         name值为：bbb
         name值为：ccc
         * */
        s1.getName();
        s2.getName();
        s3.getName();

        PrototypeBeanService p1 = context.getBean(PrototypeBeanService.class);
        PrototypeBeanService p2 = context.getBean(PrototypeBeanService.class);
        PrototypeBeanService p3 = context.getBean(PrototypeBeanService.class);

        /**
         * 依次输出：
         Prototype name值为：aaa
         Prototype name值为：aaa
         Prototype name值为：aaa
         * */
        p1.getName();
        p2.getName();
        p3.getName();
        return "success";
    }

    @RequestMapping("/bean/init")
    public String beanInit() throws InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyBeanConfiguration.class);
        context.destroy();
        System.out.println("===========");
        return "success";
    }
}
