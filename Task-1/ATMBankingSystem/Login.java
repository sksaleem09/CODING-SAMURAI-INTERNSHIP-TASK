import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField accountField;
    private JPasswordField pinField;

    public Login() {
        initializeComponents();
    }

    private void initializeComponents() {
        ATMBase.setupFrame(this, "ATM Login", ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT);

        ATMBase.ImagePanel mainPanel = new ATMBase.ImagePanel("images/login.jpg");
        mainPanel.setLayout(null);

        mainPanel.add(ATMBase.createLabel("Account Number:", 200, 200, 200, 30, 16, true));
        accountField = new JTextField();
        accountField.setFont(new Font(ATMBase.FONT_NAME, Font.PLAIN, 14));
        accountField.setBounds(400, 200, 200, 30);
        mainPanel.add(accountField);

        mainPanel.add(ATMBase.createLabel("PIN:", 200, 250, 200, 30, 16, true));
        pinField = new JPasswordField();
        pinField.setFont(new Font(ATMBase.FONT_NAME, Font.PLAIN, 14));
        pinField.setBounds(400, 250, 200, 30);
        mainPanel.add(pinField);

        JButton loginButton = ATMBase.createButton("Login", 300, 320, 150, 40, 16);
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton);

        JButton backButton = ATMBase.createButton("Back", 50, 500, 100, 35, 14);
        backButton.addActionListener(e -> {
            dispose();
            new Main().setVisible(true);
        });
        mainPanel.add(backButton);

        add(mainPanel);
    }

    private void handleLogin() {
        String accountNumber = accountField.getText().trim();
        String pin = new String(pinField.getPassword());

        if (accountNumber.isEmpty() || pin.isEmpty()) {
            ATMBase.showError(this, "Please fill in all fields!");
            return;
        }

        Account account = ATMBase.findAccount(accountNumber);
        if (account == null) {
            ATMBase.showError(this, "Account not found!");
            return;
        }

        if (!account.getPin().equals(pin)) {
            ATMBase.showError(this, "Invalid PIN!");
            pinField.setText("");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new Menu(account).setVisible(true);
    }
}
