

package UI;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DangKy extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public DangKy() {
        this.setTitle("Đăng ký");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout((LayoutManager)null);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 30, 80, 25);
        this.add(usernameLabel);
        this.usernameField = new JTextField();
        this.usernameField.setBounds(100, 30, 160, 25);
        this.add(this.usernameField);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 70, 80, 25);
        this.add(passwordLabel);
        this.passwordField = new JPasswordField();
        this.passwordField.setBounds(100, 70, 160, 25);
        this.add(this.passwordField);
        this.registerButton = new JButton("Register");
        this.registerButton.setBounds(50, 110, 100, 25);
        this.add(this.registerButton);
        this.backButton = new JButton("Back");
        this.backButton.setBounds(160, 110, 80, 25);
        this.add(this.backButton);
        this.registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = DangKy.this.usernameField.getText();
                String password = String.valueOf(DangKy.this.passwordField.getPassword());
                JOptionPane.showMessageDialog((Component)null, "Registered successfully!");
            }
        });
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
}
