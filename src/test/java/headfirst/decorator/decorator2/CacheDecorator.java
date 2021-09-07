package headfirst.decorator.decorator2;

public class CacheDecorator implements Cache{

    Cache cache;
    public CacheDecorator(Cache cache) {
        this.cache = cache;
    }

    @Override
    public String cache() {
        String str = cache.cache();
        return str + ",装饰完了";
    }
}
