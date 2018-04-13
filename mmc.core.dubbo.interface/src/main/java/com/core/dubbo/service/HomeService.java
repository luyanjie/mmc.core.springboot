package com.core.dubbo.service;

import com.core.dubbo.domain.HomeEntity;

/**
 * home接口
 * */
public interface HomeService {
    /**
     * 回家
     * @param homeEntity  实体类
     * */
    void backHome(HomeEntity homeEntity);

    /**
     * 获取家庭信息
     * */
    HomeEntity getHome(int id);
}
