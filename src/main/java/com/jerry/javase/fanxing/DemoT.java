package com.jerry.javase.fanxing;

import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public class DemoT<T> {
    private List<T> demo;
    private T ints;

    public List<T> getDemo() {
        return demo;
    }

    public void setDemo(List<T> demo) {
        this.demo = demo;
    }

    public T getInts() {
        return ints;
    }

    public void setInts(T ints) {
        this.ints = ints;
    }
}
