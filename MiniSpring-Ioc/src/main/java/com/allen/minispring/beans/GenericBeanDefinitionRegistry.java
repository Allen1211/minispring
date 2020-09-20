package com.allen.minispring.beans;

import com.allen.minispring.exception.BeansException;
import com.allen.minispring.exception.NoSuchBeanDefinitionException;

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
     * 实际存储BeanName及其对应的BeanDefinition的映射的容器
     */
    protected final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<>(16);

    /**
     * 存储已注册的BeanDefinition的BeanName
     */
    protected final List<String> beanDefinitionNames = new ArrayList<>(16);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if(Objects.isNull(beanName)){
            throw new IllegalArgumentException("beanName can not be null");
        }
        if(Objects.isNull(beanDefinition)){
            throw new IllegalArgumentException("beanDefinition can not be null");
        }
        if(beanDefinitionMap.containsKey(beanName)){
            throw new BeansException("BeanDefinition has already resigtered");
        }
        beanDefinitionMap.put(beanName, beanDefinition);
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
}
