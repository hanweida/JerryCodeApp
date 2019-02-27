package headfirst.iterator;

import java.util.List;

public class NameIterator implements Iterator {
    List<String> stringList;
    private int index;

    public NameIterator(List<String> strings){
        this.stringList = strings;
    }


    @Override
    public boolean hasNext() {
        return stringList.size() > index;
    }

    @Override
    public Object next() {
        if(!hasNext()){
            throw new NullPointerException("没有了");
        }
        return stringList.get(index++);
    }
}
