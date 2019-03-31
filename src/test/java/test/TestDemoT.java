package test;

import com.jerry.javase.fanxing.DemoT;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public class TestDemoT {
    private static int i =0;

    public static void main(String[] args) {
        Random random = new Random();

        System.out.println(random.nextInt(900));
    }


    @Test
    public void test(){
        if( 1 == ++i || 2 == ++i){
            System.out.println(i);
        }
    }

    @Test
    public void test2(){
        StringBuilder a= null;
        //System.out.println(a.append("d"));
        Cell b = null;
        System.out.println(b.cas(1, 1));
    }

    static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return true;
        }
    }
}
