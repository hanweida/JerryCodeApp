package com.test.thread.testparentlock;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Parent{
                 int i = 0;
     AtomicInteger count = new AtomicInteger(0);

    public void sum(){
        System.out.println(Thread.currentThread().getName() + "\t"+count.getAndIncrement());
        System.out.println(this.toString());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Wake up" + Thread.currentThread().getName() + "\t"+count.getAndIncrement());
    }
}
