package com.test.json.gson;

/**
 * 使用于List<T> 泛型基础解析
 * Created with IntelliJ IDEA.
 * User: yingmule
 * Date: 17-12-19
 * Time: 上午1:07
 * To change this template use File | Settings | File Templates.
 */
public class Result<T> {
    public int code;
    public String message;
    public T data;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
