package Repo;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhapRepo {
    public DangNhapRepo() {
    }

    public static DangNhapRepo getInstance() {
        return new DangNhapRepo();
    }

    public boolean DangNhap(String username, String password, long l) {
        boolean isAuthenticated = false;

        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ChuTro WHERE username=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true; // Login successful
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
}
