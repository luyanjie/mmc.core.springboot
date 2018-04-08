package org.spring.springboot.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.core.dubbo.domain.User;
import com.core.dubbo.service.UserService;

import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl {

    @Reference(version = "1.0.0")
    UserService userService;

    public User printCity(int id) {
        User user = userService.getUserById(id);
        System.out.println(user.getName().toString());
        return user;
    }

}
