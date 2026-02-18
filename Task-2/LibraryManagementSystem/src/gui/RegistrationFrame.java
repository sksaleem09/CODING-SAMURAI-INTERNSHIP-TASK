package gui;

import dao.UserDAO;
import java.awt.*;
import javax.swing.*;

public class RegistrationFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JTextField emailField;
    private final JButton registerButton;
    private final JButton loginButton;
    private final UserDAO userDAO;

    public RegistrationFrame() {
        userDAO = new UserDAO();

        setTitle("Library Management System - Registration");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmLabel);
        panel.add(confirmPasswordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(registerButton);
        panel.add(loginButton);

        registerButton.addActionListener(e -> register());
        loginButton.addActionListener(e -> openLoginFrame());

        add(panel);
        setVisible(true);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Password Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.userExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Registration Failed",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.registerUser(username, password, email)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            usernameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            emailField.setText("");
            openLoginFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openLoginFrame() {
        @SuppressWarnings("unused")
        LoginFrame login = new LoginFrame();
        this.dispose();
    }
}
