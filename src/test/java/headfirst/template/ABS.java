package headfirst.template;

public abstract class ABS {
    abstract void cal();

    void getResult(){
        System.out.println("获得计算结果中");
        this.cal();
        System.out.println("获得结果");
    }
}
