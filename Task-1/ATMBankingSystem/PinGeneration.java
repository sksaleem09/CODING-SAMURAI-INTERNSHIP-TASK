import javax.swing.*;
import java.awt.*;

public class PinGeneration extends JFrame {
    private Account account;

    public PinGeneration(Account account) {
        this.account = account;
        initializeComponents();
    }

    private void initializeComponents() {
        ATMBase.setupFrame(this, "PIN Generation", 
            ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT_SMALL);

        ATMBase.ImagePanel panel = new ATMBase.ImagePanel("images/pin.jpg");
        setContentPane(panel);

        panel.add(ATMBase.createLabel("Set Your 4-Digit PIN", 250, 40, 400, 35, 26, true));

        JLabel lblAcc = new JLabel("Account Number: " + account.getAccountNumber());
        lblAcc.setForeground(ATMBase.TEXT_COLOR);
        lblAcc.setFont(new Font(ATMBase.FONT_NAME, Font.BOLD, 16));
        lblAcc.setBounds(250, 110, 350, 25);
        panel.add(lblAcc);

        panel.add(ATMBase.createLabel("Enter PIN:", 250, 170, 120, 25, 16, true));
        JPasswordField txtPin = new JPasswordField();
        txtPin.setBounds(380, 170, 170, 28);
        panel.add(txtPin);

        panel.add(ATMBase.createLabel("Confirm PIN:", 250, 220, 120, 25, 16, true));
        JPasswordField txtConfirm = new JPasswordField();
        txtConfirm.setBounds(380, 220, 170, 28);
        panel.add(txtConfirm);

        JButton btnSet = ATMBase.createButton("Set PIN", 290, 290, 220, 40, 16);
        btnSet.addActionListener(e -> {
            String pin = new String(txtPin.getPassword()).trim();
            String confirm = new String(txtConfirm.getPassword()).trim();

            if (!ATMBase.isValidPin(pin)) {
                ATMBase.showError(this, "PIN must be exactly 4 digits.");
                return;
            }
            if (!pin.equals(confirm)) {
                ATMBase.showError(this, "PIN and Confirm PIN do not match.");
                return;
            }

            account.setPin(pin);
            ATMBase.showInfo(this, "PIN set successfully!\n\nNow login using your Account Number and PIN.");
            ATMBase.switchFrame(this, new Login());
        });
        panel.add(btnSet);

        JButton btnCancel = ATMBase.createButton("Cancel", 290, 345, 220, 40, 16);
        btnCancel.addActionListener(e -> ATMBase.switchFrame(this, new Main()));
        panel.add(btnCancel);
    }
}

