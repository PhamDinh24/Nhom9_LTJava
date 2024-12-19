package UI;

import javax.swing.*;
import database.JDBCUtil;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DongTienDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> cbMaKhach;
    private JComboBox<String> cbMaPhong;  // Thêm ComboBox cho Mã Phòng
    private JTextField txtSoTien;
    private JButton btnDongY, btnHuy;

    public DongTienDialog(JFrame parent) {
        super(parent, "Đóng Tiền", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));  // Cập nhật số hàng trong GridLayout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Mã Khách:"));
        cbMaKhach = new JComboBox<>();
        loadMaKhach();
        panel.add(cbMaKhach);

        panel.add(new JLabel("Mã Phòng:"));
        cbMaPhong = new JComboBox<>();  // Khởi tạo ComboBox cho Mã Phòng
        loadMaPhong();
        panel.add(cbMaPhong);

        panel.add(new JLabel("Số Tiền:"));
        txtSoTien = new JTextField();
        panel.add(txtSoTien);

        btnDongY = new JButton("Đồng Ý");
        btnHuy = new JButton("Hủy");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnDongY);
        buttonPanel.add(btnHuy);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnDongY.addActionListener(e -> handleDongTien());
        btnHuy.addActionListener(e -> dispose());
    }

    private void loadMaKhach() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT maKhach FROM KhachTro";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> maKhachList = new ArrayList<>();
            while (resultSet.next()) {
                maKhachList.add(resultSet.getString("maKhach"));
            }
            for (String maKhach : maKhachList) {
                cbMaKhach.addItem(maKhach);
            }

            resultSet.close();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã khách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
    }

    // Phương thức tải danh sách Mã Phòng từ cơ sở dữ liệu
    private void loadMaPhong() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT maPhong FROM PhongTro WHERE trangThai = 'Trống'"; // Chỉ lấy các phòng trống
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> maPhongList = new ArrayList<>();
            while (resultSet.next()) {
                maPhongList.add(resultSet.getString("maPhong"));
            }
            for (String maPhong : maPhongList) {
                cbMaPhong.addItem(maPhong);
            }

            resultSet.close();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã phòng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
    }

    private void handleDongTien() {
        String maKhach = (String) cbMaKhach.getSelectedItem();
        String maPhong = (String) cbMaPhong.getSelectedItem();
        String soTienStr = txtSoTien.getText().trim();

        if (maKhach == null || maKhach.isEmpty() || maPhong == null || maPhong.isEmpty() || soTienStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double soTien;
        try {
            soTien = Double.parseDouble(soTienStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số tiền phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "INSERT INTO DongTien (maKhach, maPhong, soTien, ngayDong) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maKhach);  // Lưu mã khách
            statement.setString(2, maPhong);  // Lưu mã phòng
            statement.setDouble(3, soTien);   // Lưu số tiền

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Đóng tiền thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể đóng tiền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi đóng tiền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
    }
}
