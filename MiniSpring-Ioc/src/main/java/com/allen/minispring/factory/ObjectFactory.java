package com.allen.minispring.factory;

/**
 * @ClassName ObjectFactory
 * @Description 定义获取一个对象的工厂方法功能
 * @Author XianChuLun
 * @Date 2020/9/10
 * @Version 1.0
 */
public interface ObjectFactory<T> {

    /**
     * 获取由该对象工厂管理的一个对象
     * @return 获取到的对象
     */
    T getObject();
}
