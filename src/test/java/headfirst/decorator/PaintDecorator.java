package headfirst.decorator;

public class PaintDecorator extends DecoratorParent{

    PaintDecorator(SuperDecorator decorator) {
        super(decorator);
    }

    @Override
    String doBike() {
        String str = super.doBike();
        return str += "，给自行车画了个图";
    }
}
