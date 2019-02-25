package headfirst.adapter.classAdapter;

import headfirst.adapter.pojo.Source220V;
import headfirst.adapter.pojo.Target5V;

/**
 * 例如手机充电头，需要从220V电压 -》 5V 的电压，供手机充电
 * 类适配器， 继承 适配源类，也就是 220V类， 实现 目标类接口，也就是 5v 类
 */
public class Class220vTo5vAdapter extends Source220V implements Target5V {

    @Override
    public int input() {
        return outPut() / 44;
    }
}
