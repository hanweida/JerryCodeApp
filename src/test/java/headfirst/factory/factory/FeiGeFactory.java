package headfirst.factory.factory;

public class FeiGeFactory extends BikeFactory {
    @Override
    Bike createBike() {
        return new FeiGeGongLuBike();
    }
}
