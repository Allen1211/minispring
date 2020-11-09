package com.allen.minispring.test.bean;

import java.util.Arrays;

/**
 * @ClassName CDPlayer
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/8
 * @Version 1.0
 */
public class CDPlayer {

    private String brand;

    private Disk disk;

    private Integer[] status;

    public CDPlayer(String brand, Disk disk) {
        this.brand = brand;
        this.disk = disk;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "CDPlayer{" +
                "brand='" + brand + '\'' +
                ", disk=" + disk +
                ", status=" + Arrays.toString(status) +
                '}';
    }
}
