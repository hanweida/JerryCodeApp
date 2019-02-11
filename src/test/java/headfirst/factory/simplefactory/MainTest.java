package headfirst.factory.simplefactory;

import org.junit.Test;

/**
 * 简单工厂，简单工厂更像是一种习惯，非真正意义的工厂模式，将对象的获得封装起来
 * 简单工厂的实现，通过一个Factory工厂，传递创建对象的参数，从而达到创建对象的目的
 */
public class MainTest {
    BikeFactory bikeFactory = new BikeFactory();

    @Test
    public void test(){
        Bike bike = bikeFactory.create("普通车");
        System.out.println("生产了 "+ bike.getName());

        Bike bike2 = bikeFactory.create("山地车");
        System.out.println("生产了 "+ bike2.getName());

        Bike bike3 = bikeFactory.create("公路赛");
        System.out.println("生产了 "+ bike3.getName());
    }
}
                                                                        