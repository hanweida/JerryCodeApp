package jdk.lessons.interrupt.interrupt;

import java.util.concurrent.BlockingQueue;

/**
 * 恢复中断策略示例
 * Created by ES-BF-IT-126 on 2018/6/4.
 */
public class InterruptWork2 {
    BlockingQueue<String> blockingQueue;
    public void interrupt(){
        class a implements Runnable{

            @Override
            public void run() {

            }
        }
        boolean interrupted = false;
        try {
            blockingQueue.take();
        } catch (InterruptedException e) {
            interrupted = true;
            //再次执行方法
        } finally {
            if(interrupted){
                //通过中断方法，中断当前线程，使不断执行 catch内的方法
                Thread.currentThread().interrupt();
            }
        }
    }
}
