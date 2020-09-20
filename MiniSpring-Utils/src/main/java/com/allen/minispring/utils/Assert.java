package com.allen.minispring.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName Assert
 * @Description 断言类
 * @Author XianChuLun
 * @Date 2020/9/10
 * @Version 1.0
 */
public class Assert {

    private Assert() {
    }

    public static void notNull(Object obj, String message){
        if(Objects.isNull(obj)){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String str, String message){
        if(Objects.isNull(str) || str.isEmpty()){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message){
        if(Objects.isNull(collection) || collection.isEmpty()){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map, String message){
        if(Objects.isNull(map) || map.isEmpty()){
            throw new IllegalArgumentException(message);
        }
    }



}
