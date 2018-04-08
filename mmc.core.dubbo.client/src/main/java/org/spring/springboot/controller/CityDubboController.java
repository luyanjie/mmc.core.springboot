package org.spring.springboot.controller;

import com.core.dubbo.domain.User;
import org.spring.springboot.domain.City;
import org.spring.springboot.dubbo.CityDubboConsumerService;
import org.spring.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class CityDubboController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping("/find/city")
    public User findCityByName()
    {
        return userServiceImpl.printCity(1);
    }
}
