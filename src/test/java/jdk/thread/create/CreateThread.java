package jdk.thread.create;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程三种方式
 * Thread
 * Runnable
 * Callback或者Future
 */
public class CreateThread {
    class Thread1 extends Thread {
        @Override
        public void run(){
            int i = 0;
            while (true) {
                System.out.println("Say hello");
                i++;
                if (i == 10) {
                    break;
                }
            }
        }

    }

    // 继承Thread 实现run 方法
    @Test
    public void createThread1() throws InterruptedException {
        Thread create1 = new Thread1();
        create1.start();
        create1.join();
    }

    // 实现Runnable 方法
    @Test
    public void createThread2() throws InterruptedException {
        Thread create2 = new Thread(new Runnable(){
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    System.out.println("Say hello2");
                    i++;
                    if (i == 10) {
                        break;
                    }
                }
            }
        });
        create2.start();
    }

    class MyThread implements Callable {

        public Object call() throws Exception {
            System.out.println("excute:" + Thread.currentThread().getName());
            return Thread.currentThread().getName()+"name";
        }
    }

    @Test
    public void createCallable(){
        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask).start();

        try {
           System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}   
