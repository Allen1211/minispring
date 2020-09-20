package com.allen.minispring.exception;

/**
 * @ClassName BeanDefinitionReadException
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/6
 * @Version 1.0
 */
public class BeanDefinitionReadException extends Exception {

    public BeanDefinitionReadException(String message) {
        super(message);
    }

    public BeanDefinitionReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
