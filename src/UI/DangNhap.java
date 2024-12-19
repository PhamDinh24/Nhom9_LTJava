package UI;

import Main.Main;  // Ensure you import the Main class
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhap extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public DangNhap() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Đăng Nhập");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblUsername = new JLabel("Tên Đăng Nhập:");
        txtUsername = new JTextField(20);
        JLabel lblPassword = new JLabel("Mật khẩu:");
        txtPassword = new JPasswordField(20);
        
        btnLogin = new JButton("Đăng Nhập");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin(txtUsername.getText(), new String(txtPassword.getPassword()))) {
                    openMainForm();
                } else {
                    JOptionPane.showMessageDialog(DangNhap.this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);

        add(panel);
    }

    private boolean validateLogin(String username, String password) {
        // Simple validation for admin login credentials
        return username.equals("admin") && password.equals("password");
    }

    private void openMainForm() {
        SwingUtilities.invokeLater(() -> {
            Main mainForm = new Main();
            mainForm.setVisible(true);  // Show Main form after login
            dispose();  // Close the login form
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DangNhap loginForm = new DangNhap();
            loginForm.setVisible(true);  // Show login form when the application starts
        });
    }
}
