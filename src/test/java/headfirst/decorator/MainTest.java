package headfirst.decorator;

import org.junit.Test;

    /**
     * 装饰器模式(Decorator)
     * 装饰器模式 有一个超类abstract类型，组件和装饰器都继承超类，子类都继承实现超类方法
     * 装饰器内有超类多态声明，包装调用组件的 实现超类方法
     * 装饰器子类继承装饰器，套装组件方法
     */
    public class MainTest {
        @Test
        public void test(){
            SuperDecorator superDecorator = new PaintDecorator(new ColorDecorator(new FengHuangBikeComponent()));
            System.out.println(superDecorator.doBike());
        }
    }
