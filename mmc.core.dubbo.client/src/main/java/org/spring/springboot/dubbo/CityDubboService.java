package org.spring.springboot.dubbo;

import org.spring.springboot.domain.City;

public interface CityDubboService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
    City findCityByName(String cityName);
}