import javax.swing.*;
import java.awt.*;

public class Deposit extends JFrame {
    private Account currentAccount;
    private JTextField amountField;

    public Deposit(Account account) {
        this.currentAccount = account;
        initializeComponents();
    }

    private void initializeComponents() {
        ATMBase.setupFrame(this, "Deposit Money", ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT);

        ATMBase.ImagePanel mainPanel = new ATMBase.ImagePanel("images/deposit.jpg");
        mainPanel.setLayout(null);

        mainPanel.add(ATMBase.createLabel("Deposit Money", 300, 100, 300, 40, 24, true));

        JLabel balanceLabel = new JLabel("Current Balance: ₹" + currentAccount.getBalance());
        balanceLabel.setFont(new Font(ATMBase.FONT_NAME, Font.BOLD, 16));
        balanceLabel.setForeground(Color.YELLOW);
        balanceLabel.setBounds(250, 160, 300, 30);
        mainPanel.add(balanceLabel);

        mainPanel.add(ATMBase.createLabel("Enter Amount to Deposit:", 200, 250, 250, 30, 16, true));
        amountField = new JTextField();
        amountField.setFont(new Font(ATMBase.FONT_NAME, Font.PLAIN, 14));
        amountField.setBounds(450, 250, 200, 30);
        mainPanel.add(amountField);

        JButton depositButton = ATMBase.createButton("Deposit", 300, 330, 200, 40, 16);
        depositButton.addActionListener(e -> handleDeposit());
        mainPanel.add(depositButton);

        JButton backButton = ATMBase.createButton("Back to Menu", 300, 400, 200, 40, 14);
        backButton.addActionListener(e -> {
            dispose();
            new Menu(currentAccount).setVisible(true);
        });
        mainPanel.add(backButton);

        add(mainPanel);
    }

    private void handleDeposit() {
        String amountText = amountField.getText().trim();

        if (amountText.isEmpty()) {
            ATMBase.showError(this, "Please enter an amount!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (!ATMBase.isValidAmount(amount)) {
                ATMBase.showError(this, "Amount must be greater than zero!");
                return;
            }

            if (JOptionPane.showConfirmDialog(this, "Deposit ₹" + amount + "?", 
                "Confirm Deposit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                currentAccount.deposit(amount);
                JOptionPane.showMessageDialog(this, 
                    "Deposit successful!\nAmount deposited: ₹" + amount + 
                    "\nNew balance: ₹" + currentAccount.getBalance(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");
                dispose();
                new Deposit(currentAccount).setVisible(true);
            }
        } catch (NumberFormatException e) {
            ATMBase.showError(this, "Invalid amount! Please enter a valid number.");
        }
    }
}
