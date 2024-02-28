package ic.doc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReversePolishCalculator implements UpdatingModel {

    private final Stack<Integer> stack = new Stack<>();
    private final List<UpdatableView> observers = new ArrayList<>();

    private final Exception invalidArgumentException =
            new IllegalArgumentException("Invalid argument");

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void clear() {
        stack.clear();
        updateObservers();
    }

    @Override
    public int showResult() {
        if (stack.isEmpty()) {
            return 0;
        }
        return stack.peek();
    }

    public void push(Character character) {
        try {
            stack.push(Integer.parseInt(String.valueOf(character)));
        } catch (NumberFormatException e) {
            if (stack.size() < 2) {
                updateErrorInObservers(invalidArgumentException);
                return;
            }
            if (!evaluateExpression(character)) {
                return;
            }
        }
        updateObservers();
    }

    private boolean evaluateExpression(Character character) {
        if ("+-*/".indexOf(character) == -1) {
            updateErrorInObservers(invalidArgumentException);
            return false;
        }
        Integer secondOperand = stack.pop();
        Integer firstOperand = stack.pop();
        if (character == '+') {
            stack.push(firstOperand + secondOperand);
        } else if (character == '-') {
            stack.push(firstOperand - secondOperand);
        } else if (character == '*') {
            stack.push(firstOperand * secondOperand);
        } else if (character == '/') {
            stack.push(firstOperand / secondOperand);
        }
        return true;
    }

    public void addObserver(UpdatableView updatableView) {
        observers.add(updatableView);
    }

    private void updateObservers() {
        observers.forEach(updatableView -> updatableView.updateResultField(this));
    }

    private void updateErrorInObservers(Exception e) {
        observers.forEach(updatableView -> updatableView.updateError(e));
    }
}
