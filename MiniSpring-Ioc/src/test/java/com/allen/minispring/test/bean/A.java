package com.allen.minispring.test.bean;

/**
 * @ClassName A
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/9
 * @Version 1.0
 */
public class A {
    private B b;

    public A() {
    }

    public A(B b) {
        this.b = b;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "This is A";
    }
}
