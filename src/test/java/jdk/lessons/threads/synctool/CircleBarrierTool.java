package jdk.lessons.threads.synctool;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏
 * Created by ES-BF-IT-126 on 2018/5/31.
 */
public class CircleBarrierTool {

    static class Worker implements Runnable{
        CyclicBarrier cyclicBarrier;
        @Override
        public void run() {
            System.out.println("Runnable Run：" + Thread.currentThread().getId());
            try {
                if(12 == Thread.currentThread().getId()){
                    cyclicBarrier.reset();
                }
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println("BrokenBarrierException："+Thread.currentThread().getId());
            }
            System.out.println("Runnable final：" + Thread.currentThread().getId());
        }

        Worker(CyclicBarrier cyclicBarrier){
           this.cyclicBarrier = cyclicBarrier;
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("inside runable");
            }
        };
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, runnable);
        for(int i =0; i < 7; i++){
            new Thread(new Worker(cyclicBarrier)).start();
        }
    }
}
