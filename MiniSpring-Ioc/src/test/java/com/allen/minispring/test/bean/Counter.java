package com.allen.minispring.test.bean;

/**
 * @ClassName Counter
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/15
 * @Version 1.0
 */
public class Counter {

    private Double height;
    private Double width;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}
