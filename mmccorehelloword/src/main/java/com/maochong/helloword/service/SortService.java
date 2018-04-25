package com.maochong.helloword.service;

import java.util.*;

/**
 * 排序算法
 * @author jokin
 * */
public class SortService {

    /**
     * 冒泡排序
     * */
    public void bubbleSort(int num,int[] ints)
    {
        for (int i=1;i<num;i++)
        {
            for (int j=0;j<num-1;j++)
            {
                if(ints[j]>ints[j+1])
                {
                    int temp = ints[j];
                    ints[j] = ints[j+1];
                    ints[j+1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     * */
    public void ChoseSort(int num,int[] ints)
    {
        for (int i=0;i<num;i++)
        {
            int min = i;
            for(int j=i;j<num;j++)
            {
                min = ints[j]<ints[min]?j:min;
            }
            if(min==i)
            {
                continue;
            }
            ints[i] = ints[i]^ints[min];
            ints[min] = ints[i]^ints[min];
            ints[i] = ints[i]^ints[min];
        }
    }

    /**
     * 插入排序
     * */
    public int[]  insertSort(int num,int[] ints)
    {
        for (int i=1;i<num;i++)
        {
            // 当前比较的值
            int temp = ints[i];
            // 当前比较值的前一个下标 index
            int j = i-1;
            while (j>=0 && ints[j]>temp)
            {
                ints[j+1] = ints[j];
                j--;
            }
            ints[j+1] = temp;
        }
        return ints;
    }

    /**
     * 快速排序
     * */
    public void quickSort(int[] arr,int low,int high)
    {
        //1,找到递归算法的出口
        if( low > high) {
            return;
        }
        //2, 存
        int i = low;
        int j = high;
        //3,key
        int key = arr[ low ];
        //4，完成一趟排序
        while( i< j) {
            //4.1 ，从右往左找到第一个小于key的数
            while(i<j && arr[j] > key){
                j--;
            }
            // 4.2 从左往右找到第一个大于key的数
            while( i<j && arr[i] <= key) {
                i++;
            }
            //4.3 交换
            if(i<j) {
                int p = arr[i];
                arr[i] = arr[j];
                arr[j] = p;
            }
        }
        // 4.4，调整key的位置
        int p = arr[i];
        arr[i] = arr[low];
        arr[low] = p;
        //5, 对key左边的数快排
        quickSort(arr, low, i-1 );
        //6, 对key右边的数快排
        quickSort(arr,i+1, high);
    }


    public int[] intArrays(int num)
    {
        int[] ints = new int[num];
        Random random = new Random(System.currentTimeMillis());
        for (int i=0;i<num;i++)
        {
            int value = random.nextInt(num*10 +1);
            if(!ints.equals(value))
            {
                ints[i] = value;
            }
        }
        return ints;
    }
}
