package com.allen.minispring.factory;

/**
 * @ClassName SingletonBeanRegistry
 * @Description 单例Bean注册中心 定义了单例对象的注册、获取、移除等功能
 * @Author XianChuLun
 * @Date 2020/9/10
 * @Version 1.0
 */
public interface SingletonBeanRegistry {

    /**
     * 注册一个已创建的单例对象到单例对象注册中心
     * @param beanName beanName
     * @param singletonObject 已创建完成的单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 根据beanName从单例对象缓存池尝试获取单例对象
     * 获取到的可能是早期对象（若刚被创建而未被初始化 且 该bean定义允许提前暴露），也可能是初始化完成后的完整对象，也可能是null
     * @param beanName beanName
     * @return 要获取的单例对象 可能是早期对象，也可能是初始化完成后的完整对象，也可能是null
     */
    Object getSingleton(String beanName);

    /**
     * 根据beanName从单例对象缓存池尝试获取单例对象
     * 获取到的可能是早期对象（若刚被创建而未被初始化 且 该enableEarlyExpose为true），也可能是初始化完成后的完整对象，也可能是null
     * @param beanName beanName
     * @param enableEarlyExpose 是否允许早期暴露
     * @return 要获取的单例对象
     */
    Object getSingleton(String beanName, boolean enableEarlyExpose);

    /**
     * 根据beanName从单例对象缓存池尝试获取单例对象
     * 若获取不到，则通过objectFactory获取该bean
     * @param beanName beanName
     * @param objectFactory 该bean的ObjectFactory
     * @return 尝试获取的单例对象
     */
    Object getSingleton(String beanName, ObjectFactory<?> objectFactory);

    /**
     * 从单例对象缓存池去除beanName所对应的对象
     * @param beanName beanName
     */
    void removeSingleton(String beanName);
}
