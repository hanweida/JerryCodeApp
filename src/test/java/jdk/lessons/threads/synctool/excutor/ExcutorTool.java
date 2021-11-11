package jdk.lessons.threads.synctool.excutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ES-BF-IT-126 on 2018/5/31.
 */
public class ExcutorTool {

    static class Worker implements Runnable{
        @Override
        public void run() {
            System.out.println("Runnable Run：" + Thread.currentThread().getId());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void start(){
        Executor executor = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 20; i++){
            executor.execute(new Worker());
        }
    }
    public static void main(String[] args) {
        ExcutorTool excutorTool = new ExcutorTool();
        excutorTool.start();
    }
}
