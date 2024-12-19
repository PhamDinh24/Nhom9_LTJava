package UI;

import Repo.DangNhapRepo;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangKy extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

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
        registerButton.setBounds(50, 110, 200, 25);
        add(registerButton);

        // Xử lý sự kiện đăng ký
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                Long role = 0L;  // role 0: Chủ trọ, role 1: Khách hàng

                boolean isSuccess = DangNhapRepo.getInstance().DangKy(username, password, role);

                if (isSuccess) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
                    dispose();  // Đóng form đăng ký
                    DangNhap loginForm = new DangNhap();
                    loginForm.setVisible(true);  // Mở form đăng nhập
                } else {
                    JOptionPane.showMessageDialog(null, "Đăng ký thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        DangKy registerForm = new DangKy();
        registerForm.setVisible(true);
    }
}
