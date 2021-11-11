package jdk.lessons.threads.synctool;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * description:coundownLatch 实现
 * @author:Jerry 
 * @method: 
 * @date: 2018/11/27
 * @param:
 * @Returns:
 */
public class CountdownLatchDemo {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
   public void demo (){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread start");
                    try {
                        Thread.sleep(10000);
                        System.out.println("Thread Sleep end");
                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("before");
            Thread.sleep(50);
            System.out.println("await");
            countDownLatch.await();
            System.out.println("release");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
