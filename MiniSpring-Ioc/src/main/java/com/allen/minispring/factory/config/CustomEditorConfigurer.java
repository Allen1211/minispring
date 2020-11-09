package com.allen.minispring.factory.config;

import com.allen.minispring.factory.ConfigurableBeanFactory;

import java.beans.PropertyEditor;
import java.util.Map;

/**
 * @ClassName CustomEditorConfigurer
 * @Description 该类是BeanFactoryPostProcessor的一个实现
 * 提供方便地在容器启动时注册自定义PropertyEditor到容器中的功能
 * @Author XianChuLun
 * @Date 2020/11/7
 * @Version 1.0
 */
public class CustomEditorConfigurer implements BeanFactoryPostProcessor{

    private Map<Class<?>, PropertyEditor> customEditors;

    public Map<Class<?>, PropertyEditor> getCustomEditors() {
        return customEditors;
    }

    public void setCustomEditors(Map<Class<?>, PropertyEditor> customEditors) {
        this.customEditors = customEditors;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableBeanFactory beanFactory) {
        if(customEditors != null) {
            this.customEditors.forEach(beanFactory::registerCustomEditor);
        }
    }

}
