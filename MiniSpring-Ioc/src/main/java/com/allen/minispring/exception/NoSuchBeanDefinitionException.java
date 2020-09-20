package com.allen.minispring.exception;

/**
 * @ClassName NoSuchBeanDefinitionException
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public class NoSuchBeanDefinitionException extends BeansException{

    private String beanName;

    private Class<?> beanClass;

    public NoSuchBeanDefinitionException(String beanName) {
        super(beanName + " can not be found");
    }

    public NoSuchBeanDefinitionException(String beanName, String message) {
        super(message);
        this.beanName = beanName;
    }

    public NoSuchBeanDefinitionException(Class<?> beanClass) {
        super(beanClass + " can not be found");
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}
