package headfirst.observer;

import org.junit.Test;

/**
 * description:观察者模式
 */
public class MainTest {
    @Test
    public void test(){
        //成立队伍了
        TeamMessageSub teamMessageSub = new TeamMessageSub();
        //来了3个球员
        Player1 player1 = new Player1();
        Player2 player2 = new Player2();
        Player3 player3 = new Player3();
        //经过队伍一顿忽悠，入队注册群了
        teamMessageSub.registObserver(player1);
        teamMessageSub.registObserver(player2);
        teamMessageSub.registObserver(player3);
        //队伍发消息了
        teamMessageSub.setMessage("收100会费了！");
        //第三个立马就不干了，要求退出
        teamMessageSub.removeObserver(player3);
        teamMessageSub.setMessage("收100会费了！");
    }
}
