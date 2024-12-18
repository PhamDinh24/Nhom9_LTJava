import Model.Phong;
import UI.DongTienDialog;
import UI.XuatBaoCaoDialog;
import database.JDBCUtil;
import UI.PhongTrolog;
import UI.Trolog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private JFrame frame;
    private JTextField txtMaSV, txtQueQuan, txtHoTen, txtSDT, txtNgaySinh, txtGmail;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbGioiTinh;

    public Main() {
        initializeUI();
        setupEventListeners();
        connectToDatabase();
    }

    private void initializeUI() {
        frame = new JFrame("Quản Lý Phòng Trọ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = createTitlePanel();

        // Center Panel with Form and Table
        JPanel centerPanel = createCenterPanel();

        // Menu Panel
        JPanel menuPanel = createMenuPanel();

        // Button Panel
        JPanel buttonPanel = createButtonPanel();

        // Main Panel Assembly
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
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

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createTitledBorder("Thông Tin Khách trọ:")
        ));

        formPanel.add(new JLabel("Mã Khách:"));
        txtMaSV = new JTextField();
        formPanel.add(txtMaSV);

        formPanel.add(new JLabel("Quê Quán:"));
        txtQueQuan = new JTextField();
        formPanel.add(txtQueQuan);

        formPanel.add(new JLabel("Họ Tên:"));
        txtHoTen = new JTextField();
        formPanel.add(txtHoTen);
        
        formPanel.add(new JLabel("Giới Tính:"));
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"}); 
        formPanel.add(cbGioiTinh);

        formPanel.add(new JLabel("Số điện thoại:"));
        txtSDT = new JTextField();
        formPanel.add(txtSDT);

        formPanel.add(new JLabel("Ngày Sinh:"));
        txtNgaySinh = new JTextField();
        formPanel.add(txtNgaySinh);

        formPanel.add(new JLabel("Gmail:"));
        txtGmail = new JTextField();
        formPanel.add(txtGmail);

        centerPanel.add(formPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Khách", "Họ Tên", "Ngày Sinh", "Giới Tính", "Quê Quán", "Số Điện Thoại", "Gmail"});
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        loadData();
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaSV.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    cbGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                    txtQueQuan.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txtSDT.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    txtGmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
                }
            }
        });

        return centerPanel;
    }
    
    private void showDanhSachPhongTro() {
    	PhongTrolog PhongTrolog = new PhongTrolog(frame);
        PhongTrolog.setVisible(true);
    }
    
    private void showNhapTro() {
    	Trolog Trolog = new Trolog(frame);
        Trolog.setVisible(true);
    }
    
    private void showDongTien() {
        DongTienDialog dongTienDialog = new DongTienDialog(frame);
        dongTienDialog.setVisible(true);
    }
    
    private void showXuatBaoCao() {
        XuatBaoCaoDialog xuatBaoCaoDialog = new XuatBaoCaoDialog(frame); 
        xuatBaoCaoDialog.setVisible(true); 
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] menuItems = {
            "Danh sách Phòng trọ", 
            "Nhập trọ", 
            "Đóng tiền", 
            "Xuất Báo Cáo"
        };

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setBackground(new Color(240, 240, 240));

            btn.addActionListener(e -> {
                System.out.println("Bạn đã chọn menu: " + item);

                switch (item) {
                    case "Danh sách Phòng trọ":
                        showDanhSachPhongTro();
                        break;
                    case "Nhập trọ":
                        showNhapTro();
                        break;
                    case "Đóng tiền":
                        showDongTien();
                        break;
                    case "Xuất Báo Cáo":
                        showXuatBaoCao();
                        break;
                    default:
                        System.out.println("Menu không xác định!");
                }
            });

            menuPanel.add(btn);
        }

        return menuPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        String[] buttonLabels = {"Thêm mới", "Cập Nhật", "Xóa"};

        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            btn.addActionListener(e -> {
                System.out.println("Bạn đã nhấn nút: " + label);
                switch (label) {
                    case "Thêm mới":
                        handleThemmoi();
                        break;
                    case "Cập Nhật":
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

        return buttonPanel;
    }
    
    private void handleThemmoi() {
    	String gioiTinh = (String) cbGioiTinh.getSelectedItem();
    	Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(frame, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "INSERT INTO KhachTro (hoTen, ngaySinh, gioiTinh, queQuan, soDienThoai, gmail) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

           
            statement.setString(1, txtHoTen.getText());
            statement.setDate(2, java.sql.Date.valueOf(txtNgaySinh.getText())); 
            statement.setString(3, gioiTinh);
            statement.setString(4, txtQueQuan.getText());
            statement.setString(5, txtSDT.getText());
            statement.setString(6, txtGmail.getText());

            
            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Thêm mới thành công!");
                loadData(); 
                clearFields(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Không thể thêm mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm mới dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
     
    }

    
    private void handleTimKiem() {
        System.out.println("Đang xử lý tìm kiếm...");
     
    }


    private void handleCapNhat() {
    	String gioiTinh = (String) cbGioiTinh.getSelectedItem();
    	Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(frame, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "UPDATE KhachTro SET hoTen=?, ngaySinh=?, gioiTinh=?, queQuan=?, soDienThoai=?, gmail=? WHERE maKhach=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, txtHoTen.getText());
            statement.setDate(2, java.sql.Date.valueOf(txtNgaySinh.getText())); // YYY-MM-DD
            statement.setString(3, gioiTinh);
            statement.setString(4, txtQueQuan.getText());
            statement.setString(5, txtSDT.getText());
            statement.setString(6, txtGmail.getText());
            statement.setLong(7, Long.parseLong(txtMaSV.getText()));

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Cập nhật thành công!");
                loadData(); 
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
    }


    private void handleXoa() {
    	Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(frame, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "DELETE FROM KhachTro WHERE maKhach=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, Long.parseLong(txtMaSV.getText()));

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Xóa thành công!");
                clearFields();
                loadData();
            }

            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
        
    }

    private void handleHienThi() {
        System.out.println("Đang xử lý hiển thị...");
       
    }
    
    private void clearFields() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtQueQuan.setText("");
        txtSDT.setText("");
        txtGmail.setText("");
    }



    private void setupEventListeners() {
        // TODO: Implement event handling for buttons
    }

    private void connectToDatabase() {
        try {
            Connection connection = JDBCUtil.getConnection();
            if (connection != null) {
                System.out.println("Database connection successful!");
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, 
                "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadData() {
        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
        	JOptionPane.showMessageDialog(frame, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT * FROM KhachTro";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

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

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
        	JDBCUtil.closeConnection(connection);
        }
    }

    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> new Main());
    }
}