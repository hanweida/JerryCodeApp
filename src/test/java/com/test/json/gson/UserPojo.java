package com.test.json.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 基础，使用与 Gson基础使用与SerializedName 备用名
 * Created with IntelliJ IDEA.
 * User: yingmule
 * Date: 17-12-19
 * Time: 上午12:30
 * To change this template use File | Settings | File Templates.
 */
public class UserPojo {
    public UserPojo() {
    }

    public UserPojo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserPojo(int age) {
        this.age = age;
    }

    @SerializedName(value = "name", alternate = {"username", "stuname"})
    String name;
    int age;

    @Override
    public String toString() {
        return "UserPojo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
