package com.maochong.helloword.service.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * prototype表示该bean在每次被注入的时候，都要重新创建一个实例，这种情况适用于有状态的Bean.
 * @author jokin
 * */
@Service
@Scope(value = "prototype")
public class PrototypeBeanService {
    private String name = "aaa";

    public void getName()
    {
        if(Objects.equals(name, "aaa")){
            System.out.println("Prototype name值为："+name);
            name = "bbb";
        }
        else if(Objects.equals(name, "bbb")){
            System.out.println("Prototype name值为："+name);
            name = "ccc";
        }
        else{
            System.out.println("Prototype name值为："+name);
        }
    }
}
