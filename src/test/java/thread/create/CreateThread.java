package thread.create;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程三种方式
 * Thread
 * Runnable
 * Callback或者Future
 */
public class CreateThread {

    class MyThread implements Callable {

        public Object call() throws Exception {
            System.out.println("excute:" + Thread.currentThread().getName());
            return Thread.currentThread().getName()+"name";
        }
    }

    @Test
    public void createCallable(){
        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask).start();

        try {
           System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}   
