package jdk.lessons.threads.synctool;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class ReetantLock {
    static int i = 0;

    @Test
    public void test() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "Prepark");
                //LockSupport.park();
                reentrantLock.lock();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ++i);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "Prepark");
                //LockSupport.park();
                reentrantLock.lock();

                System.out.println(Thread.currentThread().getName() + ++i);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "Prepark");
                //LockSupport.park();
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName() + ++i);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        for(int i = 0; i < 2; i++){
            System.out.println(Thread.currentThread().getName() + "excute"+"{ "+i);
            //LockSupport.unpark(thread1);
            System.out.println("unpark");
        }                            
//
//        Thread.sleep(20000);
//        thread2.interrupt();
        Thread.sleep(520000);

    }
}
