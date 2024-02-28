package ic.doc;

public interface UpdatableView {
    void updateResultField(UpdatingModel updatingModel);

    void updateError(Exception e);
}
