package headfirst.adapter.interfaceAdapter;

import headfirst.adapter.pojo.Source220V;

public class Adapter5V extends InterfaceAdapter {

    public Adapter5V(Source220V source220V) {
        super(source220V);
    }

    @Override
    public int output5V() {
        return source220V.outPut() / 44;
    }
}
