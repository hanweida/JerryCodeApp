package com.test.thread;

import com.jerry.framework.util.DateUtil;

/**
 * Created by ES-BF-IT-126 on 2018/8/6.
 */
public class ThreadTestJoin {
    public static void main(String[] args) {
        B b = new B();
        A a = new A(b);

        try {
            b.start();
            a.start();
            b.join();
            System.out.println(Thread.currentThread().getName()+"main 结束"+ DateUtil.getCurrentDateTimeString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class A extends Thread{
    private B b;
    public A (B b){
        this.b = b;
    }
    @Override
    public void run() {
        synchronized (b){
            System.out.println(Thread.currentThread().getName()+"A 启动"+ DateUtil.getCurrentDateTimeString());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"A END"+ DateUtil.getCurrentDateTimeString());
        }
    }
}

class B extends Thread{
    @Override
    synchronized public void run() {
            System.out.println(Thread.currentThread().getName()+"B 启动"+ DateUtil.getCurrentDateTimeString());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"B END"+ DateUtil.getCurrentDateTimeString());
        }
    }
