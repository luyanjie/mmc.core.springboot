package com.maochong.helloword.controller;

import com.maochong.helloword.dao.LessonUserMapper;
import com.maochong.helloword.entity.LessonUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mybatis")
public class LessonController {

    @Autowired
    LessonUserMapper lessonUserMapper;

    /**
     * http://localhost:8080/mybatis/lesson/select/one/1
     * */
    @RequestMapping("/lesson/select/one/{id}")
    public LessonUser lessonFindById(@PathVariable("id") Integer id)
    {
        return lessonUserMapper.findById(id);
    }

    /**
     * http://localhost:8080/mybatis/lesson/select/list/11
     * */
    @RequestMapping("/lesson/select/list/{id}")
    public List<LessonUser> findByIdList(@PathVariable("id") Integer id)
    {
        return lessonUserMapper.findByIdList(id);
    }
}
