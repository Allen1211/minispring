package com.allen.minispring.utils;

/**
 * @ClassName StringUtils
 * @Description 字符串工具类
 * @Author XianChuLun
 * @Date 2020/9/8
 * @Version 1.0
 */
public class StringUtils {

    private StringUtils(){
    }

    public static boolean notEmpty(String s){
        return !isEmpty(s);
    }

    public static boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }

}
