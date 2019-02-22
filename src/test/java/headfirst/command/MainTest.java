package headfirst.command;

import org.junit.Test;

/**
 * 命令模式
 */
public class MainTest {
    @Test
    public void test(){
        Command command1 = new FirstCommand(new Receiver());
        Command command2 = new SecondCommand(new Receiver());

        Invoker invoker = new Invoker();
        invoker.addCommand(command1);
        invoker.addCommand(command2);

        invoker.excuteCommand();
    }
}
