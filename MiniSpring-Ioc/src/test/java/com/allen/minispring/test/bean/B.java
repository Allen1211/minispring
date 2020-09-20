package com.allen.minispring.test.bean;

/**
 * @ClassName B
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/9
 * @Version 1.0
 */
public class B {
    private A a;

    public B() {
    }

    public B(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "This is B";
    }
}
