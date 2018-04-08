package com.core.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.core.dubbo.domain.User;

/**
 * 服务提供
 * @author jokin
 * @data 2018-04-08
 * */
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Override
    public void register(User user) {
        if(user == null){
            System.out.println("传null 给我，想害我啊");
        }
        else {
            System.out.println(user.getId());
        }
    }

    @Override
    public User getUserById(int id) {
        User user = new User();
        if(id >0 )
        {
            user.setId(10000L);
            user.setName("jokin");
            user.setOrder(100);
        }
        else {
            user.setId(1L);
            user.setName("maochong");
            user.setOrder(1);
        }
        return user;
    }
}
