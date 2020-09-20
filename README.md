# minispring


A simple IoC and AOP container implement refer to spring-framework.

一个参考spring框架实现的，简单易懂的IoC及AOP框架。


- minispring是为了更好地学习spring-framework而建立的。
- minispring旨在用简洁的方式实现spring-framework中最基本的IoC容器及AOP框架的核心功能。
- minispring仅关注主流程而忽略了完备的可用性和可扩展性，简化了源码中复杂的封装及类结构，目的是能够清晰地展现spring-framework中的核心概念及核心流程。

## Table of Contents

- [Background](#background)
- [Usage](#usage)
- [API](#api)
- [Structure](#structure)
- [Change Log](#change-log)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Background
众所周知，对于Java开发者而言，Spring框架的原理、架构及源码一直都是很好的学习材料。从原理可以学到控制反转、面向切面、解耦等思想，从架构和源码可以学到设计模式的最佳实践......

但我在学习Spring原理及源码过程中体会到:
1. 自己适当造造轮子，才能理解得更深，在实际开发遇到问题才容易解决。 
2. Spring框架的源码对于学习的范本而言过于庞大，套娃式的封装和复杂的类组织结构令人望而生畏，难以理解。


以上两点是这个“造轮子”项目的由来。

## Usage
minispring基于`jdk1.8.0_201`，使用maven构建，使用IntelliJ IDEA开发。

``` shell
$ git clone https://github.com/Allen1211/minispring.git

$ mvn clean install
```

#### 运行Hello-world范例

1. 在 test目录下，找到`com.allen.minispring.test.example.TestHelloWorld`
2. 运行 `runHelloWorld()`测试方法

#### 自己编写一个测试

1. 在resources目录下，编写XML配置文件, 如 

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans>

        <bean id="timeHolder" name="timeHolder" class="com.allen.minispring.test.example.TimeHolder" scope="prototype"/>
    
        <bean id="helloWorld" name="helloWorld" class="com.allen.minispring.test.example.HelloWorld">
            <constructor-arg name="greeting" value="Hello MiniSpring!" class="java.lang.String"/>
            <property name="timeHolder" ref="timeHolder"/>
        </bean>

    </beans>
    ```
2. 编写测试代码

    ```java
    public class TestHelloWorld {
        @Test
        public void runHelloWorld() throws BeanDefinitionReadException {
            BeanFactory beanFactory = new XmlBeanFactory("HelloWorld.xml");

            HelloWorld helloWorld = (HelloWorld) beanFactory.getBean("helloWorld");

            Assert.assertNotNull(helloWorld);

            System.out.println(helloWorld.getGreeting() + " now is " + helloWorld.getTimeHolder());
        }
    }

    ```

3. 执行

    ``` shell
    22:25:57.615 [main] DEBUG com.allen.minispring.factory.XmlBeanDefinitionReader - Read And Registered BeanDefinition. name: timeHolder
    22:25:57.620 [main] DEBUG com.allen.minispring.factory.XmlBeanDefinitionReader - Read And Registered BeanDefinition. name: helloWorld
    Hello MiniSpring! now is TimeHolder{time=2020-09-19T22:25:57.679}
    ```

## Structure

待补充

## API

待补充

## Change Log

### v1.0 (2020/09/19)

- Bean定义方式: 通过XML配置文件方式定义Bean。支持定义构造器参数、property参数。
- Bean作用域: 支持单例(singleton)、多例(phototype)。
- 依赖注入: 支持构造器依赖注入，setter方法依赖注入。
- 循环依赖: 支持属性循环依赖。构造器循环依赖将抛异常提示。
- 通过BeanFactory根据BeanName获取Bean示例。


## Maintainers

[@Allen1211](https://github.com/Allen1211)

## Contributing

#### 贡献代码流程

创建features/bug分支 => 编写代码 => 发Pull Request请求merge到master分支 => review => merge到master分支

#### 规范

参考阿里巴巴开发手册

## License

MIT © 2020 Allen0x4bb
