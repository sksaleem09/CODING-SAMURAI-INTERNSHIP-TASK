import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        ATMBase.setupFrame(this, "ATM Banking System - Welcome", 
            ATMBase.WINDOW_WIDTH, ATMBase.WINDOW_HEIGHT_SMALL);

        ATMBase.ImagePanel panel = new ATMBase.ImagePanel("images/welcome.jpg");
        setContentPane(panel);

        JLabel title = new JLabel("ATM Banking System");
        title.setForeground(ATMBase.TEXT_COLOR);
        title.setFont(new Font(ATMBase.FONT_NAME, Font.BOLD, 30));
        title.setBounds(240, 40, 400, 40);
        panel.add(title);

        JButton btnCreate = ATMBase.createButton("Create Account", 300, 180, 200, 40, 16);
        btnCreate.addActionListener(e -> ATMBase.switchFrame(this, new CreateAccount()));
        panel.add(btnCreate);

        JButton btnLogin = ATMBase.createButton("Login", 300, 240, 200, 40, 16);
        btnLogin.addActionListener(e -> ATMBase.switchFrame(this, new Login()));
        panel.add(btnLogin);

        JButton btnExit = ATMBase.createButton("Exit", 300, 300, 200, 40, 16);
        btnExit.addActionListener(e -> System.exit(0));
        panel.add(btnExit);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

