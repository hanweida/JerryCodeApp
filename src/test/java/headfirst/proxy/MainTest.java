package headfirst.proxy;

import headfirst.proxy.actproxy.ActorHandle;
import headfirst.proxy.actproxy.Actors;
import headfirst.proxy.actproxy.Subjects;
import headfirst.proxy.cglibproxy.ActorCglib;
import headfirst.proxy.cglibproxy.ProxyFactory;
import headfirst.proxy.staticProxy.Actor;
import headfirst.proxy.staticProxy.Agent;
import headfirst.proxy.staticProxy.Subject;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class MainTest {
    @Test
    public void test(){
        //静态代理
//        Agent agent = new Agent(new Actor());
//        agent.talk();
//        agent.order();
//        agent.film();
//        agent.checkout();
//        //动态代理
//        ActorHandle actorHandle = new ActorHandle(new Actors());
//       Subjects subject = (Subjects) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Subjects.class}, actorHandle);
//        subject.talk();
//        subject.order();
//        subject.film();
//        subject.checkout();
//        //cglib代理
        ActorCglib actorCglib = new ActorCglib();
        //代理对象
        ActorCglib proxy = (ActorCglib) new ProxyFactory().getProxyInstance(actorCglib);
        proxy.talk();
        proxy.order();
        proxy.film();
        proxy.checkout();
    }
}
