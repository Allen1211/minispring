package com.allen.minispring.test.example;

import java.time.LocalDateTime;

/**
 * @ClassName TimeHolder
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/19
 * @Version 1.0
 */
public class TimeHolder {

    private LocalDateTime time;

    public TimeHolder() {
        this.time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TimeHolder{" +
                "time=" + time +
                '}';
    }
}
