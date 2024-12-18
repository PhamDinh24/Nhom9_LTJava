package Repo.DAO;

import Model.*;
import database.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChuPhongDAO {
    public static ChuPhongDAO getInstance() {
        return new ChuPhongDAO();
    }

    
    public void insert(ChuPhong obj) {
        try {
            Connection con = JDBCUtil.getConnection();
            String query = " INSERT INTO ChuTro(ChuTroID, HoTen, NgaySinh, GioiTinh, SoCanCuocCongDan, SoDienThoai, TaiKhoan, MatKhau) VALUES\n" +
                    "(NEXT VALUE FOR ChuTro_seq, ?, ?, ?, ?, ?, ?, ? ) ";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, obj.getHoTen());
                ps.setDate(2, new java.sql.Date(obj.getNgaySinh().getTime()));
                ps.setString(3, obj.getGioiTinh());
                ps.setString(4, obj.getCCCD());
                ps.setString(5, obj.getSoDt());
                ps.setString(6, obj.getTenTaiKhoan());
                ps.setString(7, obj.getMatKhau());
                ps.executeUpdate();
            }
            System.out.println("Insert Successful !");
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateChuPhong(Long id, String hoTen, Date ngaySinh, String gioiTinh, String CCCD, String soDt) {
        try {
            Connection con = JDBCUtil.getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" UPDATE ChuPhong SET ");

            boolean firstField = true;

            if (hoTen != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append(" HoTen = ?");
                firstField = false;
            }
            if (ngaySinh != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append(" NgaySinh = ?");
                firstField = false;
            }
            if (gioiTinh != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append(" GioiTinh = ?");
                firstField = false;
            }
            if (CCCD != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append(" CCCD = ?");
                firstField = false;
            }
            if (soDt != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append(" SoDienThoai = ?");
                firstField = false;
            }

            stringBuilder.append(" WHERE TroID = ?");

            PreparedStatement ps = con.prepareStatement(stringBuilder.toString());

            int paramIndex = 1;

            if (hoTen != null) {
                ps.setString(paramIndex++, hoTen);
            }
            if (ngaySinh != null) {
                ps.setDate(paramIndex++, (java.sql.Date) ngaySinh);
            }
            if (gioiTinh != null) {
                ps.setString(paramIndex++, gioiTinh);
            }
            if (CCCD != null) {
                ps.setString(paramIndex++, CCCD);
            }
            if (soDt != null) {
                ps.setString(paramIndex++, soDt);
            }

            ps.setLong(paramIndex, id);

            ps.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void delete(ChuPhong obj) {
        if (obj.getId() == null) {
            System.out.println("loi id is null");
            return;
        }

        try {
            Connection con = JDBCUtil.getConnection();
            String query = "DELETE FROM ChuTro WHERE ChuTroID=?";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setLong(1, obj.getId());
                ps.execute();
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
    public ChuPhong findById(Long id) {
        Connection co = JDBCUtil.getConnection();
        String query = " SELECT * FROM ChuTro WHERE ChuTroID=? ";
        ChuPhong ketQua = null;
        try(PreparedStatement ps = co.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Long chuTroId = rs.getLong("ChuTroId");
                String hoTen = rs.getString("HoTen");
                Date ngaySinh = new java.util.Date(rs.getDate("NgaySinh").getTime());
                String gioiTinh = rs.getString("GioiTinh");
                String cccd = rs.getString("SoCanCuocCongDan");
                String soDienThoai = rs.getString("SoDienThoai");
                String taiKhoan = rs.getString(("TaiKhoan"));
                String matKhau = rs.getString(("MatKhau"));
                ketQua = new ChuPhong(chuTroId, hoTen, ngaySinh, gioiTinh, cccd, soDienThoai, taiKhoan, matKhau);
            }
            JDBCUtil.closeConnection(co);
        } catch(Exception e){
            e.printStackTrace();
        }
        return ketQua;
    }

    public List<Phong> findPhong(ChuPhong chu){
        List<Phong> listPhong = new ArrayList<>();

        Connection con = JDBCUtil.getConnection();
        String query = " SELECT * FROM Tro WHERE ChuTroID = ? ";
        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, chu.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Phong p = new Phong();
                p.setId(rs.getLong("TroID"));
                p.setDiaChi(DiaChiDAO.getInstance().findById(rs.getLong("DiaChiID")));
                p.setGia((rs.getDouble("GiaPhong")));
                p.setMoTa(rs.getString("MoTa"));
                p.setHinhAnh(rs.getString("HinhAnh"));
                p.setChu(ChuPhongDAO.getInstance().findById(chu.getId()));
                p.setKhach(KhachHangDAO.getInstance().findById(rs.getLong("KhachHangID")));
                p.setDienTich(rs.getDouble("DienTich"));
                listPhong.add(p);
            }
        JDBCUtil.closeConnection(con);
        } catch(Exception e){
            e.printStackTrace();
        }
        return listPhong;
    }
}
