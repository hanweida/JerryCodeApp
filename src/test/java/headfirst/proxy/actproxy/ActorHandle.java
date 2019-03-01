package headfirst.proxy.actproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 演员代理类
 */
public class ActorHandle implements InvocationHandler {
    private Subjects subject;
    public ActorHandle(Subjects subject){
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("film")){
            proxy = method.invoke(subject, args);
        } else {
            System.out.println("代理都负责了");
        }
        return proxy;
    }
}
