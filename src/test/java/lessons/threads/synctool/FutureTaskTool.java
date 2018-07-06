package lessons.threads.synctool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask， 阻塞获得结果
 * Created by ES-BF-IT-126 on 2018/5/31.
 */
public class FutureTaskTool {


    private void startRunnable() throws ExecutionException, InterruptedException {
        final StringBuilder stringBuilder = new StringBuilder("start");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                stringBuilder.append("Run");

            }
        };
        FutureTask<StringBuilder> futureTask = new FutureTask<StringBuilder>(runnable, stringBuilder);
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("get before");
        System.out.println(futureTask.get());
        System.out.println("callable final");
    }

    private void startCallBack() throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return Thread.currentThread().getId()+"";
            }
        };
        FutureTask<String> futureTask = new FutureTask<String>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("get before");
        System.out.println(futureTask.get());
        System.out.println("callable final");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTaskTool futureTaskTool = new FutureTaskTool();
        futureTaskTool.startCallBack();
        futureTaskTool.startRunnable();
    }
}
