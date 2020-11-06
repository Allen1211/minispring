package com.allen.minispring.factory;

import com.allen.minispring.beans.propertyeditors.PropertyEditorRegistry;

import java.beans.PropertyEditor;

/**
 * @ClassName ConfigurableBeanFactory
 * @Description 提供对BeanFactory实例进行自定义配置的功能接口。如，为BeanFactory注册BeanFactoryPostProcessor
 * BeanPostProcessor等改变BeanFactory的运行时行为
 * @Author XianChuLun
 * @Date 2020/11/6
 * @Version 1.0
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor);

    PropertyEditorRegistry getPropertyEditorRegistry();

}
