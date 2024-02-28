package ic.doc;

public interface UpdatableView {
    void updateResultField(StackCalculator stackCalculator);

    void updateError(Exception e);
}
