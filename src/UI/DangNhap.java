package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DangNhap extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public DangNhap() {
        setTitle("Đăng nhập");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 30, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 30, 160, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 70, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 70, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 110, 80, 25);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 110, 100, 25);
        add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                // Thêm mã xác thực đăng nhập tại đây
                JOptionPane.showMessageDialog(null, "Logged in successfully!");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DangKy registerForm = new DangKy();
                registerForm.setVisible(true);
                dispose();
            }
        });
    }
    public static void main(String[] args) {
        DangNhap loginForm = new DangNhap();
        loginForm.setVisible(true);
    }
}