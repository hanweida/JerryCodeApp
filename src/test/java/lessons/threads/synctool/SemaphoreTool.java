package lessons.threads.synctool;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 * Created by ES-BF-IT-126 on 2018/5/31.
 */
public class SemaphoreTool {

    private void start(){
        final Semaphore semaphore = new Semaphore(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("aqurie before：" + Thread.currentThread().getId());
                    boolean success = semaphore.tryAcquire();
                    System.out.println("aqurie success："+ success + Thread.currentThread().getId());
                    Thread.currentThread().sleep(3000);
                    System.out.println("aqurie release：" + Thread.currentThread().getId());
                    semaphore.release(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for(int i = 0; i< 20;i++){
            new Thread(runnable).start();
        }
    }


    public static void main(String[] args) {
        SemaphoreTool semaphoreTool = new SemaphoreTool();
        semaphoreTool.start();
    }
}
