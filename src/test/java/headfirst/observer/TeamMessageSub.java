package headfirst.observer;

import java.util.ArrayList;

/**
 * 例如：队伍信息主题，队员注册此主题，当有消息时，会通知注册队伍中的队员
 */
public class TeamMessageSub implements Observerble{
    //观察者队列
    ArrayList<Observer> observers;
    /**
     * 观察者注册 消息通知
     * @param observer
     */
    @Override
    public void registObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * 观察者解除 消息通知
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        int index = observers.indexOf(observer);
        if(index > 0){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserver(String message) {
        for(Observer observer : observers){
            observer.receive(message);
        }
    }

    /**
     * 主题设置消息后，通知给各个已经注册的观察者
     * @param message
     */
    public void setMessage(String message){
        notifyObserver(message);
    }

    TeamMessageSub(){
        observers = new ArrayList<>();
    }
}
