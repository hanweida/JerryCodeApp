package headfirst.proxy.dynamicproxy;

public class Test {
    public static void main(String[] args) {
        Person person = (Person) new ProxyDynamic().getInstance(new Son());
        person.sayHello();
    }
}
