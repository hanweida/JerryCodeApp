package jdk.lessons.threads.synctool;

import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号量
 * Created by ES-BF-IT-126 on 2018/5/31.
 */
public class SemaphoreTool {

    private void start() throws InterruptedException {
        Vector<Thread> ts = new Vector<Thread>();
        AtomicInteger atomicInteger = new AtomicInteger();

        //先释放6个线程
        final Semaphore semaphore = new Semaphore(6);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //线程运行，获取信号；
                    //当剩余信号为0时，则线程阻塞
                    semaphore.acquire();
                    System.out.println("aqurie ThreadId："+ Thread.currentThread().getId());
                    Thread.currentThread().sleep(5000);
                    if(atomicInteger.incrementAndGet() == 6|| atomicInteger.get() == 8){
                        System.out.println("after 5s ");
                        //释放2个信号资源，则阻塞的线程继续执行
                        semaphore.release(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for(int i = 0; i< 10;i++){
            Thread t = new Thread(runnable);
            ts.add(t);
            t.start();
        }

        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SemaphoreTool semaphoreTool = new SemaphoreTool();
        semaphoreTool.start();
    }
}
