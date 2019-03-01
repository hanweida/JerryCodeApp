package headfirst.proxy.cglibproxy;

//被代理类：（演员）
public class ActorCglib {
    public void talk() {
        System.out.println("代理人谈判");
    }

    public void order() {
        System.out.println("代理人谈判");
    }

    public void film() {
        System.out.println("演员亲自完成 本人出演");
    }

    public void checkout() {
        System.out.println("代理人谈判");
    }
}
