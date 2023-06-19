package com.jay.base.bean;

/**
 * created by hj on 2023/6/6.
 */
public class TestBean {
    private int age;
    private String name;
    private int type;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
