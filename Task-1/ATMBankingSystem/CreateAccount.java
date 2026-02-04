import javax.swing.*;
import java.awt.*;

public class CreateAccount extends JFrame {

    public CreateAccount() {
        ATMBase.setupFrame(this, "Create Account", 
            ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT_SMALL);

        ATMBase.ImagePanel panel = new ATMBase.ImagePanel("images/create_account.jpg");
        setContentPane(panel);

        panel.add(ATMBase.createLabel("Create New Account", 250, 40, 400, 35, 26, true));
        panel.add(ATMBase.createLabel("Full Name (optional):", 200, 140, 200, 25, 16, true));

        JTextField txtName = new JTextField();
        txtName.setBounds(400, 140, 200, 28);
        panel.add(txtName);

        panel.add(ATMBase.createLabel("Click 'Generate Account' to get your Account Number.", 
            200, 190, 450, 25, 14, false));

        JButton btnGenerate = ATMBase.createButton("Generate Account", 290, 250, 220, 40, 16);
        btnGenerate.addActionListener(e -> {
            String accNo = ATMBase.generateAccountNumber();
            Account account = new Account(accNo);
            ATMBase.accounts.add(account);

            ATMBase.showInfo(this,
                "Account Created Successfully!\n\nYour Account Number is: " + accNo +
                "\n\nNow set your 4-digit PIN.");

            ATMBase.switchFrame(this, new PinGeneration(account));
        });
        panel.add(btnGenerate);

        JButton btnBack = ATMBase.createButton("Back", 290, 310, 220, 40, 16);
        btnBack.addActionListener(e -> ATMBase.switchFrame(this, new Main()));
        panel.add(btnBack);
    }
}

