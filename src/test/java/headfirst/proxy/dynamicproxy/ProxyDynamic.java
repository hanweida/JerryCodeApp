package headfirst.proxy.dynamicproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDynamic implements InvocationHandler {

    //目标代理类
    Object target;

    //生成目标代理类接口，比如 (Person)
    public Object getInstance(Object target){
        //传入 目标代理 实例
        this.target = target;
        Class<?> clazz = target.getClass();
        //生成 目标代理类 接口（如果返回 不是目标代理类的接口，会报错， 这也就是说，目标代理类必须要实现 一个接口的意义）
        //而 目标代理类 可以有好多个接口，目标代理类实现了 不同接口的方法
        //那么 getInstance() 返回的就是 这些个接口中的一个， 你想调用哪个接口的方法，就强转成哪个接口
   /*     比如 实现了 Person 接口的sayHello 方法，那么我就可以 强转成 Person 接口
        当调用的时候 通过
        Person person = (Person) new ProxyDynamic().getInstance(new Son());
        person.sayHello();
        结果就是 son 的 sayHello 实现方法和 invoke 方法的 增强方法
        */

        /*     比如还实现了 Father 接口的 da() 方法，那么我就可以 强转成 Father 接口
        当调用的时候 通过
        Father father = (Father) new ProxyDynamic().getInstance(new Son());
        father.da();
        结果就是 son 的 da 实现方法和 invoke 方法的 增强方法
        */
        
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.target, args);
        after();
        return obj;
    }

    public void before(){
        System.out.println("骑自行车");
    }

    public void after(){
        System.out.printf("回家");
    }
}
