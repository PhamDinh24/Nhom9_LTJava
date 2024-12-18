package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangKy extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public DangKy() {
        setTitle("Đăng ký");
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

        registerButton = new JButton("Register");
        registerButton.setBounds(50, 110, 100, 25);
        add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(160, 110, 80, 25);
        add(backButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                // Thêm mã xử lý đăng ký tại đây
                JOptionPane.showMessageDialog(null, "Registered successfully!");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DangNhap loginForm = new DangNhap();
                loginForm.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        DangKy registerForm = new DangKy();
        registerForm.setVisible(true);
    }
}
