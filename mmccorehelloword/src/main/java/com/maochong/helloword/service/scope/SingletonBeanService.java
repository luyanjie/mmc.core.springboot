package com.maochong.helloword.service.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 设置单例型bean，singleton表示该bean全局只有一个实例，Spring中bean的scope默认也是singleton.
 * 以下有状态的name输出就会有问题，适合无状态的
 * @author jokin
 * */
@Service
@Scope(value = "singleton")
public class SingletonBeanService {
    private String name = "aaa";

    public void getName()
    {
        if(Objects.equals(name, "aaa")){
            System.out.println("name值为："+name);
            name = "bbb";
        }
        else if(Objects.equals(name, "bbb")){
            System.out.println("name值为："+name);
            name = "ccc";
        }
        else{
            System.out.println("name值为："+name);
        }
        
    }
}
