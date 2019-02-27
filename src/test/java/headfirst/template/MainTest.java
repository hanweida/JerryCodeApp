package headfirst.template;

import org.junit.Test;

/**
 * 模板模式
 * 模板模式
 */
public class MainTest {
    @Test
    public void test(){
        ABS calculator = new CalByCalculator();
        calculator.getResult();

        ABS computer = new CalByComputer();
        computer.getResult();
    }
}
