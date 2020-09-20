package com.allen.minispring.factory;

import com.allen.minispring.beans.BeanDefinition;
import com.allen.minispring.beans.BeanDefinitionRegistry;
import com.allen.minispring.exception.BeanDefinitionReadException;
import com.allen.minispring.io.Resource;

/**
 * @ClassName AbstractBeanDefinitionReader
 * @Description BeanDefinitionReader的抽象实现类，实现模板方法。
 *              具体针对不同配置文件(XML,java config, annotation)的读取方法由子类实现
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    /**
     * BeanDefinition注册中心
     */
    protected BeanDefinitionRegistry registry;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionReadException {
        int count = 0;
        for (Resource resource : resources){
            count += loadBeanDefinitions(resource);
        }
        return count;
    }

    protected void doRegisterBeanDefinition(BeanDefinition beanDefinition){
        this.registry.registerBeanDefinition(beanDefinition.getBeanName(), beanDefinition);
    }
}
