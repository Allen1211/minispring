package com.allen.minispring.utils;


import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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

    public static <T> T instantiate(Class<T> clazz) throws ReflectiveOperationException {
        Constructor<T> constructor = clazz.getConstructor();
        return instantiate(constructor);
    }

    public static <T> T instantiate(Constructor<T> constructor, Object... args) throws ReflectiveOperationException {
        return constructor.newInstance(args);
    }

    public static Object parseStringToObjectByClass(Class<?> clazz, String val) {
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
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            setObjectProperty(object, clazz, entry.getKey(), entry.getValue());
        }
    }

    public static void setObjectProperty(Object object, Class<?> clazz, String propertyName, Object propertyVal) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method setMethod = clazz.getMethod(setMethodName, propertyVal.getClass());
        setMethod.invoke(object, propertyVal);
    }

    public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }


    private static final Map<Class<?>, Function<String, Object>> PARSE_METHOD =
            new HashMap<Class<?>, Function<String, Object>>() {{
                put(short.class, Short::parseShort);
                put(int.class, Integer::parseInt);
                put(long.class, Long::parseLong);
                put(float.class, Float::parseFloat);
                put(double.class, Double::parseDouble);
                put(boolean.class, Boolean::parseBoolean);
                put(Short.class, Short::valueOf);
                put(Integer.class, Integer::valueOf);
                put(Long.class, Long::valueOf);
                put(Float.class, Float::valueOf);
                put(Double.class, Double::valueOf);
                put(Boolean.class, Boolean::valueOf);
            }};

    public static Object parseStrArrToTypeArr(String[] strArr, Class<?> componentType) {
        Object arr = Array.newInstance(componentType, strArr.length);
        Function<String, Object> parseFunction = PARSE_METHOD.get(componentType);
        if (parseFunction == null) {
            throw new IllegalArgumentException("can not parse componentType: " + componentType);
        }
        for (int i = 0; i < strArr.length; i++) {
            Array.set(arr, i, parseFunction.apply(strArr[i]));
        }
        return arr;
    }

    public static Object parseStrArrToTypeArr(String[] strArr, Class<?> componentType,
                                              Function<String, Object> parseMethod) {
        Object arr = Array.newInstance(componentType, strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            Array.set(arr, i, parseMethod.apply(strArr[i]));
        }
        return arr;
    }
}
