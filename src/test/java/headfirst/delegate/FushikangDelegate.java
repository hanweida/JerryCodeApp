package headfirst.delegate;

public class FushikangDelegate implements Tech{

    Tech tech;
    public FushikangDelegate() {
    }

    public FushikangDelegate(Tech tech) {
        this.tech = tech;
    }

    @Override
    public void makeCar() {
        tech.makeCar();
    }
}
