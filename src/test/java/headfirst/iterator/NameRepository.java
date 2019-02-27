package headfirst.iterator;

import java.util.ArrayList;
import java.util.List;

public class NameRepository implements Container {

    List<String> strings = null;

    public NameRepository(){
        strings = new ArrayList<>();
    }

    void add(String str){
        strings.add(str);
    }

    @Override
    public Iterator getIterator() {
        return new NameIterator(strings);
    }
}
