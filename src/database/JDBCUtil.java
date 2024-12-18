package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/QLPTro?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Đăng ký driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Tạo kết nối
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối MySQL thành công!");
        } catch (ClassNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy Driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi: Không thể kết nối tới cơ sở dữ liệu MySQL!");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Đóng kết nối thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đóng kết nối!");
            e.printStackTrace();
        }
    }
}


