package com.allen.minispring.factory;

import com.allen.minispring.exception.BeanDefinitionReadException;
import com.allen.minispring.io.ClassPathResource;
import com.allen.minispring.io.Resource;

/**
 * @ClassName XmlBeanFactory
 * @Description DefaultBeanFactory的子类，基于XML配置文件建立Bean工厂
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class XmlBeanFactory extends DefaultBeanFactory{

    private final XmlBeanDefinitionReader reader;

    public XmlBeanFactory(String resourceClassPathLocation) throws BeanDefinitionReadException {
        this(new ClassPathResource(resourceClassPathLocation));
    }

    public XmlBeanFactory(Resource resource) throws BeanDefinitionReadException {
        super();
        this.reader =  new XmlBeanDefinitionReader(super.beanDefinitionRegistry);
        this.reader.loadBeanDefinitions(resource);
    }

    public XmlBeanFactory(Resource... resources) throws BeanDefinitionReadException {
        super();
        this.reader =  new XmlBeanDefinitionReader(super.beanDefinitionRegistry);
        this.reader.loadBeanDefinitions(resources);
    }

}
