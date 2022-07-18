package jdk.thread.base;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现线程的等待和通知
 * 加深对锁基础的理解
 */
public class WaitAndNotifyImplements {
    // 互斥锁
    AtomicInteger x = new AtomicInteger(0);
    BlockingQueue<Thread> blockingQueue = new LinkedBlockingQueue<Thread>();
    BlockingQueue<Thread> waitQueue = new LinkedBlockingQueue<Thread>();



    static int a = 0;
    static int b = 0;
    static int c = 0;
    MyLock myLock = new MyLock();
    MyLock.MyCondition conditionInc = myLock.newCondition();
    MyLock.MyCondition conditionSub = myLock.newCondition();



    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                myLock.lock();
                if (a < 10) {

                }
                System.out.println("currentThread：" + Thread.currentThread().getName() + "a:" + a + "b:" + b + "c:" + c);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myLock.unlock();
            }
        }
    }

    public void inc(){
        while (true) {
            myLock.lock();
            System.out.println("inc:" + Thread.currentThread().getName());
            if (a < 10) {
                a++;
                conditionSub.notifyCondition();
                System.out.println("wait: a:" + a + " in " + Thread.currentThread().getName());
            } else {
                System.out.println("wait: a:" + a + " in " + Thread.currentThread().getName());
                conditionInc.waitCondition();
            }
            myLock.unlock();
        }
    }

    public void sub(){
        while (true) {
            myLock.lock();
            System.out.println("sub:" + Thread.currentThread().getName());
            if (a == 0) {
                System.out.println("wait: a:" + a + " in " + Thread.currentThread().getName());
                conditionSub.waitCondition();
            } else {
                a--;
                conditionInc.notifyCondition();
                System.out.println("wait: a:" + a + " in " + Thread.currentThread().getName());
            }
            myLock.unlock();
        }
    }


    @Test
    public void test(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                inc();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sub();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyLock implements CustomLock{
        @Override
        public void lock() {
            // 如果没有获得到锁，一直自旋
            while (true) {
                System.out.println("lock currentThread :" + Thread.currentThread().getName());
                if (x.get() > 0) {

                } else {
                    if (x.compareAndSet(0, 1)) {
                        // 拿到锁
                        System.out.println("catch locked currentThread :" + Thread.currentThread().getName());
                        return;
                    } else {
                        // 没拿到锁
                    }
                }
                // 没拿到锁，放入阻塞队列
                blockingQueue.offer(Thread.currentThread());
                // 挂起当前线程
                LockSupport.park();
            }
        }

        @Override
        public void unlock() {
            x.compareAndSet(1, 0);
            Thread blockThread = blockingQueue.poll();
            if (blockThread != null) {
                LockSupport.unpark(blockThread);
            }
        }

        public MyCondition newCondition(){
            return new MyCondition();
        }

        class MyCondition implements CustomConditionWait{
            @Override
            public void waitCondition() {
                // 当前线程进入等待队列
                waitQueue.add(Thread.currentThread());
                // 释放锁
                x.compareAndSet(1, 0);
                // 阻塞线程执行
                Thread blockThread = blockingQueue.poll();
                if (null != blockThread) {
                    // 唤醒阻塞线程
                    LockSupport.unpark(blockThread);
                    // 挂起当前线程
                    LockSupport.park();
                }
            }

            @Override
            public void notifyCondition() {
                // 收到唤醒通知
                Thread waitThread = waitQueue.poll();
                // 进入到阻塞队列
                if (null != waitThread) {
                    blockingQueue.offer(waitThread);
                }
            }
        }
    }
}



