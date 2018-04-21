package com.maochong.helloword.controller;

import com.maochong.helloword.service.hash.HashRingFictitiousNoteService;
import com.maochong.helloword.service.hash.HashRingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/hash")
public class HashController {

    /**
     * 创建hash环，多节点分配，应用于数据库分表操作，Redis多节点操作
     * */
    @RequestMapping("/ring")
    public String ring()
    {
        String[] ips = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (String ip:ips
             ) {
            System.out.println(StringUtils.concat("[",ip,"]的hash值", HashRingService.getHash(ip),",被路由到的节点：", HashRingService.getServer(ip)));
        }
        return "success";
    }

    /**
     * 借助虚拟节点，实现分布平衡的hash算法
     * */
    @RequestMapping("/ring/fictitious/note")
    public String fictitiousNote()
    {
        HashMap<String,Integer> map= new HashMap<>();
        List<String> id= new ArrayList<>();
        int count = 1000;
        for(int i=0;i<count;i++){
            id.add(UUID.randomUUID().toString().replace("-", ""));
        }
        for (String anId : id) {
            String aString = HashRingFictitiousNoteService.getServer(anId);
            map.merge(aString, 1, (a, b) -> a + b);
        }
        Set<String> set= map.keySet();
        for(String a:set){
            System.out.println("节点【"+a+"】分配到元素个数为==>"+map.get(a));
        }
        return "success";
    }
}
