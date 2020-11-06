package com.allen.minispring.factory.config;

import com.allen.minispring.factory.BeanFactory;

/**
 * @ClassName BeanFactoryPostProcessor
 * @Description 可以通过此接口在BeanFactory初始化后改变BeanFactory的一些行为
 * 比如：修改某些BeanDefinition里定义的属性值
 * @Author XianChuLun
 * @Date 2020/9/22
 * @Version 1.0
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {
    /**
     * 对BeanFactory做出修改
     * @param beanFactory BeanFactory
     */
    void postProcessBeanFactory(BeanFactory beanFactory);
}
