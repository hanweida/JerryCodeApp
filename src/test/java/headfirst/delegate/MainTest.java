package headfirst.delegate;

import org.junit.Test;

/**
 * 委托模式、通过组合的模式，降低耦合度，委托模式是一种面向对象的设计模式，允许对象组合实现与继承相同的代码重用
 * 作用：为其他对象提供一种类似代理的方式以控制对这个对象的访问。
 */
public class MainTest {
    @Test
    public void test(){
        Tech tech = new WeiLaiTech();
        FushikangDelegate fushikangDelegate = new FushikangDelegate(tech);
        fushikangDelegate.makeCar();
    }
}
