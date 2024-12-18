package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.JDBCUtil;

public class PhongTrolog extends JDialog {
    private JTextField txtMaPhong, txtTenPhong, txtDienTich, txtGiaThue, txtMaKhach;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbTrangThai;

    public PhongTrolog(JFrame parent) {
        super(parent, "Danh Sách Phòng Trọ", true);
        setSize(1000, 600);
        setLocationRelativeTo(parent);
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Danh sách các phòng trọ", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Center Panel with Form and Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createTitledBorder("Thông Tin Phòng Trọ:")
        ));

        formPanel.add(new JLabel("Mã Phòng:"));
        txtMaPhong = new JTextField();
        formPanel.add(txtMaPhong);

        formPanel.add(new JLabel("Tên Phòng:"));
        txtTenPhong = new JTextField();
        formPanel.add(txtTenPhong);

        formPanel.add(new JLabel("Diện Tích:"));
        txtDienTich = new JTextField();
        formPanel.add(txtDienTich);

        formPanel.add(new JLabel("Giá Thuê:"));
        txtGiaThue = new JTextField();
        formPanel.add(txtGiaThue);

        formPanel.add(new JLabel("Trạng Thái:"));
        cbTrangThai = new JComboBox<>(new String[]{"Trống", "Đã thuê"});
        formPanel.add(cbTrangThai);

        formPanel.add(new JLabel("Mã Khách:"));
        txtMaKhach = new JTextField();
        formPanel.add(txtMaKhach);

        centerPanel.add(formPanel, BorderLayout.NORTH);

        // Table Panel
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Phòng", "Tên Phòng", "Diện Tích", "Giá Thuê", "Trạng Thái", "Mã Khách"});
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Buttons Panel (Add, Update, Delete)
        JPanel buttonPanel = new JPanel();
        String[] buttonLabels = {"Thêm mới", "Cập nhật", "Xóa"};

        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            btn.addActionListener(e -> {
                System.out.println("Bạn đã nhấn nút: " + label);
                switch (label) {
                    case "Thêm mới":
                        handleThemMoi();
                        break;
                    case "Cập nhật":
                        handleCapNhat();
                        break;
                    case "Xóa":
                        handleXoa();
                        break;
                    default:
                        System.out.println("Nút không xác định!");
                }
            });

            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        // Table row click event to populate the form
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaPhong.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtTenPhong.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtDienTich.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtGiaThue.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    cbTrangThai.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
                    txtMaKhach.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    // Handle Add New Room
    private void handleThemMoi() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "INSERT INTO PhongTro (tenPhong, dienTich, giaThue, trangThai, maKhach) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, txtTenPhong.getText());
            statement.setDouble(2, Double.parseDouble(txtDienTich.getText()));
            statement.setDouble(3, Double.parseDouble(txtGiaThue.getText()));
            statement.setString(4, (String) cbTrangThai.getSelectedItem());
            statement.setLong(5, txtMaKhach.getText().isEmpty() ? 0 : Long.parseLong(txtMaKhach.getText()));

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
                loadData();  // Reload the data
                clearFields(); // Clear the form fields
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm mới dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    // Handle Update Room
    private void handleCapNhat() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "UPDATE PhongTro SET tenPhong=?, dienTich=?, giaThue=?, trangThai=?, maKhach=? WHERE maPhong=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, txtTenPhong.getText());
            statement.setDouble(2, Double.parseDouble(txtDienTich.getText()));
            statement.setDouble(3, Double.parseDouble(txtGiaThue.getText()));
            statement.setString(4, (String) cbTrangThai.getSelectedItem());
            statement.setLong(5, txtMaKhach.getText().isEmpty() ? 0 : Long.parseLong(txtMaKhach.getText()));
            statement.setLong(6, Long.parseLong(txtMaPhong.getText()));

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();  // Reload the data
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    // Handle Delete Room
    private void handleXoa() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "DELETE FROM PhongTro WHERE maPhong=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, Long.parseLong(txtMaPhong.getText()));

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                clearFields();
                loadData();  // Reload the data
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    // Clear the form fields
    private void clearFields() {
        txtMaPhong.setText("");
        txtTenPhong.setText("");
        txtDienTich.setText("");
        txtGiaThue.setText("");
        cbTrangThai.setSelectedIndex(0);
        txtMaKhach.setText("");
    }

    // Load data from the database into the table
    private void loadData() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT * FROM PhongTro";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0);  // Clear the table before adding new data

            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getLong("maPhong"),
                        resultSet.getString("tenPhong"),
                        resultSet.getDouble("dienTich"),
                        resultSet.getDouble("giaThue"),
                        resultSet.getString("trangThai"),
                        resultSet.getLong("maKhach")
                });
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    // Method to display the dialog
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame parentFrame = new JFrame();
            PhongTrolog dialog = new PhongTrolog(parentFrame);
            dialog.setVisible(true);
        });
    }
}
