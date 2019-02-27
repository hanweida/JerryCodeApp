package headfirst.iterator;

import org.junit.Test;

/**
 * 迭代器模式，访问聚合内容无需知道内部结构。分离了集合对象遍历行为，抽象出一个迭代器类来负责
 * 既不暴露集合内部结构，又让外部代码透明的访问内部数据
 */
public class MainTest {
    @Test
    public void test(){
        NameRepository nameRepository = new NameRepository();
        for(int i = 0; i < 10; i++){
            nameRepository.add("位置: " +i);
        }

        Iterator iterator = nameRepository.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
