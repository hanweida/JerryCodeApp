package test;
import org.junit.Test;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptedTest {

  @Test
  public void test(){
    ReentrantLock reentrantLock = new ReentrantLock();
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("excute jdk.thread");
       // LockSupport.park();
        System.out.println("after jdk.thread");
      }
    });

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("excute thread2");
        LockSupport.park();
        System.out.println("thread2 aquire");
        System.out.println(Thread.interrupted());
      }
    });

    Thread thread3 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("excute thread3");
        //thread2.interrupt();
        LockSupport.unpark(thread2);

      }
    });

    try {
      thread.start();
      Thread.sleep(1000);
      thread2.start();
      Thread.sleep(1000);
      thread3.start();
      Thread.sleep(1000);
      thread.join();
      thread2.join();
      thread3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


  }


}
