package com.maochong.mybatis.controller;

import com.maochong.mybatis.service.MyBatisSqlRunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mybatis/sql/runner")
public class MyBatisSqlRunnerController {

    @Autowired
    MyBatisSqlRunnerService myBatisSqlRunnerService;

    @RequestMapping("/test")
    public String test(int id) throws Exception
    {
        myBatisSqlRunnerService.selectOne(id);
        return "success";
    }
}