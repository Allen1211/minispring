package com.allen.minispring.factory;

import com.allen.minispring.beans.BeanDefinitionRegistry;
import com.allen.minispring.exception.BeanDefinitionReadException;
import com.allen.minispring.io.Resource;

/**
 * @ClassName BeanDefinitionReader
 * @Description 定义从任何的配置文件和配置方式读取Bean定义的能力的接口
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public interface BeanDefinitionReader {

    /**
     * 获取Bean注册中心
     *
     * @return Bean注册中心
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 根据一个Resource加载bean定义，并返回加载到的bean定义的个数
     *
     * @param resource 要读取的资源
     * @return 读取成功的BeanDefinition数量
     * @throws BeanDefinitionReadException 当读取到非法定义且无法继续解析时抛出
     */
    int loadBeanDefinitions(Resource resource) throws BeanDefinitionReadException;

    /**
     * 根据多个Resource加载bean定义，并返回加载到的bean定义的个数
     *
     * @param resources 要读取的资源
     * @return 读取成功的BeanDefinition数量
     * @throws BeanDefinitionReadException 当读取到非法定义且无法继续解析时抛出
     */
     int loadBeanDefinitions(Resource... resources) throws BeanDefinitionReadException;


}
