import javax.swing.*;
import java.awt.*;

public class Balance extends JFrame {
    private Account currentAccount;

    public Balance(Account account) {
        this.currentAccount = account;
        initializeComponents();
    }

    private void initializeComponents() {
        ATMBase.setupFrame(this, "Balance Enquiry", ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT);

        ATMBase.ImagePanel mainPanel = new ATMBase.ImagePanel("images/balance.jpg");
        mainPanel.setLayout(null);

        mainPanel.add(ATMBase.createLabel("Balance Enquiry", 300, 100, 300, 40, 24, true));
        mainPanel.add(ATMBase.createLabel("Account Number:", 250, 200, 200, 30, 16, true));

        JLabel accountValue = new JLabel(currentAccount.getAccountNumber());
        accountValue.setFont(new Font(ATMBase.FONT_NAME, Font.PLAIN, 16));
        accountValue.setForeground(Color.YELLOW);
        accountValue.setBounds(450, 200, 200, 30);
        mainPanel.add(accountValue);

        mainPanel.add(ATMBase.createLabel("Current Balance:", 250, 280, 200, 30, 18, true));

        JLabel balanceValue = new JLabel("₹" + currentAccount.getBalance());
        balanceValue.setFont(new Font(ATMBase.FONT_NAME, Font.BOLD, 24));
        balanceValue.setForeground(Color.GREEN);
        balanceValue.setBounds(450, 275, 200, 40);
        mainPanel.add(balanceValue);

        JButton backButton = ATMBase.createButton("Back to Menu", 300, 400, 200, 40, 14);
        backButton.addActionListener(e -> {
            dispose();
            new Menu(currentAccount).setVisible(true);
        });
        mainPanel.add(backButton);

        add(mainPanel);
    }
}
