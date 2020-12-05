package test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Objects;

public class TestSPI {
    @Test
    public void test(){
        HashMap<A, Integer> map = new HashMap<A, Integer>(3);
        A a1 = new A(1);
        A a2 = new A(2);
        A a3 = new A(3);
        A a4 = new A(4);
        A a5 = new A(5);
        A a6 = new A(6);
        A a7 = new A(7);
        A a8 = new A(8);
        A a9 = new A(9);
        map.put(a1, 1);
        map.put(a2, 2);
        map.put(a3, 3);
        map.put(a4, 4);
        map.put(a5, 5);
        map.put(a6, 6);
        map.put(a7, 7);
        map.put(a8, 8);
        map.put(a9, 9);

    }
}

class A{
    private int a;

    A(int  a){
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        A a1 = (A) o;
        return a == a1.a;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}