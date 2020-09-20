package com.allen.minispring.test.bean;

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

    @Override
    public String toString() {
        return "CDPlayer{" +
                "brand='" + brand + '\'' +
                ", disk=" + disk.toString() +
                '}';
    }
}
