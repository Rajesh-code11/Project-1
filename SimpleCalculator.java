import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame {
    private JTextField display;
    private double result = 0;
    private String operator = "=";
    private boolean calculating = true;

    public SimpleCalculator() {
        setTitle("Simple Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 4, 4));
        String[] buttons = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", ".", "+"};

        for (String buttonLabel : buttons) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (('0' <= command.charAt(0) && command.charAt(0) <= '9') || command.equals(".")) {
                if (calculating) {
                    display.setText(command);
                } else {
                    display.setText(display.getText() + command);
                }
                calculating = false;
            } else {
                if (calculating) {
                    if (command.equals("-")) {
                        display.setText(command);
                        calculating = false;
                    } else if (command.equals("C")) {
                        clearAll();
                    } else {
                        operator = command;
                    }
                } else {
                    double x = Double.parseDouble(display.getText());
                    calculate(x);
                    operator = command;
                    calculating = true;
                }
            }
        }
    }

    private void calculate(double n) {
        switch (operator) {
            case "+":
                result += n;
                break;
            case "-":
                result -= n;
                break;
            case "*":
                result *= n;
                break;
            case "/":
                if (n != 0) {
                    result /= n;
                } else {
                    display.setText("Error");
                    result = 0;
                    operator = "=";
                    return;
                }
                break;
            case "=":
                result = n;
                break;
        }
        display.setText(String.valueOf(result));
    }

    private void clearAll() {
        result = 0;
        operator = "=";
        calculating = true;
        display.setText("0");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }
}