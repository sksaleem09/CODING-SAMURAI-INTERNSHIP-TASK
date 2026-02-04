import javax.swing.*;
import java.awt.*;

public class Withdraw extends JFrame {
    private Account currentAccount;
    private JTextField amountField;

    public Withdraw(Account account) {
        this.currentAccount = account;
        initializeComponents();
    }

    private void initializeComponents() {
        ATMBase.setupFrame(this, "Withdraw Money", ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT);

        ATMBase.ImagePanel mainPanel = new ATMBase.ImagePanel("images/withdraw.jpg");
        mainPanel.setLayout(null);

        mainPanel.add(ATMBase.createLabel("Withdraw Money", 300, 100, 300, 40, 24, true));

        JLabel balanceLabel = new JLabel("Current Balance: ₹" + currentAccount.getBalance());
        balanceLabel.setFont(new Font(ATMBase.FONT_NAME, Font.BOLD, 16));
        balanceLabel.setForeground(Color.YELLOW);
        balanceLabel.setBounds(250, 160, 300, 30);
        mainPanel.add(balanceLabel);

        mainPanel.add(ATMBase.createLabel("Enter Amount to Withdraw:", 200, 250, 250, 30, 16, true));
        amountField = new JTextField();
        amountField.setFont(new Font(ATMBase.FONT_NAME, Font.PLAIN, 14));
        amountField.setBounds(450, 250, 200, 30);
        mainPanel.add(amountField);

        JButton withdrawButton = ATMBase.createButton("Withdraw", 300, 330, 200, 40, 16);
        withdrawButton.addActionListener(e -> handleWithdraw());
        mainPanel.add(withdrawButton);

        JButton backButton = ATMBase.createButton("Back to Menu", 300, 400, 200, 40, 14);
        backButton.addActionListener(e -> {
            dispose();
            new Menu(currentAccount).setVisible(true);
        });
        mainPanel.add(backButton);

        add(mainPanel);
    }

    private void handleWithdraw() {
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

            if (amount > currentAccount.getBalance()) {
                ATMBase.showError(this, 
                    "Insufficient balance!\nAvailable balance: ₹" + currentAccount.getBalance() + 
                    "\nRequested amount: ₹" + amount);
                return;
            }

            if (JOptionPane.showConfirmDialog(this, "Withdraw ₹" + amount + "?", 
                "Confirm Withdrawal", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (currentAccount.withdraw(amount)) {
                    JOptionPane.showMessageDialog(this, 
                        "Withdrawal successful!\nAmount withdrawn: ₹" + amount + 
                        "\nRemaining balance: ₹" + currentAccount.getBalance(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    amountField.setText("");
                    dispose();
                    new Withdraw(currentAccount).setVisible(true);
                } else {
                    ATMBase.showError(this, "Insufficient balance!");
                }
            }
        } catch (NumberFormatException e) {
            ATMBase.showError(this, "Invalid amount! Please enter a valid number.");
        }
    }
}
