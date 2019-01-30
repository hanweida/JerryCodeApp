package headfirst.celve;

public class ShouWangXianFeng extends Game{
    @Override
    void kill() {
        setAction(new Fly());
        toKill();
    }
}
