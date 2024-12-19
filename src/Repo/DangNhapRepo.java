package Repo;

import database.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhapRepo {
    public static DangNhapRepo getInstance() {
        return new DangNhapRepo();
    }

    public boolean DangNhap(String username, String password, Long role) {
        boolean isAuthenticated = false;

        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection == null) {
                throw new Exception("Không thể kết nối đến cơ sở dữ liệu.");
            }

            String tableName = (role == 0) ? "ChuTro" : "KhachHang";  // Chọn bảng theo role
            String sql = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?";  // Tạo câu truy vấn động

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password); // Kiểm tra mật khẩu trực tiếp, không mã hóa

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        isAuthenticated = true;  // Nếu tìm thấy người dùng với mật khẩu đúng
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Xử lý ngoại lệ, có thể thêm ghi log cho debug
        }

        return isAuthenticated;
    }

    // Thêm phương thức DangKy để đăng ký người dùng mới
    public boolean DangKy(String username, String password, Long role) {
        boolean isSuccess = false;

        try (Connection connection = JDBCUtil.getConnection()) {
            if (connection == null) {
                throw new Exception("Không thể kết nối đến cơ sở dữ liệu.");
            }

            String tableName = (role == 0) ? "ChuTro" : "KhachHang";  // Chọn bảng theo role
            String sql = "INSERT INTO " + tableName + " (username, password) VALUES (?, ?)";  // Thêm người dùng mới

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);  // Lưu mật khẩu (nên mã hóa trong thực tế)

                int rowsAffected = ps.executeUpdate();  // Thực hiện câu lệnh

                if (rowsAffected > 0) {
                    isSuccess = true;  // Đăng ký thành công
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Xử lý ngoại lệ, có thể thêm ghi log cho debug
        }

        return isSuccess;
    }
}
