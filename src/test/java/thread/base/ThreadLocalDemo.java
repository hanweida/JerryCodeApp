package thread.base;

import org.junit.Test;

public class ThreadLocalDemo {
    @Test
    public void testThreadLocalBase(){
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(1);
    }
}
