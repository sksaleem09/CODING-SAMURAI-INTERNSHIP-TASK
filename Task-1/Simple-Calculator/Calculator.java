// Import Swing package for GUI components

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatorGUI extends JFrame implements ActionListener {

    // Text field to display numbers and results
    JTextField display;

    // Variables to store numbers and operator
    double num1 = 0, num2 = 0;
    String operator = "";

    // Constructor: runs when object is created
    public CalculatorGUI() {

        // Set title of window
        setTitle("Simple Calculator");

        // Set size of calculator window
        setSize(300, 400);

        // Close program when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout of frame
        setLayout(new BorderLayout());

        // Create display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false); // User cannot type directly
        display.setHorizontalAlignment(JTextField.RIGHT);

        // Add display at top of window
        add(display, BorderLayout.NORTH);

        // Create panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // Button labels
        String[] buttons = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", "C", "=", "/"
        };

        // Create and add buttons to panel
        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this); // Register button click
            panel.add(btn);
        }

        // Add panel to center
        add(panel, BorderLayout.CENTER);

        // Make window visible
        setVisible(true);
    }

    // This method runs when any button is clicked
    public void actionPerformed(ActionEvent e) {

        String text = e.getActionCommand();

        // If number button is clicked
        if (text.matches("[0-9]")) {
            display.setText(display.getText() + text);
        } // Clear button
        else if (text.equals("C")) {
            display.setText("");
            num1 = num2 = 0;
            operator = "";
        } // Equals button
        else if (text.equals("=")) {
            num2 = Double.parseDouble(display.getText());

            // Perform operation
            switch (operator) {
                case "+":
                    display.setText(String.valueOf(num1 + num2));
                    break;
                case "-":
                    display.setText(String.valueOf(num1 - num2));
                    break;
                case "*":
                    display.setText(String.valueOf(num1 * num2));
                    break;
                case "/":
                    if (num2 != 0) {
                        display.setText(String.valueOf(num1 / num2)); 
                    }else {
                        display.setText("Error");
                    }
                    break;
            }
        } // Operator buttons (+ - * /)
        else {
            operator = text;
            num1 = Double.parseDouble(display.getText());
            display.setText("");
        }
    }

    // Main method
    public static void main(String[] args) {
        new CalculatorGUI(); // Create calculator object
    }
}
