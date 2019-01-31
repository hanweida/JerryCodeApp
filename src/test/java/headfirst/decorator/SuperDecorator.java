package headfirst.decorator;

/**
 * 超类，组件和装饰器都继承超类，超类中的方法，子类实现
 */

public abstract class SuperDecorator {

    /**
     * 超类方法，由子类去实现
     */
    abstract String doBike();
}
