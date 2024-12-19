package UI;

import javax.swing.*;
import database.JDBCUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class TimKiemDialog extends JDialog {
    private JTextField txtSearch;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public TimKiemDialog(JFrame parent) {
        super(parent, "Tìm Kiếm Mã Khách", true);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(getParent());

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel lblSearch = new JLabel("Nhập Mã Khách:");
        txtSearch = new JTextField();
        JButton btnSearch = new JButton("Tìm Kiếm");

        btnSearch.addActionListener(this::handleSearch);

        inputPanel.add(lblSearch, BorderLayout.WEST);
        inputPanel.add(txtSearch, BorderLayout.CENTER);
        inputPanel.add(btnSearch, BorderLayout.EAST);

        // Initialize table model
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "Mã Khách", "Họ Tên", "Ngày Sinh", "Giới Tính", "Quê Quán", "Số Điện Thoại", "Email"
        });
        
        // Initialize JTable
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleSearch(ActionEvent e) {
        String maKhach = txtSearch.getText().trim();
        if (maKhach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM KhachTro WHERE maKhach = ?")) {

            statement.setLong(1, Long.parseLong(maKhach));
            ResultSet resultSet = statement.executeQuery();

            // Clear previous search results
            tableModel.setRowCount(0);

            if (resultSet.next()) {
                // Add search result to table
                Object[] rowData = new Object[]{
                    resultSet.getLong("maKhach"),
                    resultSet.getString("hoTen"),
                    resultSet.getDate("ngaySinh"),
                    resultSet.getString("gioiTinh"),
                    resultSet.getString("queQuan"),
                    resultSet.getString("soDienThoai"),
                    resultSet.getString("gmail")
                };
                tableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách trọ với mã này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Mã khách phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
