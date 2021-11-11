package jdk.thread.base;
import org.junit.Test;

public class ThreadStop {
//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Future<String> future = executorService.submit(new Call());
//        try {
//            String result = future.get(3, TimeUnit.SECONDS);
//            System.out.println(result);
//        } catch (InterruptedException e) {
//            System.out.println("InterruptedException");
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            System.out.println("ExecutionException");
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            System.out.println("TimeoutException");
//            e.printStackTrace();
//        }
//        System.out.println("final");
//        executorService.shutdownNow();
//    }
//    static class Call implements Callable<String>{
//
//        @Override
//        public String call() throws Exception {
//            Thread.sleep(4000);
//            return "ok";
//        }
//    }


    @Test
    public void test(){
        Thread thread = new Thread(new InterrupteRunnable());
        thread.start();
        System.out.println("final");
        while (!thread.isInterrupted()){
            System.out.println("pre"+thread.isInterrupted());
            try {
                Thread.sleep(1000);
                thread.interrupt();
                System.out.println("after isInterrupted"+thread.isInterrupted());
                System.out.println("after isInterrupted 2"+thread.isInterrupted());
                System.out.println("after"+thread.interrupted());
                System.out.println("after"+thread.interrupted());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class InterrupteRunnable implements Runnable{

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                //System.out.println(Thread.interrupted());
                System.out.println("a");
            }
        }
    }
}
