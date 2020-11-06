package com.allen.minispring.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName ReflectionUtil
 * @Description 反射操作工具类
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static <T> T instantiate(Class<T> clazz) throws ReflectiveOperationException{
        Constructor<T> constructor = clazz.getConstructor();
        return instantiate(constructor);
    }

    public static <T> T instantiate(Constructor<T> constructor, Object ...args) throws ReflectiveOperationException{
        return constructor.newInstance(args);
    }

    public static Object parseStringToObjectByClass(Class<?> clazz, String val){
        try {
            Constructor<?> constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(val);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setObjectProperty(Object object, Map<String, Object> propertyMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()){
            setObjectProperty(object, clazz, entry.getKey(), entry.getValue());
        }
    }

    public static void setObjectProperty(Object object, Class<?> clazz, String propertyName, Object propertyVal) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method setMethod = clazz.getMethod(setMethodName, propertyVal.getClass());
        setMethod.invoke(object, propertyVal);
    }

    public static Class<?> getFieldType(Class<?> clazz, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            return null;
        }

    }

}
