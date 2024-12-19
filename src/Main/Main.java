package Main;

import Model.Phong;
import UI.DongTienDialog;
import UI.XuatBaoCaoDialog;
import database.JDBCUtil;
import UI.PhongTrolog;
import UI.Trolog;
import UI.TimKiemDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private JFrame frame;
    private JTextField txtMaKhach, txtQueQuan, txtHoTen, txtSDT, txtNgaySinh, txtEmail;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbGioiTinh;

    public Main() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        frame = new JFrame("Quản Lý Phòng Trọ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createMenuPanel(), BorderLayout.WEST);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Frame visible only once after fully set up
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Danh sách các khách trọ", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Khách Trọ"));

        txtMaKhach = createLabeledTextField(formPanel, "Mã Khách:");
        txtHoTen = createLabeledTextField(formPanel, "Họ Tên:");
        txtNgaySinh = createLabeledTextField(formPanel, "Ngày Sinh:");

        formPanel.add(new JLabel("Giới Tính:"));
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        formPanel.add(cbGioiTinh);

        txtQueQuan = createLabeledTextField(formPanel, "Quê Quán:");
        txtSDT = createLabeledTextField(formPanel, "Số Điện Thoại:");
        txtEmail = createLabeledTextField(formPanel, "Email:");

        centerPanel.add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Khách", "Họ Tên", "Ngày Sinh", "Giới Tính", "Quê Quán", "Số Điện Thoại", "Email"});
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                populateFormFromTable();
            }
        });
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
        return centerPanel;
    }

    private JTextField createLabeledTextField(JPanel panel, String labelText) {
        panel.add(new JLabel(labelText));
        JTextField textField = new JTextField();
        panel.add(textField);
        return textField;
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            txtMaKhach.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
            cbGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
            txtQueQuan.setText(tableModel.getValueAt(selectedRow, 4).toString());
            txtSDT.setText(tableModel.getValueAt(selectedRow, 5).toString());
            txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createMenuButton(menuPanel, "Danh Sách Phòng Trọ", this::showDanhSachPhongTro);
        createMenuButton(menuPanel, "Nhập Trọ", this::showNhapTro);
        createMenuButton(menuPanel, "Đóng Tiền", this::showDongTien);
        createMenuButton(menuPanel, "Xuất Báo Cáo", this::showXuatBaoCao);

        return menuPanel;
    }

    private void createMenuButton(JPanel panel, String title, Runnable action) {
        JButton btn = new JButton(title);
        btn.addActionListener(e -> action.run());
        panel.add(btn);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        createActionButton(buttonPanel, "Thêm Mới", this::handleThemmoi);
        createActionButton(buttonPanel, "Cập Nhật", this::handleCapNhat);
        createActionButton(buttonPanel, "Xóa", this::handleXoa);
        createActionButton(buttonPanel, "Tìm kiếm", this::TimKiemDialog);
        return buttonPanel;
    }

    private void createActionButton(JPanel panel, String label, Runnable action) {
        JButton btn = new JButton(label);
        btn.addActionListener(e -> action.run());
        panel.add(btn);
    }

    private void connectToDatabase() {
        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM KhachTro");
             ResultSet resultSet = statement.executeQuery()) {

            tableModel.setRowCount(0);

            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getLong("maKhach"),
                        resultSet.getString("hoTen"),
                        resultSet.getDate("ngaySinh"),
                        resultSet.getString("gioiTinh"),
                        resultSet.getString("queQuan"),
                        resultSet.getString("soDienThoai"),
                        resultSet.getString("gmail")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleThemmoi() {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO KhachTro (hoTen, ngaySinh, gioiTinh, queQuan, soDienThoai, gmail) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, txtHoTen.getText());
            statement.setDate(2, java.sql.Date.valueOf(txtNgaySinh.getText()));
            statement.setString(3, cbGioiTinh.getSelectedItem().toString());
            statement.setString(4, txtQueQuan.getText());
            statement.setString(5, txtSDT.getText());
            statement.setString(6, txtEmail.getText());

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Thêm mới thành công!");
                loadData();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm mới: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCapNhat() {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE KhachTro SET hoTen=?, ngaySinh=?, gioiTinh=?, queQuan=?, soDienThoai=?, gmail=? WHERE maKhach=?")) {

            statement.setString(1, txtHoTen.getText());
            statement.setDate(2, java.sql.Date.valueOf(txtNgaySinh.getText()));
            statement.setString(3, cbGioiTinh.getSelectedItem().toString());
            statement.setString(4, txtQueQuan.getText());
            statement.setString(5, txtSDT.getText());
            statement.setString(6, txtEmail.getText());
            statement.setLong(7, Long.parseLong(txtMaKhach.getText()));

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Cập nhật thành công!");
                loadData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleXoa() {
        int option = JOptionPane.showConfirmDialog(frame,
                "Bạn có chắc chắn muốn xóa khách trọ này?",
                "Xác Nhận Xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            try (Connection connection = JDBCUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM KhachTro WHERE maKhach=?")) {

                statement.setLong(1, Long.parseLong(txtMaKhach.getText()));

                int rows = statement.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                    loadData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "Mã khách không tồn tại trong cơ sở dữ liệu!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Hành động xóa đã bị hủy bỏ.");
        }
    }

    private void clearFields() {
        txtMaKhach.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cbGioiTinh.setSelectedIndex(0);
        txtQueQuan.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
    }

    private void showDanhSachPhongTro() {
        new PhongTrolog(frame).setVisible(true);
    }

    private void showNhapTro() {
        new Trolog(frame).setVisible(true);
    }

    private void showDongTien() {
        new DongTienDialog(frame).setVisible(true);
    }

    private void showXuatBaoCao() {
        new XuatBaoCaoDialog(frame).setVisible(true);
    }

    private void TimKiemDialog() {
        new TimKiemDialog(frame).setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

	public void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}

	public void setSize(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	public void setLocationRelativeTo(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setTitle(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
