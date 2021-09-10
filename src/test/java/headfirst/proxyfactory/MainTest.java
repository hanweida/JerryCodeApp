package headfirst.proxyfactory;

/**
 * 代理工厂组合
 */
public class MainTest {
    public static void main(String[] args) {

        CarProxyFactory carProxyFactory = new CarProxyFactory(Car.class);
        Car car = (Car)carProxyFactory.newInstance();
        car.toString();
        TSCar tsCar = car.getCar();
        System.out.println(tsCar.getColor());
//        car.driver();
//        car.end();
//        car.park();
    }
}
