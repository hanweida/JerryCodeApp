package headfirst.single;

// 双检，懒汉加载
public class SingletonDouble {
    private static SingletonDouble singletonDouble;
    private SingletonDouble() {};

    public static SingletonDouble singletonDoubleInstance(){
        if(singletonDouble == null){
            try {
                Thread.sleep(300);
                synchronized (SingletonDouble.class){
                    if(singletonDouble == null){
                        singletonDouble = new SingletonDouble();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return singletonDouble;
    }
}
