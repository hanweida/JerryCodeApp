package headfirst.factory.factory;

public class FengHuangFactory extends BikeFactory {
    @Override
    Bike createBike() {
        return new FengHuangGongLuBike();
    }
}
