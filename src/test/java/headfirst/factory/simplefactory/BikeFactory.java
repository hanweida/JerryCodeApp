package headfirst.factory.simplefactory;

/**
 * 简单工厂：简单工厂通过传入的参数，返回需要的对象
 */
public class BikeFactory {
    public Bike create(String type){
        if("山地车".equals(type)){
            return new ShanDiBike();
        } else if("公路赛".equals(type)){
            return new GongLuBike();
        } else {
            return new Bike();
        }
    }
}
