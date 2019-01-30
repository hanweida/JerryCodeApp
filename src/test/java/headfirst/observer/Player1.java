package headfirst.observer;

/**
 * 队员1，实现Observer接口，注册队伍消息主题，接收通知，并打印出来
 */
public class Player1 implements Observer{

    @Override
    public void receive(String message) {
        System.out.println("Player1收到了: "+message);
    }
}
