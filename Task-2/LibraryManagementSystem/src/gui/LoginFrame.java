package gui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton backButton;
    private final UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();

        setTitle("Library Management System - Login");
        setSize(300, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);

        loginButton.addActionListener(e -> login());
        backButton.addActionListener(e -> goBack());

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not used - lambdas handle events directly
    }

    private void goBack() {
        @SuppressWarnings("unused")
        RegistrationFrame reg = new RegistrationFrame();
        this.dispose();
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.loginUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            @SuppressWarnings("unused")
            LibraryDashboard dash = new LibraryDashboard();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}
