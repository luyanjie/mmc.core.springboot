package com.maochong.helloword.service.bean;

public class MyBeanService {

    public MyBeanService()
    {
        super();
        System.out.println("构造函数");
    }

    public void init()
    {
        System.out.println("this is init method");
    }

    public void  destroy(){
        System.out.println("this is destroy method");
    }
}
