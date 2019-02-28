package headfirst.state;

public class LogoutState implements UserState {
    @Override
    public void deleteCard() {
        System.out.println("删除购物车失败，重新登录");
    }

    @Override
    public void addCard() {
        System.out.println("添加购物车失败，重新登录");
    }
}
