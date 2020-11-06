package com.allen.minispring.test.bean;

/**
 * @ClassName Counter
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/15
 * @Version 1.0
 */
public class Counter {

    private Integer id;
    private String brand;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
