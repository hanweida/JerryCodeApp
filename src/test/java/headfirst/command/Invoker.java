package headfirst.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    List<Command> commandList = new ArrayList<>();
    public void addCommand(Command command){
        commandList.add(command);
    }

    public void excuteCommand(){
        for(Command command : commandList){
            command.excute();
        }
    }
}
