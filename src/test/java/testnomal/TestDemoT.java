package testnomal;

import com.jerry.javase.fanxing.DemoT;
import org.junit.Test;

/**
 * Created by ES-BF-IT-126 on 2018/1/8.
 */
public class TestDemoT {
    @Test
    public void testDemoT(){
        DemoT<Integer> demo = new DemoT<>();
        demo.setInts(2);
    }

}
