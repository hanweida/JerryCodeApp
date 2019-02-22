package com.test.thread.testparentlock;

public class Child extends Parent implements Runnable {
    @Override
    public void run() {
        sum();
    }
}
