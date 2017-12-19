package com.test.json.gson;

/**
 * 使用于List<T> 泛型基础解析
 * Created with IntelliJ IDEA.
 * User: yingmule
 * Date: 17-12-19
 * Time: 上午1:01
 * To change this template use File | Settings | File Templates.
 */
public class UserPojo2<T>{
    public int code;
    public String message;
    public T data;
}
