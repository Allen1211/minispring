package com.allen.minispring.test.bean;

import com.allen.minispring.test.bean.*;

/**
 * @ClassName Store
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/14
 * @Version 1.0
 */
public class Store {
    private CDPlayer cdPlayer;
    private Counter counter;
    private Person staff;

    public Store(CDPlayer cdPlayer) {
        this.cdPlayer = cdPlayer;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Person getStaff() {
        return staff;
    }

    public void setStaff(Person staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "Store{" +
                "cdPlayer=" + cdPlayer +
                ", counter=" + counter +
                ", staff=" + staff +
                '}';
    }
}
