package test;

import org.apache.dubbo.common.Constants;
import org.springframework.boot.test.context.SpringBootTest;

public class Test {

    @org.junit.jupiter.api.Test
    public void test(){
//        String[] addresses = Constants.REGISTRY_SPLIT_PATTERN.split("aa_aa");
//        System.out.println(addresses.length);
//        System.out.println(addresses[0]);
//        System.out.println(addresses[1]);
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(1);
        System.out.println(threadLocal.get());
    }
}
