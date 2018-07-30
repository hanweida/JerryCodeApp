package lessons.threads.synctool.excutor;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ES-BF-IT-126 on 2018/6/4.
 */

interface CancellTask<T> extends Callable{
    void cancel();
    RunnableFuture newTask();
}

class Cancelling implements CancellTask{

    Socket socket;
    void setSocket(Socket socket){this.socket = socket;}
    @Override
    public void cancel() {
        try {
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RunnableFuture newTask() {
        return new FutureTask(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try{
                    Cancelling.this.cancel();
                } finally {
                    super.cancel(mayInterruptIfRunning);
                }
                return false;
            }

        };
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}

public class CancellingExecutor extends ThreadPoolExecutor {


    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if(callable instanceof CancellTask){
            return ((CancellTask) callable).newTask();
        } else {
            return super.newTaskFor(callable);
        }
    }
}
