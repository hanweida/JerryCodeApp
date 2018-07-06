package lessons.interrupt.interrupt;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ES-BF-IT-126 on 2018/6/1.
 */
public class InterruptWork {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<BigInteger> bigIntegers = new ArrayBlockingQueue<BigInteger>(100);
        Producer thread = new Producer(bigIntegers);
        thread.start();
        Thread.sleep(1000);
        thread.cancel();
//        for(int i = 0; i<5;i++){
//
//            if(i == 4){
//
//            }
//        }
    }
}

class Producer extends Thread{
    private BlockingQueue<BigInteger> queue;

    Producer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger bigInteger = BigInteger.ONE;
            try {
                while (!Thread.currentThread().isInterrupted()){
                    queue.put(bigInteger = bigInteger.nextProbablePrime());
                    System.out.println("queue");
                }
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                e.printStackTrace();
            }
    }

    public void cancel(){
        System.out.println("cancel");
        interrupt();
    }
}
