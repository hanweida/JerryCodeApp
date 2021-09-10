package headfirst.proxyfactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CarProxy <T> implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            System.out.println("invoke");
            //// 诸如hashCode()、toString()、equals()等方法，将target指向当前对象thisreturn method.invoke(this, args);
            //target，在执行Object.java内的方法时，target被指向了this，target已经变成了傀儡、象征、占位符。在投鞭断流式的拦截时，已经没有了target。
            return method.invoke(this, args);
        }
        System.out.println("new TSCar");
        return new TSCar("red");
    }
}
