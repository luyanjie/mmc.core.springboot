package com.maochong.helloword.controller;

import com.maochong.helloword.service.SortService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.DateUtils;
import org.thymeleaf.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * @author jokin
 * */
@RestController
@RequestMapping("/sort")
public class SortController {

    SortService sortService = new SortService();

    @RequestMapping("/bubble")
    public void  bubbleSort()
    {
        int num = 10000;
        int[] ints = sortService.intArrays(num);
        long startTime = System.currentTimeMillis();
        sortService.bubbleSort(num,ints);
        long endTime = System.currentTimeMillis();
        System.out.println("间隔时间："+(endTime-startTime));
    }

    @RequestMapping("/insert")
    public void insertSort()
    {
        int num = 10000;
        int[] ints = sortService.intArrays(num);
        long startTime = System.currentTimeMillis();
        sortService.insertSort(num,ints);
        long endTime = System.currentTimeMillis();
        System.out.println("间隔时间："+(endTime-startTime));
    }

    @RequestMapping("/quick")
    public void quickSort()
    {
        int num = 10000;
        int[] ints = sortService.intArrays(num);
        long startTime = System.currentTimeMillis();
        sortService.quickSort(ints,0,ints.length-1);
        long endTime = System.currentTimeMillis();
        System.out.println("间隔时间："+(endTime-startTime));
    }
}
