package headfirst.proxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {
    //需要被代理的对象
    private Object target;

    /**
     * 返回真实对象
     * @return
     */
    public Object getProxyInstance(Object target){
        this.target = target;
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(target.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj =  methodProxy.invokeSuper(o, objects);
        after();
        return obj;
    }

    public void before(){
        System.out.println("before 增强");
    }

    public void after(){
        System.out.println("after 增强");
    }
}
