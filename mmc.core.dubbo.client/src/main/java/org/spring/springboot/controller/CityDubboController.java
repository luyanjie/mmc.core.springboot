package org.spring.springboot.controller;

import com.core.dubbo.domain.User;
import org.spring.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class CityDubboController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping("/find/use/{id}")
    public User findUseById(@PathVariable("id") int id)
    {
        return userServiceImpl.printCity(id);
    }

    @RequestMapping("/add/use")
    public String addUser()
    {
        userServiceImpl.register();
        return "success";
    }
}
