package headfirst.state;

/**
 * 状态接口, 用状态来控制行为，不同的状态，对应不同的行为
 */
public interface UserState {
    void deleteCard();
    void addCard();
}
