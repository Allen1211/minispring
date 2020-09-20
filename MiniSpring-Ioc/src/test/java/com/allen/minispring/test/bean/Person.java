package com.allen.minispring.test.bean;

/**
 * @ClassName Person
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/5
 * @Version 1.0
 */
public class Person {

    private Integer id;

    private String name;

    public Person() {
        this.id = -1;
        this.name = "unknown";
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
