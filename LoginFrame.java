package org.mavensample.ui;

import javax.swing.*;
import java.awt.*;

import org.mavensample.model.User;
import org.mavensample.service.AuthService;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();

        setTitle("Pharmacy Login");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔥 ANA PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // USERNAME
        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // PASSWORD
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // BUTTON
        
loginButton = new JButton("Login");
loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // EKLE
        panel.add(userLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(10));

        panel.add(passLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(loginButton);

panel.add(buttonPanel);

        add(panel);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = authService.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Login failed");
            return;
        }

        if (user.getRole().equalsIgnoreCase("admin")) {
            new AdminFrame(user);
        } else {
            new StaffFrame(user);
        }

        dispose();
    }
}