package headfirst.command;

/**
 * 其实不用接口形式也行，单类的接受者去实现方法也可以
 */
public interface IReceiver {

    void doA();
    void doB();
}
