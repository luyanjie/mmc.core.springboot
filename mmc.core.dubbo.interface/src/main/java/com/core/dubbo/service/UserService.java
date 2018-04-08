package com.core.dubbo.service;

import com.core.dubbo.domain.User;

public interface UserService {

    void register(User user);

    User getUserById(int id);
}
