package headfirst.decorator;

/**
 * 装饰器实现
 * 例如：颜色装饰器，继承装饰器父类，给车包装颜色
 */
public class ColorDecorator extends DecoratorParent{

    /**
     * 构造函数，传入将需要装饰的类
     * @param decorator
     */
    ColorDecorator(SuperDecorator decorator) {
        super(decorator);
    }
    /**
     * 这里实现父类doBike方法，因为可能有其他装饰器会 装饰此类
     */
    @Override
    String doBike(){
        String str = superDecorator.doBike();
        return str + ",给自行车喷漆";
    }
}
