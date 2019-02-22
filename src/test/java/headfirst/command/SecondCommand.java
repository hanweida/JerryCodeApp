package headfirst.command;

public class SecondCommand implements Command {
    IReceiver iReceiver;

    //采用单个类的形式
    Receiver receiver;
    public SecondCommand(Receiver receiver){
        this.receiver = receiver;
    }
    //采用单个类形式
    @Override
    public void excute() {
        receiver.doB();
    }
    
    /*
    //采用接收者接口形式
    public SecondCommand(IReceiver receiver){
        iReceiver = receiver;
    }

    @Override
    public void excute() {
        iReceiver.doB();
    }*/
}
