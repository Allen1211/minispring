package com.allen.minispring.beans;

import com.allen.minispring.exception.NoSuchBeanDefinitionException;

/**
 * @ClassName BeanDefinitionRegistry
 * @Description BeanDefinition的注册中心，每当一个BeanDefinition被解析创建，都应注册到注册中心。
 * 该接口提供了从注册中心中的BeanDefinition进行增删改查的功能
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public interface BeanDefinitionRegistry {

    /**
     * 将Bean定义注册到Bean定义注册中心
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 从Bean定义注册中心按照bean的名称移除Bean定义
     */
    void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 根据Bean的名称从Bean定义注册中心获取Bean定义.
     */
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 根据类型从Bean定义注册中心获取Bean定义.
     * @param clazz 类型
     * @return 所有与该类型匹配的bean定义
     */
    BeanDefinition[] getBeanDefinition(Class<?> clazz);

    /**
     * 返回类型与clazz匹配的所有BeanDefinition的名字
     * @param clazz class对象
     * @return 类型与clazz匹配的所有BeanDefinition的名字
     */
    String[] getBeanDefinitionNamesByType(Class<?> clazz);

    /**
     * 判断Bean定义注册中心是否包括给定名称的Bean定义
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取当前Bean定义注册中心的所有Bean定义的名称
     */
    String[] getBeanDefinitionNames();

}
