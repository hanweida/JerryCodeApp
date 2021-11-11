package jdk.lessons.threads.synctool.excutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by ES-BF-IT-126 on 2018/6/4.
 */
public class Excutor3 {
    class Work implements Runnable{
        @Override
        public void run() {
            System.out.println("Runnable");
        }
    }

    public void runWork(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future future = executorService.submit(new Work());
        try {
            future.get(1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            //如果在任务中发生异常，则抛出一个异常
            //throw aException();
        } catch (TimeoutException e) {
            //如果任务返回超时，则处理超时异常
            e.printStackTrace();
        } finally {
            //如果任务已经结束，则无影响
            //如果任务有异常，则取消当前任务
            future.cancel(true);
        }
    }

    public static void main(String[] args) {
        Excutor3 excutor3 = new Excutor3();
        excutor3.runWork();
    }
}
