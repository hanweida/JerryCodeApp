package jdk.lessons.threads.synctool.excutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ES-BF-IT-126 on 2018/6/1.
 */
public class ExecutorCompletionTool {

    class CallableWork implements Callable<String> {
        @Override
        public String call() throws Exception {
            if(12  == Thread.currentThread().getId()){
                System.out.println("invoke Slepp 12");
                Thread.currentThread().sleep(2000);
            }
            System.out.println("invoke " + Thread.currentThread().getId());
            return Thread.currentThread().getId()+"";
        }
    }

    public void start(){
        Executor executor = Executors.newFixedThreadPool(10);
        CompletionService completionService = new ExecutorCompletionService(executor);
        for(int i = 0; i<5;i++){
            completionService.submit(new CallableWork());
        }
        System.out.println("After Submit");
        for(int i = 0; i<5;i++){
            try {
                Future future = completionService.poll();
                System.out.println("Result："+future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorCompletionTool executorCompletionTool = new ExecutorCompletionTool();
        executorCompletionTool.start();
    }
}
