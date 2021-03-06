package com.maochong.mybatis.controller;

import com.maochong.mybatis.common.enums.DataBaseTypeEnum;
import com.maochong.mybatis.dao.BlogMapper;
import com.maochong.mybatis.dao.LessonUserMapper;
import com.maochong.mybatis.dao.UserMapper;
import com.maochong.mybatis.entity.BlogEntity;
import com.maochong.mybatis.entity.LessonUser;
import com.maochong.mybatis.entity.LessonUserBlogEntity;
import com.maochong.mybatis.entity.User;
import com.maochong.mybatis.entity.typehandler.BlogTypeHandlerEntity;
import com.maochong.mybatis.entity.typehandler.Content;
import com.maochong.mybatis.service.mapper.UserMapperService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis操作数据库controller
 * @author jokin
 * */
@RestController
@RequestMapping("/mybatis")
public class MyBatisController {

    @Autowired
    LessonUserMapper lessonUserMapper;

    @Autowired
    UserMapperService userMapper;

    @Autowired
    BlogMapper blogMapper;

    @RequestMapping("hello.html")
    public String hello()
    {
        return "hello mybatis";
    }

    /**
     * http://localhost:8081/mybatis/lesson/add?name=name13&age=13&address=厦门13
     * */
    @RequestMapping("/lesson/add")
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int lessonAdd(String name, int age,String address)
    {
        Assert.notNull(name,"需要name参数");
        try {
            int back = lessonUserMapper.insert(name,age,address);
            // int num = 0;
            // back = back/num;
            return back;
        }
        catch (Exception ex)
        {
            // 当出错的时候手动处理事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return 0;
    }

    @RequestMapping("/lesson/add/batch")
    public int lessonAddBatch()
    {
        List<LessonUser> list = new ArrayList<>();
        for (int i=0;i<2;i++)
        {
            LessonUser lessonUser = new LessonUser();
            lessonUser.setUserAge(1+i);
            lessonUser.setUserName("name"+i);
            lessonUser.setUserAddress("address"+i);
            list.add(lessonUser);
        }
        return lessonUserMapper.insertBatch(list);
    }

    /**
     * http://localhost:8081/mybatis/lesson/add/blog/type
     * */
    @RequestMapping("/lesson/add/blog/type")
    public int blogAdd()
    {
        BlogTypeHandlerEntity blog = new BlogTypeHandlerEntity();
        blog.setUid(13);
        blog.setTitle("sn");
        blog.setDate("2018-04-03");
        blog.setAuthorName("mo");
        blog.setContent(new Content("province","city"));
        blog.setDataBase(DataBaseTypeEnum.MYSQL);
        return lessonUserMapper.insertBlogTypeHandler(blog);
    }

    /**
     * http://localhost:8081/mybatis/lesson/select/blog/type/3
     * */
    @RequestMapping("/lesson/select/blog/type/{id}")
    public BlogTypeHandlerEntity blogTypeSelect(@PathVariable("id") Integer id)
    {
        return lessonUserMapper.selectBlog(id);
    }

    @RequestMapping("/lesson/add/key")
    public int lessonAddKey(String name, int age,String address)
    {
        LessonUser lessonUser = new LessonUser();
        lessonUser.setUserAddress(address);
        lessonUser.setUserName(name);
        lessonUser.setUserAge(age);

        System.out.println(lessonUser.getId());

        // 返回主键方式1
        int back = lessonUserMapper.insertKey(lessonUser);
        System.out.println(lessonUser.getId());


//        // 返回主键方式2
//        lessonUser.setUserAddress(address+"1");
//        lessonUser.setUserName(name+"1");
//        lessonUser.setUserAge(age+1);
//        lessonUser.setId(0);
//        back = lessonUserMapper.insertReturnGeneratedKeys(lessonUser);
//        System.out.println(lessonUser.getId());
        return back;
    }



    /**
     * http://localhost:8081/mybatis/lesson/select/one/13
     * */
    @RequestMapping("/lesson/select/one/{id}")
    // @Cacheable(cacheNames = "user",key = "'user'+#id")
    public LessonUser lessonFindById(@PathVariable("id") Integer id) {
        return lessonUserMapper.findById(id);
    }

    /**
     * http://localhost:8081/mybatis/lesson/select/list/11
     * */
    @RequestMapping("/lesson/select/list/{id}")
    public List<LessonUser> findByIdList(@PathVariable("id") Integer id)
    {
        return lessonUserMapper.findByIdList(id);
    }

    /**
     * http://localhost:8081/mybatis/lesson/select/list/proc/1
     * */
    @RequestMapping("/lesson/select/list/proc/{id}")
    public List<LessonUser> findByIdListProc(@PathVariable("id") Integer id)
    {
        return lessonUserMapper.findByIdListProc(id);
    }

    /**
     * http://localhost:8081/mybatis/lesson/select/list/join/6
     * */
    @RequestMapping("/lesson/select/list/join/{id}")
    public LessonUserBlogEntity findByIdListJoin(@PathVariable("id") Integer id)
    {
        return blogMapper.getUsetBlogs(id);
    }

    /**
     * http://localhost:8081/mybatis/lesson/select/list/join/6
     * */
    @RequestMapping("/lesson/select/list/blog/{id}")
    public List<LessonUserBlogEntity> findByIdBlogList(@PathVariable("id") Integer id)
    {
        return blogMapper.getUsetBlogList(id);
    }

    /**
     * http://localhost:8081/mybatis/lesson/delete/5
     * */
    @RequestMapping("/lesson/delete/{id}")
    public int deleteUserFromId(@PathVariable("id") Integer id)
    {
        return lessonUserMapper.deleteUserFromId(id);
    }

    /**
     * http://localhost:8081/mybatis/lesson/update?userName=name12&userAge=12&userAddress=厦门12&id=12
     * */
    @RequestMapping("/lesson/update")
    public int updateUserFromId(LessonUser user)
    {
        return lessonUserMapper.updateUserFromId(user);
    }

    /**
     * http://localhost:8081/mybatis/lesson/find?name=luyanjie
     * */
    @RequestMapping("/lesson/find")
    public LessonUser lessonFindByName(String name)
    {
        return lessonUserMapper.findByName(name);
    }

    /**
     * http://localhost:8081/mybatis/lesson/find/like?name=luyanjie
     * */
    @RequestMapping("/lesson/find/like")
    public List<LessonUser> lessonFindByNameList(String name)
    {
        return lessonUserMapper.findByNameList("%"+name+"%");
    }

    @RequestMapping("/blog/select/{id}")
    public BlogEntity blogSelectId(@PathVariable("id") Integer id)
    {
        return blogMapper.getBlog(id);
    }
    @RequestMapping("/blog/select")
    public List<BlogEntity> getBlogByTitle(String title)
    {
        return blogMapper.getBlogByTitle(title);
    }

    @RequestMapping("/blog/select/all")
    public List<BlogEntity> blogSelect()
    {
        return blogMapper.getAllBlog();
    }

    @RequestMapping("/blog/insert")
    public int blogInsert(BlogEntity blog)
    {
        return blogMapper.insertBlog(blog);
    }

    @RequestMapping("/blog/update")
    public int blogUpdate(BlogEntity blog)
    {
        return blogMapper.updateBlog(blog);
    }

    @RequestMapping("/blog/delete")
    public int blogDelete(int id)
    {
        return blogMapper.deleteBlog(id);
    }


    @RequestMapping("/wanghong/add")
    public int wanghongAdd(String name, Integer order)
    {
        Assert.notNull(name,"需要name参数");
        return userMapper.wanghongAdd(name,order);
    }

    @RequestMapping("/wanghong/find/name/{name}")
    public User wangHongFindByName(@PathVariable("name") String name)
    {
        return userMapper.wangHongFindByName(name);
    }
}

