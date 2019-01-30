package headfirst.celve;

public class ChiJi extends Game {
    @Override
    void kill() {
        setAction(new Shot());
        toKill();
    }
}
