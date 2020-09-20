package com.allen.minispring.test.example;

/**
 * @ClassName HelloWorld
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/19
 * @Version 1.0
 */
public class HelloWorld {

    private String greeting;

    private TimeHolder timeHolder;

    public HelloWorld(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public TimeHolder getTimeHolder() {
        return timeHolder;
    }

    public void setTimeHolder(TimeHolder timeHolder) {
        this.timeHolder = timeHolder;
    }

    @Override
    public String toString() {
        return "HelloWorld{" +
                "greeting='" + greeting + '\'' +
                ", timeHolder=" + timeHolder +
                '}';
    }
}
