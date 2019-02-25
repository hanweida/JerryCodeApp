package headfirst.adapter.interfaceAdapter;

import headfirst.adapter.pojo.DCOutPutV;
import headfirst.adapter.pojo.Source220V;

/**
 * 接口适配器
 */
public abstract class InterfaceAdapter implements DCOutPutV{

    public Source220V source220V;
    public InterfaceAdapter(Source220V source220V){
        this.source220V = source220V;
    }

    @Override
    public int output5V() {
        return source220V.outPut();
    }

    @Override
    public int output10V() {
        return source220V.outPut();
    }

    @Override
    public int output50V() {
        return source220V.outPut();
    }
}
