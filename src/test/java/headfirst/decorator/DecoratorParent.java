package headfirst.decorator;

/**
 * 装饰器父类，子类实现此装饰器
 */
public class DecoratorParent extends SuperDecorator {
    //超类声明，用来实现组件，调用组件的方法
    SuperDecorator superDecorator;
    //子类传递组件对象
    DecoratorParent(SuperDecorator decorator){
        this.superDecorator = decorator;
    }
    @Override
    String doBike() {
        return superDecorator.doBike();
    }
}
