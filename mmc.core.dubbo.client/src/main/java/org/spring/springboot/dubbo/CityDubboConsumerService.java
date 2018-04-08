package org.spring.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.spring.springboot.domain.City;
import org.springframework.stereotype.Component;

@Component
public class CityDubboConsumerService {

    @Reference(version = "1.0.0")
    CityDubboService cityDubboService;

    public City printCity() {
        String cityName="浙江宁波";
        City city = cityDubboService.findCityByName(cityName);
        System.out.println(city.toString());
        return city;
    }
}