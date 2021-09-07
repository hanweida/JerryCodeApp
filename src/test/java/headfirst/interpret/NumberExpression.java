package headfirst.interpret;

public class NumberExpression implements Expression{
    private long number;

    public NumberExpression(long number) {
        this.number = number;
    }

    @Override
    public long interpret() {
        return this.number;
    }
}
