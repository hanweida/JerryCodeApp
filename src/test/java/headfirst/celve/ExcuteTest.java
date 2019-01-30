package headfirst.celve;

import org.junit.Test;

/**
 * description:策略模式
 * @author:Jerry 
 * @method: 
 * @date: 2019/1/30
 * @param:
 * @Returns:
 */
public class ExcuteTest {
    @Test
    public void test(){
        Game chiji = new ChiJi();
        Game shouWangXianFeng = new ShouWangXianFeng();
        chiji.kill();
        shouWangXianFeng.kill();
    }
}
