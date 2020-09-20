package com.allen.minispring.beans;

import com.allen.minispring.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BeanDefinitionValueResolver
 * @Description 该类把BeanDefinition中定义的待注入的值(通常为包装类型)转换为实际的值。
 * @Author XianChuLun
 * @Date 2020/9/8
 * @Version 1.0
 */
public class BeanDefinitionValueResolver {

    /**
     * 通过该beanFactory获取待注入的bean
     */
    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValue(Object value){
        if(value instanceof RunTimeReferenceBean){
            RunTimeReferenceBean rb = (RunTimeReferenceBean) value;
            return this.beanFactory.getBean(rb.getBeanName());
        }else {
            return value;
        }
    }

    public Object[] resolveValues(Object[] values){
        List<Object> resolvedValues = new ArrayList<>(values.length);
        for(Object value : values){
            Object resolvedValue = resolveValue(value);
            resolvedValues.add(resolvedValue);
        }
        return resolvedValues.toArray();
    }

    public void resolveValues(ConstructorArgumentValues cav){
        List<ConstructorArgumentValues.ArgumentValue> listArgumentValues = cav.getListArgumentValues();
        for (ConstructorArgumentValues.ArgumentValue ag : listArgumentValues){
            if(!ag.isConverted()){
                Object value = resolveValue(ag.getValue());
                ag.setTypeClazz(value.getClass());
                ag.setValue(value);
                ag.setConverted(true);
            }
        }
    }


}
