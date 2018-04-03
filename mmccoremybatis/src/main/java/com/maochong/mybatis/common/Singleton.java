package com.maochong.mybatis.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jokin
 * */
public class Singleton {
    private  static Map<String,Object> singletonMap = new HashMap<>();

    /**
     * 构造函数有参数
     * */
    public static <T> T getInstance(final Class<T> clazz, Class<?>[] parameterTypes, Object[] initargs) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, IllegalAccessException{
        String clazzName = clazz.getName();

        if(!singletonMap.containsKey(clazzName)){
            synchronized(singletonMap){
                if(!singletonMap.containsKey(clazzName)){
                    Constructor<T> constructor = clazz.getConstructor(parameterTypes);
                    T instance = constructor.newInstance(initargs);
                    singletonMap.put(clazzName,instance);
                    return instance;
                }
            }
        }
        return (T) singletonMap.get(clazzName);
    }

    /**
     * 构造函数无参数
     * */
    public static <T> T getInstance(final Class<T> clazz) throws InstantiationException, IllegalAccessException{
        String clazzName = clazz.getName();
        if(!singletonMap.containsKey(clazzName)){
            synchronized(singletonMap){
                if(!singletonMap.containsKey(clazzName)){
                    T instance = clazz.newInstance();
                    singletonMap.put(clazzName,instance);
                    return instance;
                }
            }
        }
        return (T) singletonMap.get(clazzName);
    }
}
