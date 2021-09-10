package headfirst.proxyfactory;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Proxy;

public class CarProxyFactory<T> {
    private Class<T> carClassInterface;
    CarProxyFactory(Class carClassInterface){
        this.carClassInterface = carClassInterface;
    }

    private T newInstance(CarProxy<T> carProxy){
       return (T)Proxy.newProxyInstance(carClassInterface.getClassLoader(), new Class[]{carClassInterface}, carProxy);
    }

    public T newInstance(){
        CarProxy<T> carProxy = new CarProxy<T>();
        return newInstance(carProxy);
    }
}
