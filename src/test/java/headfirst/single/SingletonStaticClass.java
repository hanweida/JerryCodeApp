package headfirst.single;

// 采用内置静态类的方法创建
public class SingletonStaticClass {
    private SingletonStaticClass(){}
    public static SingletonStaticClass getInstance(){
        return SingletonStaticClassHandler.singletonStaticClass;
    }

    private static class SingletonStaticClassHandler{
        private static SingletonStaticClass singletonStaticClass = new SingletonStaticClass();
    }
}
