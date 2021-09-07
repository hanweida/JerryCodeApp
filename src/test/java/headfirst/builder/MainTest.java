package headfirst.builder;

/**
 * 建造者模式
 * Builder模式是将对象的构建和表示分离，好比构建一辆汽车，Builder是将部件和组装过程分离，从而达到高内聚低耦合的目的。
 */
public class MainTest {
    public static void main(String[] args) {
        CarConfig carConfig = new CarConfig.Bulder().setSeat("真皮座椅").build();
        System.out.println(carConfig.getSeat());

        CarConfig carConfig2 = new CarConfig.Bulder().build();
        System.out.println(carConfig2.getSeat());
    }
}
