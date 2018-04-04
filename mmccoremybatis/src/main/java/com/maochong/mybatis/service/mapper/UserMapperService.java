package com.maochong.mybatis.service.mapper;

import com.maochong.mybatis.common.constant.MyDataSource;
import com.maochong.mybatis.common.enums.DataSourceType;
import com.maochong.mybatis.dao.UserMapper;
import com.maochong.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserMapperService {
    @Autowired
    UserMapper userMapper;

    @MyDataSource(DataSourceType.Slave)
    public int wanghongAdd(String name, Integer order)
    {
        Assert.notNull(name,"需要name参数");
        return userMapper.insert(name,order);
    }

    @MyDataSource(DataSourceType.Slave)
    public User wangHongFindByName(@PathVariable("name") String name)
    {
        return userMapper.findByName(name);
    }
}
