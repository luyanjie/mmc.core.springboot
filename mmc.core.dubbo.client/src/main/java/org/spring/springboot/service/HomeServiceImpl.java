package org.spring.springboot.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.core.dubbo.domain.HomeEntity;
import com.core.dubbo.service.HomeService;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceImpl {

    @Reference(version = "1.0.0")
    HomeService homeService;

    public HomeEntity printHome(int id)
    {
        return homeService.getHome(id);
    }

    public void backHome(HomeEntity homeEntity)
    {
        homeService.backHome(homeEntity);
    }
}
