package headfirst.adapter.symbolAdapter;

import headfirst.adapter.pojo.Source220V;
import headfirst.adapter.pojo.Target5V;

/**
 * 对象转换器
 */
public class Symbol220vTo5vAdapter implements Target5V {

    Source220V source220V;

    public Symbol220vTo5vAdapter(Source220V source220V){
        this.source220V = source220V;
    }


    @Override
    public int input() {
        return source220V.outPut()/44;
    }
}
