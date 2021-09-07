package headfirst.decorator.decorator2;

public class CacheComponent implements Cache{

    @Override
    public String cache() {
        return "需要装饰的组件";
    }
}
