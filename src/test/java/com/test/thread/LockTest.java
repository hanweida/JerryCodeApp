package com.test.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@SuppressWarnings("restriction")
public class LockTest {
    private Object obj = new Object();
    private int count = 0;
//    @Before
//    public void setUp() {
//
//    }
//
//    @After
//    public void tearDown() {
//    }

    @Test
    public void testUnsafe() {
        CountDownLatch latch=new CountDownLatch(10000);;
        for (int i = 0; i < 10000; i++) {
            Worker worker  = new Worker(latch);
            worker.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public int inc(){
        synchronized(this){
            System.out.println(count);
            return ++count;

        }
    }

    private Lock lock = new Lock();
    public int lockInc() {
        try {
            lock.lock();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int newCount = ++count;
        System.out.println(count);
        lock.unlock();
        return newCount;
    }
    public class Lock{
        private boolean isLocked = false;

        public synchronized void lock()
                throws InterruptedException{
          //while(isLocked){
//            if(isLocked){
                wait();
            //}
            isLocked = true;
        }

        public synchronized void unlock(){
            isLocked = false;
            notify();
        }
    }
    class Worker extends Thread {
        private CountDownLatch latch;
        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }
        public void run() {
//          inc();
            lockInc();
            latch.countDown();
        }
    }
}
