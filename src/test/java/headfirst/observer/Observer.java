package headfirst.observer;

/**
 * 观察者接口，观察者实现此接口，主题通知消息时，执行update方法
 */
public interface Observer {
    /**
     * 主题通知观察者，执行修改操作
     */
    void receive(String message);
}
