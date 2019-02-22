package headfirst.command;

/**
 * 接收者也可以用做命令模式
 */
public class Receiver {

    public void doA(){
        System.out.println("doA");
    }

    public void doB(){
        System.out.println("doB");
    }
}
