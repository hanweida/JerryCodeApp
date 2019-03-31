package lessons.threads.synctool;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 读写锁
 */
public class ReadWriteLockTest {

    @Test
    public void test(){
        //默认构造方法是 非公平锁
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

        System.out.println((1 << 16) -1);

        System.out.println(2 & 65534);
    }


}
