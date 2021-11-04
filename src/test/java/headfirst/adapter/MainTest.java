package headfirst.adapter;

import headfirst.adapter.classAdapter.Class220vTo5vAdapter;
import headfirst.adapter.interfaceAdapter.Adapter5V;
import headfirst.adapter.interfaceAdapter.InterfaceAdapter;
import headfirst.adapter.pojo.Source220V;
import headfirst.adapter.pojo.Target5V;
import headfirst.adapter.symbolAdapter.Symbol220vTo5vAdapter;
import org.junit.Test;

/**
 * 适配器模式
 * 1.类适配器
 * 2.对象适配
 * 3.接口适配
 *
 * 3种适配方式，1,2种 类似
 */
public class MainTest {
    /**
     * 类适配模式
     */
    @Test
    public void classAdapterTest(){
        Target5V target5V = new Class220vTo5vAdapter();
        System.out.println(target5V.input());
    }

    /**
     * 对象适配模式
     */
    @Test
    public void symbolAdapterTest(){
        Target5V target5V = new Symbol220vTo5vAdapter(new Source220V());
        System.out.println(target5V.input());
    }

    /**
     * 接口适配器模式
     */
    @Test
    public void interfaceAdapterTest(){
        //实现子类
        //Adapter5V adapter5V = new Adapter5V(new Source220V());
        //System.out.println(adapter5V.output5V());

        //直接实现子类
        InterfaceAdapter adapter5V1 = new InterfaceAdapter(new Source220V()){
            @Override
            public int output5V() {
                return source220V.outPut() / 44;
            }
        };
        System.out.println(adapter5V1.output5V());
    }

}
