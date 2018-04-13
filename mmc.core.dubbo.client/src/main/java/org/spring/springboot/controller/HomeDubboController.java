package org.spring.springboot.controller;

import com.core.dubbo.domain.HomeEntity;
import org.spring.springboot.service.HomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo/home")
public class HomeDubboController {

    @Autowired
    HomeServiceImpl homeService;

    @RequestMapping("/find/{id}")
    public HomeEntity findId(@PathVariable("id") int id)
    {
        return homeService.printHome(id);
    }

    @RequestMapping("/back")
    public void backHome()
    {
        HomeEntity homeEntity = new HomeEntity();
        homeEntity.setName("名曰");
        homeEntity.setId(-1000);
        homeEntity.setAddress("不知道的地方");
        homeService.backHome(homeEntity);
    }
}
