package headfirst.observer;

/**
 * 队员2，实现Observer接口，注册队伍消息主题，接收通知，并打印出来
 */
public class Player2 implements Observer {

    @Override
    public void receive(String message) {
        System.out.println("Player2收到了: "+message);
    }
}
