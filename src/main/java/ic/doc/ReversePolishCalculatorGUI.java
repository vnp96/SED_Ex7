package ic.doc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReversePolishCalculatorGUI implements UpdatableView {
    private final JFrame frame;
    private final JTextField resultField;
    private final JTextField errorField;

    ReversePolishCalculatorGUI(ActionListener operationController,
                               ActionListener clearController) {

        resultField = new JTextField(5);
        resultField.setEditable(false);
        errorField = new JTextField(30);
        errorField.setEditable(false);
        errorField.setBackground(Color.LIGHT_GRAY);
        errorField.setForeground(Color.RED);

        List<JButton> operationButtons = new ArrayList<>();
        String operations = "1234567890+-*/";
        for (String op : operations.split(""))
            operationButtons.add(new JButton(op));
        JButton clearButton = new JButton("C");

        operationButtons.forEach(b -> b.addActionListener(operationController));
        clearButton.addActionListener(clearController);

        JPanel panel = new JPanel();
        operationButtons.forEach(panel::add);
        panel.add(clearButton);
        panel.add(resultField);
        panel.add(errorField);

        frame = new JFrame("Reverse Polish Calculator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
    }

    public static void main(String[] args) {
        StackCalculator stackCalculator = new StackCalculator();
        ActionListener operationController =
                e -> stackCalculator.push(e.getActionCommand().charAt(0));
        ActionListener clearController = e -> stackCalculator.clear();

        ReversePolishCalculatorGUI calculatorGUI =
                new ReversePolishCalculatorGUI(operationController, clearController);
        stackCalculator.addObserver(calculatorGUI);
        calculatorGUI.display();
    }

    private void display() {
        frame.setVisible(true);
    }

    @Override
    public void updateResultField(StackCalculator stackCalculator) {
        resultField.setText(String.valueOf(stackCalculator.top()));
        errorField.setText("");
    }

    @Override
    public void updateError(Exception e) {
        errorField.setText(e.getMessage());
    }
}
