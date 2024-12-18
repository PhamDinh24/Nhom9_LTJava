package Repo.DAO;

import Model.ChuPhong;
import Model.DiaChi;
import Model.KhachHang;
import Model.Phong;
import database.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO {
    public static PhongDAO getInstance() {
        return new PhongDAO();
    }

    public void insert(Phong phong) {
        try {

            if (phong.getId() == null || phong.getDiaChi() == null ||
                    phong.getGia() == 0.0 || phong.getMoTa() == null ||
                    phong.getDienTich() == 0.0 || phong.getHinhAnh() == null) {

                throw new IllegalArgumentException("loi cac truong khong duoc trong");
            }

            Connection con = JDBCUtil.getConnection();
            String query = "INSERT INTO Tro VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setLong(1, phong.getId());
                ps.setObject(2, phong.getDiaChi());
                ps.setDouble(3, phong.getGia());
                ps.setString(4, phong.getMoTa());
                ps.setDouble(5, phong.getDienTich());
                ps.setString(6, phong.getHinhAnh());
                ps.setObject(7, phong.getChu());
                ps.setObject(8, phong.getKhach());

                ps.executeUpdate();
            }
            JDBCUtil.closeConnection(con);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePhong(Long id, DiaChi diaChi, Double gia, String moTa, Double dienTich, String hinhAnh, ChuPhong chu, KhachHang khach) {
        try {
            Connection con = JDBCUtil.getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE phong SET ");

            boolean firstField = true;

            if (gia != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("GiaPhong = ?");
                firstField = false;
            }
            if (moTa != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("MoTa = ?");
                firstField = false;
            }
            if (hinhAnh != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("HinhAnh = ?");
                firstField = false;
            }
            if (chu.getId() != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("ChuTroID = ?");
                firstField = false;
            }
            if (khach.getId() != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("KhachHangID = ?");
                firstField = false;
            }
            if (diaChi.getId() != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("DiaChiID = ?");
                firstField = false;
            }
            if (dienTich != null) {
                if (!firstField) stringBuilder.append(", ");
                stringBuilder.append("DienTich = ?");
            }

            stringBuilder.append(" WHERE TroID = ?");

            PreparedStatement ps = con.prepareStatement(stringBuilder.toString());

            int paramIndex = 1;

            if (gia != null) {
                ps.setDouble(paramIndex++, gia);
            }
            if (moTa != null) {
                ps.setString(paramIndex++, moTa);
            }
            if (hinhAnh != null) {
                ps.setString(paramIndex++, hinhAnh);
            }
            if (chu.getId() != null) {
                ps.setLong(paramIndex++, chu.getId());
            }
            if (khach.getId() != null) {
                ps.setLong(paramIndex++, khach.getId());
            }
            if (diaChi.getId() != null) {
                ps.setLong(paramIndex++, diaChi.getId());
            }
            if (dienTich != null) {
                ps.setDouble(paramIndex++, dienTich);
            }

            ps.setLong(paramIndex, id);

            ps.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(Phong obj) {
        if (obj.getId() == null) {
            System.out.println("id is null");
            return;
        }
        try {
            Connection con = JDBCUtil.getConnection();
            String query = "DELETE FROM Tro WHERE TroID = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setLong(1, obj.getId());
                ps.execute();
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Phong findById(Long id) {
        return null;
    }

    public List<Phong> findPhong(String Tinh, String Huyen, String Xa, String TenDuong, String soNha, Integer timKiemGiaTu, Integer timKiemGiaDen) {
        List<Phong> list = new ArrayList<>();
        int num_col = 1;
        try {
            Connection con = JDBCUtil.getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" SELECT * FROM Tro INNER JOIN DiaChi ON Tro.DiaChiID = DiaChi.DiaChiID" +
                    " WHERE 1=1 ");
            //Doan nay de hoan thien cau lenh sql
            if (Tinh != null) {
                stringBuilder.append(" AND TinhThanhPho LIKE ? ");
            }
            if (Huyen != null) {
                stringBuilder.append(" AND HuyenQuan LIKE ? ");
            }
            if (Xa != null) {
                stringBuilder.append(" AND PhuongXa LIKE ? ");
            }
            if (TenDuong != null) {
                stringBuilder.append(" AND TenDuong LIKE ? ");
            }
            if (soNha != null) {
                stringBuilder.append(" AND SoNha LIKE ? ");
            }
            if (timKiemGiaTu != null) {
                stringBuilder.append(" AND GiaPhong > ?  ");
            }
            if (timKiemGiaDen != null) {
                stringBuilder.append(" AND GiaPhong < ? ");
            }


            try (PreparedStatement ps = con.prepareStatement(stringBuilder.toString())) {
                if (Tinh != null) {
                    ps.setString(num_col, Tinh);
                    num_col++;
                }
                if (Huyen != null) {
                    ps.setString(num_col, Huyen);
                    num_col++;
                }
                if (Xa != null) {
                    ps.setString(num_col, Xa);
                    num_col++;
                }
                if (TenDuong != null) {
                    ps.setString(num_col, TenDuong);
                    num_col++;
                }
                if (soNha != null) {
                    ps.setString(num_col, soNha);
                    num_col++;
                }
                if (timKiemGiaTu != null) {
                    ps.setInt(num_col, timKiemGiaTu);
                    num_col++;
                }
                if (timKiemGiaDen != null) {
                    ps.setInt(num_col, timKiemGiaDen);
                    num_col++;
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Phong p = new Phong();
                    p.setId(rs.getLong("TroID"));
                    p.setDiaChi(DiaChiDAO.getInstance().findById(rs.getLong("DiaChiID")));
                    p.setGia((rs.getDouble("GiaPhong")));
                    p.setMoTa(rs.getString("MoTa"));
                    //moi
                    p.setDienTich(rs.getDouble("DienTich"));
                    p.setHinhAnh(rs.getString("HinhAnh"));
                    p.setChu(ChuPhongDAO.getInstance().findById(rs.getLong("ChuTroID")));
                    p.setKhach(KhachHangDAO.getInstance().findById(rs.getLong("KhachHangID")));
                    list.add(p);
                }
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
