package com.allen.minispring.factory;

import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;

/**
 * @ClassName BeanFactory
 * @Description Bean工厂接口。定义了IoC容器的基本接口功能。
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public interface BeanFactory {

    /**
     * 根据Bean的名称，获取Bean实例
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据Bean的名称和（构造器/工厂方法）所需参数获取bean实例
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 根据Bean的类型获取Bean实例.
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 根据Bean的类型和构造器/工厂方法所需参数获取Bean实例.
     */
    <T> T getBean(Class<T> requiredType, Object... args) throws BeansException;

    /**
     * 判断当前的Bean工厂中是否包括给定名称的Bean定义.
     */
    boolean containsBean(String name);

    /**
     * 判断给定名称的Bean在当前的Bean工厂中是否为单实例Bean. singleton
     */
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     * 判断给定名称的Bean在当前的Bean工厂中是否为多实例. prototype
     */
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

}
