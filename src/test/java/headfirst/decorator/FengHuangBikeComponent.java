package headfirst.decorator;

/**
 * 例如：此类为 凤凰自行车厂；相当于装饰器模式中的组件
 */
public class FengHuangBikeComponent extends SuperDecorator{
    /**
     * 子类组件实现超类中的方法
     */
    @Override
    String doBike() {
        return "生产了一辆凤凰牌自行车";
    }
}
