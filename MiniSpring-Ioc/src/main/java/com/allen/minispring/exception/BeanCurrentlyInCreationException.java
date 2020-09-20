package com.allen.minispring.exception;

/**
 * @ClassName BeanCurrentlyInCreationException
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/10
 * @Version 1.0
 */
public class BeanCurrentlyInCreationException extends RuntimeException{

    private final String beanName;

    public BeanCurrentlyInCreationException(String beanName) {
        super(beanName + " is currently in creation! Is there a Constructor-Circular-Reference existed ?");
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
