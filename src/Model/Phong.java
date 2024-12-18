package Model;

import database.JDBCUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Phong {
    private Long id;
    private DiaChi diaChi;
    private double gia;
    private String moTa;
    private double dienTich;
    private String hinhAnh;
    private ChuPhong chu;
    private KhachHang khach;

    public Phong() {
    }

    public Phong(Long id, DiaChi diaChi, double gia, String moTa, double dienTich, String hinhAnh, ChuPhong chu, KhachHang khach) {
        this.id = id;
        this.diaChi = diaChi;
        this.gia = gia;
        this.moTa = moTa;
        this.dienTich = dienTich;
        this.hinhAnh = hinhAnh;
        this.chu = chu;
        this.khach = khach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public ChuPhong getChu() {
        return chu;
    }

    public void setChu(ChuPhong chu) {
        this.chu = chu;
    }

    public KhachHang getKhach() {
        return khach;
    }

    public void setKhach(KhachHang khach) {
        this.khach = khach;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "id=" + id +
                ", diaChi=" + diaChi +
                ", gia=" + gia +
                ", moTa='" + moTa + '\'' +
                ", dienTich=" + dienTich +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", chu=" + chu +
                ", khach=" + khach +
                '}';
    }
}