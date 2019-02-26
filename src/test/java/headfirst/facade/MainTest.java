package headfirst.facade;

import headfirst.facade.component.Computer;
import org.junit.Test;

/**
 * 外观模式
 * 外观模式是对外提供的一个接口。外层不需要了解系统内如何实现
 * 外部接口不需要关系子系统内部如何实现，解耦
 * 缺点就是违反开闭原则，会修改原有代码
 */
public class MainTest {
    @Test
    public void test(){
        //Computer是子系统，提供一个方法 接口，供外部使用
        Computer computer = new Computer();
        computer.openCompument();
        computer.closeDisplay();
    }
}
