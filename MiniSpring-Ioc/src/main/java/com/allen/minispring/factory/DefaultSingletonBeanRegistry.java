package com.allen.minispring.factory;

import com.allen.minispring.exception.BeanCurrentlyInCreationException;
import com.allen.minispring.exception.BeansException;
import com.allen.minispring.utils.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultSingletonBeanRegistry
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/10
 * @Version 1.0
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    /**
     * 一级缓存：单例对象的缓存，也被称作单例缓存池
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(16);

    /**
     * 二级缓存：保存对单实例bean的包装对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /**
     * 三级缓存：提前曝光的单例对象的缓存，用于检测循环引用
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 创建单实例Bean时，用于保证bean创建的唯一性。保存当前正在创建中的bean的名称.
     */
    private final Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notEmpty(beanName, "beanName must not be empty!");
        Assert.notNull(beanName, "singletonObject must not be null!");

        synchronized (this.singletonObjects){
            if(this.singletonObjects.get(beanName) != null){
                throw new IllegalStateException("Singleton with name :" + beanName + " is already registered");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    /**
     * 把一个已经创建好的单例对象加入到单例缓存池
     * 同时会把该bean的ObjectFactory和半成品从二三级缓存中移除
     * @param beanName beanName
     * @param singletonObject 已创建完成的单例对象
     */
    protected void addSingleton(String beanName, Object singletonObject){
        synchronized (this.singletonObjects) {
            /* 将创建好的单实例bean放入到单例缓存池中 */
            this.singletonObjects.put(beanName, singletonObject);
            /* 从三级缓存中删除(该对象已被完整创建，不再需要ObjectFactory提供半成品) */
            this.singletonFactories.remove(beanName);
            /* 从二级缓存中删除(该对象已被完整创建，不再需要未初始化的早期对象 */
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 把一个单例对象的ObjectFactory加入到二级对象工厂缓存池
     * 同时会把该bean原来的ObjectFactory和半成品从二三级缓存中移除
     * @param beanName beanName
     * @param singletonFactory 单例对象的ObjectFactory
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "SingletonFactory must not be null！");
        synchronized (this.singletonObjects) {
            /* 如果当前的单实例缓存池中还没有beanName对应的单实例bean */
            if (!this.singletonObjects.containsKey(beanName)) {
                /*将当前beanName对应的ObjectFactory放入到三级缓存singletonFactories中 */
                this.singletonFactories.put(beanName, singletonFactory);
                /* 从早期的单例对象缓存中移除beanName对应的bean实例 */
                this.earlySingletonObjects.remove(beanName);
            }
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    @Override
    public Object getSingleton(String beanName, boolean enableEarlyExpose) {
        Assert.notEmpty(beanName, "beanName must not be empty!");
        // 先从一级单例对象缓存池获取完整的对象，若获取为空，则说明该Bean还未被完整创建
        // 若 该bean正在被创建 且 该bean允许早期暴露
        // 则先从早期暴露bean缓存中获取，获取不到的话再尝试获取该bean的ObjectFactory来获取早期bean
        Object singleton = this.singletonObjects.get(beanName);
        if(singleton == null && isSingletonCurrentlyInCreation(beanName) && enableEarlyExpose){
            if((singleton = this.earlySingletonObjects.get(beanName)) == null){
                ObjectFactory<?> objectFactory;
                if((objectFactory = this.singletonFactories.get(beanName)) != null){
                    if((singleton  = objectFactory.getObject()) != null){
                        this.earlySingletonObjects.put(beanName, singleton);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singleton;
    }

    @Override
    public Object getSingleton(String beanName, ObjectFactory<?> objectFactory) {
        Assert.notEmpty(beanName, "beanName must not be empty!");
        synchronized (this.singletonObjects){
            // 先从一级缓存尝试获取创建好的bean
            Object singletonObject = this.singletonObjects.get(beanName);
            // 没有获取到，尝试从objectFactory获取
            if(Objects.isNull(singletonObject)){
                // 首先检查并设置bean正在被创建，排除构造器循环引用
                checkAndSetSingletonCurrentlyInCreation(beanName);

                // 尝试从objectFactory获取
                try {
                    singletonObject = objectFactory.getObject();
                } catch(Exception be){
                    be.printStackTrace();
                    throw be;
                } finally {
                    // 检查并设置bean未正在被创建
                    checkAndSetSingletonCurrentlyNotInCreation(beanName);
                }

                // 创建成功，加入到缓存中
                if(Objects.nonNull(singletonObject)){
                    addSingleton(beanName, singletonObject);
                }
            }
            return singletonObject;
        }
    }

    @Override
    public void removeSingleton(String beanName) {
        synchronized (this.singletonObjects){
            this.singletonObjects.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
        }
    }


    /**
     * 判断该bean是否正在被创建，通常指该bean对象被创建，但是还未完成初始化工作，即还未加入到一级缓存池
     * @param beanName beanName
     * @return 该bean是否正在被创建
     */
    public boolean isSingletonCurrentlyInCreation(String beanName){
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

    /**
     * 在单例bean创建之前调用，把该bean设置为 “正在创建”
     * 若该Bean已经在创建了，通常是构造器循环引用造成的，是无法解决的问题，抛出BeanCurrentlyInCreationException
     * @param beanName beanName
     */
    protected void checkAndSetSingletonCurrentlyInCreation(String beanName) {
        if (!this.singletonsCurrentlyInCreation.add(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }

    /**
     * 在单例bean创建之后调用，把该bean设置为 “不在创建中”
     */
    protected void checkAndSetSingletonCurrentlyNotInCreation(String beanName) {
        this.singletonsCurrentlyInCreation.remove(beanName);
    }
}
