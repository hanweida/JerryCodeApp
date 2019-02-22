package headfirst.command;

public class FirstCommand implements Command  {

    //采用接口形式
    IReceiver iReceiver;

    //采用单个类的形式
    Receiver receiver;

    public FirstCommand(Receiver receiver){
        this.receiver = receiver;
    }
    //采用单个类形式
    @Override
    public void excute() {
        receiver.doA();
    }


    /*
    //采用接收者接口形式
    public FirstCommand(IReceiver receiver){
        iReceiver = receiver;
    }

    @Override
    public void excute() {
        iReceiver.doA();
    }*/
}
