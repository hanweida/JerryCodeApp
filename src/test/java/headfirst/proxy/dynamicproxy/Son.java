package headfirst.proxy.dynamicproxy;

public class Son implements Person,Father{
    @Override
    public void sayHello() {
        System.out.println("投篮");
    }

    @Override
    public void da() {
        System.out.println("被打了");
    }
}
