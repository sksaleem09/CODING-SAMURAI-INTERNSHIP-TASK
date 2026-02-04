import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private Account currentAccount;
    private static final int[] FAST_CASH_AMOUNTS = {50, 100, 200, 500, 1000};

    public Menu(Account account) {
        this.currentAccount = account;
        initializeComponents();
    }

    private void initializeComponents() {
        ATMBase.setupFrame(this, "ATM Main Menu", ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT);

        ATMBase.ImagePanel mainPanel = new ATMBase.ImagePanel("images/menu.jpg");
        mainPanel.setLayout(null);

        mainPanel.add(ATMBase.createLabel("Welcome! Choose an option:", 250, 50, 400, 40, 20, true));

        JButton balanceButton = ATMBase.createButton("Balance Enquiry", 300, 150, 200, 40, 14);
        balanceButton.addActionListener(e -> {
            dispose();
            new Balance(currentAccount).setVisible(true);
        });
        mainPanel.add(balanceButton);

        JButton depositButton = ATMBase.createButton("Deposit", 300, 210, 200, 40, 14);
        depositButton.addActionListener(e -> {
            dispose();
            new Deposit(currentAccount).setVisible(true);
        });
        mainPanel.add(depositButton);

        JButton withdrawButton = ATMBase.createButton("Withdraw", 300, 270, 200, 40, 14);
        withdrawButton.addActionListener(e -> {
            dispose();
            new Withdraw(currentAccount).setVisible(true);
        });
        mainPanel.add(withdrawButton);

        mainPanel.add(ATMBase.createLabel("Fast Cash:", 300, 340, 200, 30, 16, true));

        int startX = 200;
        int yPos = 380;
        int buttonWidth = 80;
        int spacing = 20;
        for (int i = 0; i < FAST_CASH_AMOUNTS.length; i++) {
            int amount = FAST_CASH_AMOUNTS[i];
            JButton fastCashButton = new JButton("₹" + amount);
            fastCashButton.setFont(new Font(ATMBase.FONT_NAME, Font.BOLD, 12));
            fastCashButton.setBounds(startX + i * (buttonWidth + spacing), yPos, buttonWidth, 35);
            fastCashButton.addActionListener(e -> handleFastCash(amount));
            mainPanel.add(fastCashButton);
        }

        JButton logoutButton = ATMBase.createButton("Logout", 300, 450, 200, 40, 14);
        logoutButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", 
                "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
                new Main().setVisible(true);
            }
        });
        mainPanel.add(logoutButton);

        add(mainPanel);
    }

    private void handleFastCash(int amount) {
        if (currentAccount.getBalance() < amount) {
            ATMBase.showError(this, "Insufficient balance! Available: ₹" + currentAccount.getBalance());
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Withdraw ₹" + amount + "?", 
            "Fast Cash", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            currentAccount.setBalance(currentAccount.getBalance() - amount);
            JOptionPane.showMessageDialog(this, 
                "Transaction successful!\nAmount withdrawn: ₹" + amount + 
                "\nRemaining balance: ₹" + currentAccount.getBalance(), 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
