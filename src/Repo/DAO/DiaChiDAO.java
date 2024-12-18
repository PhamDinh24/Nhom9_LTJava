package Repo.DAO;

import database.JDBCUtil;
import Model.DiaChi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DiaChiDAO {
    public static DiaChiDAO getInstance() {
        return new DiaChiDAO();
    }

    
    public void insert(DiaChi obj) {

    }

    
    public void update(DiaChi obj) {

    }

    
    public void delete(DiaChi obj) {

    }

    
    public DiaChi findById(Long id) {
        Connection co = JDBCUtil.getConnection();
        String query = " SELECT * FROM DiaChi WHERE DiaChiID=? ";
        DiaChi ketQua = null;
        try(PreparedStatement ps = co.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Long DiaChiId = rs.getLong("DiaChiID");
                String soNha = rs.getString("SoNha");
                String tenDuong = rs.getString("TenDuong");
                String tenPhuongXa = rs.getString("PhuongXa");
                String tenQuanHuyen = rs.getString("HuyenQuan");
                String tenTinhThanhPho = rs.getString("TinhThanhPho");
                ketQua = new DiaChi(DiaChiId, soNha, tenDuong, tenPhuongXa, tenQuanHuyen, tenTinhThanhPho);
            }
            JDBCUtil.closeConnection(co);
        } catch(Exception e){
            e.printStackTrace();
        }
        return ketQua;
    }
}
