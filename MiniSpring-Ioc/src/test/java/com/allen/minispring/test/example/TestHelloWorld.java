package com.allen.minispring.test.example;

import com.allen.minispring.exception.BeanDefinitionReadException;
import com.allen.minispring.factory.BeanFactory;
import com.allen.minispring.factory.XmlBeanFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @ClassName TestHelloWorld
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/19
 * @Version 1.0
 */
public class TestHelloWorld {
    @Test
    public void runHelloWorld() throws BeanDefinitionReadException {
        BeanFactory beanFactory = new XmlBeanFactory("HelloWorld.xml");

        HelloWorld helloWorld = (HelloWorld) beanFactory.getBean("helloWorld");

        Assert.assertNotNull(helloWorld);

        System.out.println(helloWorld.getGreeting() + " now is " + helloWorld.getTimeHolder());
    }
}
