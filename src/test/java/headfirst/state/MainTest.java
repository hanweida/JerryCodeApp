package headfirst.state;

import org.junit.Test;

/**
 * 状态模式
 */
public class MainTest {
    @Test
    public void excute(){
        UserManager.getUserManager().setUserState(new LogoutState());
        UserManager.getUserManager().add();

        UserManager.getUserManager().setUserState(new LoginInState());
        UserManager.getUserManager().add();
    }
}
