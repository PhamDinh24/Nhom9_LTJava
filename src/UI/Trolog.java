package UI;

import javax.swing.*;
import database.JDBCUtil;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Trolog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> cbMaKhach;
    private JComboBox<String> cbMaPhong;
    private JTextField txtSoTien;
    private JButton btnDongY, btnHuy;

    public Trolog(JFrame parent) {
        super(parent, "Nhập Trọ", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));  // Added one extra row for SoTien field
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Mã Khách:"));
        cbMaKhach = new JComboBox<>();
        loadMaKhach();
        panel.add(cbMaKhach);

        panel.add(new JLabel("Mã Phòng:"));
        cbMaPhong = new JComboBox<>();
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

        btnHuy.addActionListener(e -> dispose());
        btnDongY.addActionListener(e -> handleDongY());
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

    private void loadMaPhong() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT maPhong FROM PhongTro WHERE trangThai = 'Trống'";
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

    private void handleDongY() {
        String maKhach = (String) cbMaKhach.getSelectedItem();
        String maPhong = (String) cbMaPhong.getSelectedItem();
        String soTien = txtSoTien.getText();

        // Kiểm tra xem thông tin đã đầy đủ chưa
        if (maKhach == null || maPhong == null || soTien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Thêm vào bảng DongTien
            String insertQuery = "INSERT INTO DongTien (maKhach, maPhong, soTien, ngayDong) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, maKhach); // maKhach là kiểu String
            statement.setString(2, maPhong); // maPhong là kiểu String
            statement.setDouble(3, Double.parseDouble(soTien));  // SoTien là kiểu double

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Cập nhật bảng PhongTro để đánh dấu phòng là "Đã thuê"
                String updateQuery = "UPDATE PhongTro SET trangThai = 'Đã thuê', maKhach = ? WHERE maPhong = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, maKhach);  // maKhach là kiểu String
                updateStatement.setString(2, maPhong);  // maPhong là kiểu String

                updateStatement.executeUpdate();
                updateStatement.close();

                // Cam kết giao dịch
                connection.commit();
                JOptionPane.showMessageDialog(this, "Đã nhập trọ thành công!");
                dispose();  // Đóng hộp thoại khi thành công
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thực hiện giao dịch!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            statement.close();
        } catch (Exception ex) {
            try {
                connection.rollback();  // Hoàn tác giao dịch khi có lỗi
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xử lý: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connection.setAutoCommit(true);  // Khôi phục lại chế độ tự động commit
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JDBCUtil.closeConnection(connection);
        }
    }
}
