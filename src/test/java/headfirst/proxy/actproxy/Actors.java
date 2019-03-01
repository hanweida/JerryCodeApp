package headfirst.proxy.actproxy;


public class Actors implements Subjects {
    @Override
    public void talk() {
    }

    @Override
    public void order() {
    }

    @Override
    public void film() {
        System.out.println("演员亲自完成 本人出演");
    }

    @Override
    public void checkout() {
    }
}
