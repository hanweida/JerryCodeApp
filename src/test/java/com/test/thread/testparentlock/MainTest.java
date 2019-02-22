package com.test.thread.testparentlock;

import org.junit.Test;

public class MainTest {
    @Test
    public void test() throws InterruptedException {
        for(int i = 0; i< 2;i++){
            Thread thread = new Thread(new Child());
            thread.start();
        }
        Thread.currentThread().join();
    }
}
