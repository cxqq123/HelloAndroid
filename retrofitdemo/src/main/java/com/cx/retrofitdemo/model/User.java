package com.cx.retrofitdemo.model;

/**
 * Created by Administrator on 2017/9/6.
 */

public class User {
    //生成POJO类
    public String name;
    public int age;
    public String emailAddress;

    public User(String name, int age, String emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
