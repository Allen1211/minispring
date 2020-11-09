package com.allen.minispring.test.bean;

import com.allen.minispring.test.bean.*;

import java.util.Arrays;

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
    private char[] levels;

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

    public char[] getLevels() {
        return levels;
    }

    public void setLevels(char[] levels) {
        this.levels = levels;
    }

    @Override
    public String toString() {
        return "Store{" +
                "cdPlayer=" + cdPlayer +
                ", counter=" + counter +
                ", staff=" + staff +
                ", levels=" + Arrays.toString(levels) +
                '}';
    }
}
