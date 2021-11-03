package test.abstractcountmultichild;

public class Test {
    public static void main(String[] args) {
        Child1 child1 = new Child1();
        Child2 child2 = new Child2();

        child1.setCount(child1.getCount() + 1);
        child2.setCount(child2.getCount() + 1);
        System.out.println(child1.getCount());
        System.out.println(child2.getCount());
    }
}
