package com.allen.minispring.exception;

/**
 * @ClassName BeanCreationException
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/15
 * @Version 1.0
 */
public class BeanCreationException extends BeansException{

    private final String beanName;

    public BeanCreationException(String message, String beanName) {
        super(message);
        this.beanName = beanName;
    }

    public BeanCreationException(String message, Throwable cause, String beanName) {
        super(message, cause);
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
