package thread.concurrent.lock.readwritelock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class ReentrantReadWriteLockDemo {
  int a = 0;
  @Test
  public void demo(){
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    Thread readWriteThread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println(Thread.currentThread().getName() +"：" + "invoke");
        readLock.lock();
        System.out.println(Thread.currentThread().getName() +"：" + "getreadlock");
        System.out.println(Thread.currentThread().getName() +"：" + "Sleep");
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + "：" + a);
        readLock.unlock();
      }
    });

    Thread readWriteThread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println(Thread.currentThread().getName() +"：" + "invoke");
        readLock.lock();
        System.out.println(Thread.currentThread().getName() +"：" + "getreadlock");
        System.out.println(Thread.currentThread().getName() +"：" + "Sleep");
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + "：" + a);
        readLock.unlock();
      }
    });

    Thread writeThread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println(Thread.currentThread().getName() +"：" + "invoke");
        writeLock.lock();
        System.out.println(Thread.currentThread().getName() +"：" + "getwritelock");
        System.out.println(Thread.currentThread().getName() +"：" + "Sleep");
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        a++;
        System.out.println(Thread.currentThread() + "：" + a);
        writeLock.unlock();
      }
    });
    readWriteThread.start();
    writeThread.start();
    readWriteThread2.start();
    try {
      readWriteThread.join();
      readWriteThread2.join();
      writeThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void demo2(){
    ReentrantLock reentrantLock = new ReentrantLock();
    reentrantLock.lock();
  }
}
