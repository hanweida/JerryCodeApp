package jdk.lessons.threads.synctool.excutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ES-BF-IT-126 on 2018/6/1.
 */
public class ExecutorServiceTool {

    class CallableWork implements Callable<String>{
        @Override
        public String call() throws Exception {
            if(15  == Thread.currentThread().getId()){
                System.out.println("invoke Slepp 15");
                Thread.currentThread().sleep(5000);
            }
            System.out.println("invoke " + Thread.currentThread().getId());
            return Thread.currentThread().getId()+"";
        }
    }

    public void startInvokeAll(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> callableList = new ArrayList<Callable<String>>();
        for(int i =0; i<6; i++){
            callableList.add(new CallableWork());
        }
        try {
            //通过invokeAll，所有CallBack 都在阻塞，等待15线程完成
            List<Future<String>> futureList = executorService.invokeAll(callableList);
            System.out.println("Futrue");
            for (Future<String> future : futureList){
                future.cancel(true);
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ExecutorServiceTool executorServiceTool = new ExecutorServiceTool();
        executorServiceTool.startInvokeAll();
//        final ExecutorService executorService = Executors.newFixedThreadPool(3);
//        for(int i = 0; i< 6; i++){
//            Future<String> future = null;
//            future = executorService.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    System.out.println("Run executorService");
//                    Thread.currentThread().sleep(1000);
//                    System.out.println("After Sleep");
//                    if(12 == Thread.currentThread().getId()){
//
//                    }
//                    return "" + Thread.currentThread().getId();
//                }
//            });
//            try {
//                System.out.println(future.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            executorService.shutdown();
//        }
    }
}
//