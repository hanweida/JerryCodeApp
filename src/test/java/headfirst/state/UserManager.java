package headfirst.state;

import javax.xml.registry.infomodel.User;

public class UserManager {
    private static UserManager userManager = new UserManager();
    private UserManager(){}
    private UserState userState;

    public static UserManager getUserManager(){
        return userManager;
    }

    public void setUserState(UserState userState){
        this.userState = userState;
    }

    /**
     * 统一删除接口
     */
    public void delete(){
        userState.deleteCard();
    }

    /**
     * 统一加购物车接口
     */
    public void add(){
        userState.addCard();
    }
}
