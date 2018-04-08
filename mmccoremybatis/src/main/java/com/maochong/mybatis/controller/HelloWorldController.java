package com.maochong.mybatis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/")
public class HelloWorldController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/hello.html")
    public String hello()
    {
        return "hello world " + port;
    }
}
