package UI;

import Repo.DangNhapRepo;
import Main.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhap extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public DangNhap() {
        // Frame settings
        setTitle("Đăng Nhập");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // GridBagConstraints to manage component placement
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Username label and text field
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblUsername, gbc);

        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtUsername, gbc);

        // Password label and text field
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblPassword, gbc);

        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtPassword, gbc);

        // Login button
        btnLogin = new JButton("Đăng Nhập");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnLogin, gbc);

        // Add action listener to the login button
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    // Handle Login button click event
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Hardcoded credentials
        String correctUsername = "admin";
        String correctPassword = "1234";

        // Check if the entered credentials match the hardcoded values
        if (username.equals(correctUsername) && password.equals(correctPassword)) {
            // Successful login
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

            // Open the main window after successful login
            openMainWindow();

            // Dispose of the login window to prevent the user from coming back to it
            this.dispose();
        } else {
            // Show error message if login fails
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);

            // Optionally, clear the password field after a failed login attempt
            txtPassword.setText("");
        }
    }

    // Method to open the main window after successful login
    private void openMainWindow() {
        SwingUtilities.invokeLater(() -> {
            // Create and display the Main window
            Main mainForm = new Main();
            mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainForm.setSize(1000, 600);  // Customize the size as needed
            mainForm.setLocationRelativeTo(null);
            mainForm.setTitle("Main Window - Chu Tro");
            mainForm.setVisible(true);
        });
    }

    // Main method to launch the login form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize and display the login window
            DangNhap loginForm = new DangNhap();
            loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginForm.setSize(400, 300);
            loginForm.setLocationRelativeTo(null);
            loginForm.setTitle("Đăng Nhập");
            loginForm.setVisible(true);
        });
    }
}
