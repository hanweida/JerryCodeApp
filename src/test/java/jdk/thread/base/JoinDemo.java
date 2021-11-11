package jdk.thread.base;

import org.junit.Test;

public class JoinDemo {

    /**
     * Thread.join
     * 父线程调用子线程的对象 subThread.join()，需要等子线程执行完成，才会执行
     * 下面的例子：启动10个线程，当主线程中执行遇到 创建的线程thread.join()时，就会等待创建线程执行完，才会继续执行
     */
    @Test
    public void demo(){
        Thread thread =null;
        for(int i = 0; i < 10; i++){
            thread  = new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                thread.start();
                Thread.sleep(100);
                System.out.println("等待线程执行完毕");
                thread.join();
                System.out.println("主线程执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("主线程执行完毕");
        }
    }
}
