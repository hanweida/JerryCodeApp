package headfirst.proxy.staticProxy;

public class Agent implements Subject {

    Subject subject;

    public Agent(Subject subject){
        this.subject = subject;
    }

    @Override
    public void talk() {
        System.out.println("代理人完成");
    }

    @Override
    public void order() {
        System.out.println("代理人完成");
    }

    @Override
    public void film() {
        subject.film();
    }

    @Override
    public void checkout() {
        System.out.println("代理人完成");
    }
}
