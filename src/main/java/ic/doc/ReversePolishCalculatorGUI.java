package ic.doc;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReversePolishCalculatorGui implements UpdatableView {
    private final JFrame frame;
    private final JTextField resultField;
    private final JTextField errorField;

    ReversePolishCalculatorGui(ActionListener operationController,
                               ActionListener clearController) {

        resultField = new JTextField(5);
        resultField.setEditable(false);
        errorField = new JTextField(20);
        errorField.setEditable(false);
        errorField.setBackground(Color.LIGHT_GRAY);
        errorField.setForeground(Color.RED);

        List<JButton> operationButtons = new ArrayList<>();
        String operations = "123456789+0-*/";
        for (String op : operations.split("")) {
            operationButtons.add(new JButton(op));
        }
        JButton clearButton = new JButton("C");

        operationButtons.forEach(b -> b.addActionListener(operationController));
        clearButton.addActionListener(clearController);

        JPanel panel = new JPanel();
        operationButtons.forEach(panel::add);
        panel.add(clearButton);
        panel.add(resultField);
        panel.add(errorField);

        frame = new JFrame("Reverse Polish Calculator");
        frame.setSize(250, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
    }

    public static void main(String[] args) {
        ReversePolishCalculator reversePolishCalculator = new ReversePolishCalculator();
        ActionListener operationController =
                e -> reversePolishCalculator.push(e.getActionCommand().charAt(0));
        ActionListener clearController = e -> reversePolishCalculator.clear();

        ReversePolishCalculatorGui calculatorGui =
                new ReversePolishCalculatorGui(operationController, clearController);
        reversePolishCalculator.addObserver(calculatorGui);
        calculatorGui.display();
    }

    private void display() {
        frame.setVisible(true);
    }

    @Override
    public void updateResultField(UpdatingModel updatingModel) {
        resultField.setText(String.valueOf(updatingModel.showResult()));
        errorField.setText("");
    }

    @Override
    public void updateError(Exception e) {
        errorField.setText(e.getMessage());
    }
}
