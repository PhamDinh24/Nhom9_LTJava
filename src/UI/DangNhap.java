package UI;

import Main.Main;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DangNhap extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    // Simulated database for storing user credentials
    private static Map<String, String> userDatabase = new HashMap<>();

    public DangNhap() {
        // Frame settings
        setTitle("Đăng Nhập");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Khoảng cách giữa các thành phần

        // Username label and text field
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblUsername, gbc);

        txtUsername = new JTextField(15);
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

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtPassword, gbc);

        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); 
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        // Add action listeners
        btnLogin.addActionListener(e -> handleLogin());
        btnRegister.addActionListener(e -> openRegisterForm());

        // Initialize user database with a default account
        userDatabase.put("admin", "1234");
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            openMainWindow();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    private void openMainWindow() {
        SwingUtilities.invokeLater(() -> {
            Main mainForm = new Main();
            mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainForm.setSize(1000, 600);
            mainForm.setLocationRelativeTo(null);
            mainForm.setTitle("Main Window - Chu Tro");
            mainForm.setVisible(true);
        });
    }

    private void openRegisterForm() {
        SwingUtilities.invokeLater(() -> {
            DangKy registerForm = new DangKy();
            registerForm.setVisible(true);
            this.dispose();
        });
    }

    public static void registerUser(String username, String password) {
        userDatabase.put(username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DangNhap loginForm = new DangNhap();
            loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginForm.setVisible(true);
        });
    }
}
