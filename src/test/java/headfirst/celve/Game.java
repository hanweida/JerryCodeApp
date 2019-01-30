package headfirst.celve;

public abstract class Game {
    abstract void kill();
    Action action;
    void setAction(Action action){
        this.action = action;
    }
    void toKill(){
        action.useWepan();
    }
}
