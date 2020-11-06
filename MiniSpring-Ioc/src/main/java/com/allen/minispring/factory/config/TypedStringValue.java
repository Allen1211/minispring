package com.allen.minispring.factory.config;

/**
 * @ClassName TypedStringValue
 * @Description
 * @Author XianChuLun
 * @Date 2020/11/6
 * @Version 1.0
 */
public class TypedStringValue {

    private final String value;

    private final Class<?> targetType;

    public TypedStringValue(String value, Class<?> targetType) {
        this.value = value;
        this.targetType = targetType;
    }

    public String getValue() {
        return value;
    }

    public Class<?> getTargetType() {
        return targetType;
    }
}
