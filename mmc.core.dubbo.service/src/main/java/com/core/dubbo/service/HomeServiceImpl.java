package com.core.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.core.dubbo.domain.HomeEntity;

@Service(version = "1.0.0",provider = "providerConfig2")
public class HomeServiceImpl implements HomeService {
    @Override
    public void backHome(HomeEntity homeEntity) {
        System.out.println(homeEntity==null?"没有值":homeEntity.getAddress());
    }

    @Override
    public HomeEntity getHome(int id) {
        if(id>0)
        {
            HomeEntity homeEntity = new HomeEntity();
            homeEntity.setId(1);
            homeEntity.setName("开心之家");
            homeEntity.setAddress("浙江省余姚市四明山镇");
            return homeEntity;
        }
        else
        {
            HomeEntity homeEntity = new HomeEntity();
            homeEntity.setId(0);
            homeEntity.setName("不开心之家");
            homeEntity.setAddress("福建省厦门市翔安区");
            return homeEntity;
        }
    }
}
