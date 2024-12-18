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
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ? WHERE username=? AND password=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                // 0: Chu phong, 1:KH
                if (role == 0) {
                    ps.setString(1, "ChuTro");
                } else {
                    ps.setString(1, "KhachHang");
                }
                ps.setString(2, username);
                ps.setString(3, password);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    isAuthenticated = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }
}
