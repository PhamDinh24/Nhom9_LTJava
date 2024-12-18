package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import database.JDBCUtil;

public class XuatBaoCaoDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNgay;
    private JButton btnXuatBaoCao, btnHuy;

    public XuatBaoCaoDialog(JFrame parent) {
        super(parent, "Xuất Báo Cáo", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Ngày (yyyy-MM-dd):"));
        txtNgay = new JTextField();
        panel.add(txtNgay);

        btnXuatBaoCao = new JButton("Xuất Báo Cáo");
        btnHuy = new JButton("Hủy");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnXuatBaoCao);
        buttonPanel.add(btnHuy);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnXuatBaoCao.addActionListener(e -> handleXuatBaoCao());
        btnHuy.addActionListener(e -> dispose());
    }

    private void handleXuatBaoCao() {
        String ngay = txtNgay.getText().trim();

        if (ngay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection connection = JDBCUtil.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT DongTien.id, DongTien.maKhach, KhachTro.hoTen, DongTien.soTien, DongTien.ngayDong " +
                           "FROM DongTien JOIN KhachTro ON DongTien.maKhach = KhachTro.maKhach WHERE DongTien.ngayDong = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(ngay)); // Chuyển ngày về định dạng SQL

            ResultSet resultSet = statement.executeQuery();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Báo Cáo Đóng Tiền - " + ngay);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Mã Khách");
            headerRow.createCell(2).setCellValue("Tên Khách Hàng");
            headerRow.createCell(3).setCellValue("Số Tiền");
            headerRow.createCell(4).setCellValue("Ngày Đóng");

            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(resultSet.getLong("id"));
                row.createCell(1).setCellValue(resultSet.getLong("maKhach"));
                row.createCell(2).setCellValue(resultSet.getString("hoTen")); // Tên khách hàng
                row.createCell(3).setCellValue(resultSet.getDouble("soTien"));
                row.createCell(4).setCellValue(resultSet.getDate("ngayDong").toString());
            }

            resultSet.close();
            statement.close();
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu Báo Cáo");
            fileChooser.setSelectedFile(new java.io.File("BaoCaoDongTien-" + ngay + ".xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(fileToSave)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(this, "Báo cáo đã được xuất ra file Excel: " + fileToSave.getAbsolutePath());
                }
            }

            workbook.close();
            dispose(); 

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất báo cáo: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

}
