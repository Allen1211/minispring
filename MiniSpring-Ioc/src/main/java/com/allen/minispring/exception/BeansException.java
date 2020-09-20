package com.allen.minispring.exception;

/**
 * @ClassName BeansException
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/4
 * @Version 1.0
 */
public class BeansException extends RuntimeException{

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
