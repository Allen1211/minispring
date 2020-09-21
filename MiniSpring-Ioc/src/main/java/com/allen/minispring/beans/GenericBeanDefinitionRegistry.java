package com.allen.minispring.beans;

import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;
import com.allen.minispring.utils.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName GenericBeanDefinitionRegistry
 * @Description BeanDefinitionRegistry的一般实现
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public class GenericBeanDefinitionRegistry implements BeanDefinitionRegistry {

    /**
     * 注册中心容器初始值大小
     */
    private static final int DEFAULT_CONTAINER_SIZE = 16;

    /**
     * 实际存储BeanName及其对应的BeanDefinition的映射的容器
     */
    protected final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<>(DEFAULT_CONTAINER_SIZE);

    /**
     * 存储已注册的BeanDefinition的BeanName
     */
    protected final List<String> beanDefinitionNames = new ArrayList<>(DEFAULT_CONTAINER_SIZE);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if(Objects.isNull(beanName)){
            throw new IllegalArgumentException("beanName can not be null");
        }
        if(Objects.isNull(beanDefinition)){
            throw new IllegalArgumentException("beanDefinition can not be null");
        }
        if(beanDefinitionMap.containsKey(beanName)){
            throw new BeansException("BeanName: " + beanName +" has already registered");
        }
        beanDefinitionMap.put(beanName, beanDefinition);

        this.beanDefinitionNames.add(beanName);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        if(Objects.isNull(beanName)){
            throw new IllegalArgumentException("beanName can not be null");
        }
        if(beanDefinitionMap.remove(beanName) == null){
            throw new NoSuchBeanDefinitionException(beanName);
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        if(Objects.isNull(beanName)){
            throw new IllegalArgumentException("beanName can not be null");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(Objects.isNull(beanDefinition)){
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return beanDefinition;
    }

    @Override
    public BeanDefinition[] getBeanDefinition(Class<?> clazz) {
        List<BeanDefinition> result = new ArrayList<>();
        String[] names = getBeanDefinitionNames();
        for(String name : names){
            BeanDefinition beanDefinition = getBeanDefinition(name);
            if(isTypeMatch(clazz, beanDefinition)){
                result.add(beanDefinition);
            }
        }
        return result.toArray(new BeanDefinition[0]);
    }

    @Override
    public String[] getBeanDefinitionNamesByType(Class<?> clazz) {
        List<String> result = new ArrayList<>();
        String[] names = getBeanDefinitionNames();
        for(String name : names){
            BeanDefinition beanDefinition = getBeanDefinition(name);
            if(isTypeMatch(clazz, beanDefinition)){
                result.add(name);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        if(Objects.isNull(beanName)){
            throw new IllegalArgumentException("beanName can not be null");
        }
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[1]);
    }

    private boolean isTypeMatch(Class<?> clazz, BeanDefinition beanDefinition) {
        return beanDefinition.getBeanClass() != null && beanDefinition.getBeanClass().equals(clazz);
    }
}
