package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangKy extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public DangKy() {
        this.setTitle("Đăng ký");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 30, 80, 25);
        this.add(usernameLabel);

        this.usernameField = new JTextField();
        this.usernameField.setBounds(100, 30, 160, 25);
        this.add(this.usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 70, 80, 25);
        this.add(passwordLabel);

        this.passwordField = new JPasswordField();
        this.passwordField.setBounds(100, 70, 160, 25);
        this.add(this.passwordField);

        // Register button
        this.registerButton = new JButton("Register");
        this.registerButton.setBounds(50, 110, 100, 25);
        this.add(this.registerButton);

        // Back button
        this.backButton = new JButton("Back");
        this.backButton.setBounds(160, 110, 80, 25);
        this.add(this.backButton);

        // Register button action listener
        this.registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = DangKy.this.usernameField.getText().trim();
                String password = String.valueOf(DangKy.this.passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Save the new user credentials
                    DangNhap.registerUser(username, password);
                    JOptionPane.showMessageDialog(null, "Registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Go back to the login screen after successful registration
                    DangNhap loginForm = new DangNhap();
                    loginForm.setVisible(true);
                    DangKy.this.dispose();
                }
            }
        });

        // Back button action listener
        this.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DangNhap loginForm = new DangNhap();
                loginForm.setVisible(true);
                DangKy.this.dispose();
            }
        });
    }

    public static void main(String[] args) {
        DangKy registerForm = new DangKy();
        registerForm.setVisible(true);
    }

	public static void registerUser(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
}
