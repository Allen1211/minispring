package com.allen.minispring.beans;

/**
 * @ClassName RunTimeReferenceBean
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/8
 * @Version 1.0
 */
public class RunTimeReferenceBean {

    private final String beanName;

    private final Class<?> clazz;

    public RunTimeReferenceBean(String beanName) {
        this(beanName, null);
    }

    public RunTimeReferenceBean(String beanName, Class<?> clazz) {
        this.beanName = beanName;
        this.clazz = clazz;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
