package Controller;

import Model.KhachHang;
import Repo.DAO.KhachHangDAO;

import java.util.Date;


public class KhachHangController {
    public void suaKhachHang(Long id, String hoTen, Date namSinh, String gioiTinh, String cccd, String soDienThoai){
        KhachHangDAO.getInstance().updateKhachHang(id, hoTen, namSinh, gioiTinh, cccd, soDienThoai);
    }
}
