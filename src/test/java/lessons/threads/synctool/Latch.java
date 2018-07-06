package lessons.threads.synctool;

import java.util.concurrent.CountDownLatch;

/**
 * 同步工具类-闭锁CountDownLatch
 * Created by ES-BF-IT-126 on 2018/5/31.
 */
public class Latch {

    private static CountDownLatch downLatchStart = new CountDownLatch(1);
    private CountDownLatch downLatchEnd = null;

    private void start(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程：" + Thread.currentThread().getId()+"等待");
                try {
                    downLatchStart.await();
                } catch (InterruptedException e) {
                    System.out.println("捕获异常");
                    e.printStackTrace();
                }
                System.out.println("Run：" + Thread.currentThread().getId());
                downLatchEnd.countDown();
            }
        };

        for(int i = 0; i < 10; i++){
               new Thread(runnable).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Latch latch = new Latch();
        latch.downLatchEnd = new CountDownLatch(10);
        System.out.println("latch start");
        latch.start();
        Thread.sleep(3000);
        System.out.println("开始");
        downLatchStart.countDown();
        System.out.println("等待线程");
        latch.downLatchEnd.await();
        System.out.println("总线程结束");
    }
}
