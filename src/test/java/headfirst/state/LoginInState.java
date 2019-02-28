package headfirst.state;


public class LoginInState implements UserState {
    @Override
    public void deleteCard() {
        System.out.println("删除购物车成功");
    }

    @Override
    public void addCard() {
        System.out.println("添加购物车成功");
    }
}
