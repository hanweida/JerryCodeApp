package headfirst.observer;

/**
 * 观察者模式中的主题；
 * 观察者会注册主题，主题将消息推送给观察者
 */
public interface Observerble {
    /**
     * 注册观察者，通知消息给注册的观察者
     */
    void registObserver(Observer observer);
    /**
     * 移除观察者，观察者解除主题通知
     */
    void removeObserver(Observer observer);

    /**
     * 通知消息给观察者
     */
    void notifyObserver(String message);
}
