chạy lệnh này trong MySQL:
-- Tạo cơ sở dữ liệu
CREATE DATABASE QLPTro;

-- Sử dụng cơ sở dữ liệu
USE QLPTro;

CREATE TABLE ChuPhong (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,         -- Mã ID, kiểu số nguyên lớn, tự động tăng
    hoTen VARCHAR(255) NOT NULL,                  -- Họ và tên, chuỗi ký tự, không được để trống
    ngaySinh DATE,                                -- Ngày sinh, kiểu ngày tháng
    gioiTinh VARCHAR(50),                         -- Giới tính, chuỗi ký tự (ví dụ: "Nam", "Nữ")
    CCCD VARCHAR(20) UNIQUE NOT NULL,             -- Số căn cước công dân, chuỗi ký tự, không được để trống và phải duy nhất
    soDt VARCHAR(15) UNIQUE NOT NULL,             -- Số điện thoại, chuỗi ký tự, không được để trống và phải duy nhất
    tenTaiKhoan VARCHAR(100) UNIQUE NOT NULL,     -- Tên tài khoản, chuỗi ký tự, không được để trống và phải duy nhất
    matKhau VARCHAR(255) NOT NULL                 -- Mật khẩu, chuỗi ký tự, không được để trống
);

-- Tạo bảng KhachTro
CREATE TABLE KhachTro (
    maKhach BIGINT PRIMARY KEY AUTO_INCREMENT, -- Mã khách tự động tăng
    hoTen VARCHAR(255) NOT NULL,              -- Họ tên khách
    ngaySinh DATE,                            -- Ngày sinh
    gioiTinh VARCHAR(50),                     -- Giới tính
    queQuan VARCHAR(255),                     -- Quê quán
    soDienThoai VARCHAR(15) UNIQUE,          -- Số điện thoại (duy nhất)
    gmail VARCHAR(255) UNIQUE NOT NULL       -- Gmail (bắt buộc, duy nhất)
);

-- Tạo bảng DongTien
CREATE TABLE DongTien (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,     -- Mã đóng tiền tự động tăng
    maKhach BIGINT NOT NULL,                  -- Mã khách (liên kết với bảng KhachTro)
    soTien DOUBLE NOT NULL,                   -- Số tiền đóng
    ngayDong DATE NOT NULL,                   -- Ngày đóng tiền
    FOREIGN KEY (maKhach) REFERENCES KhachTro(maKhach) ON DELETE CASCADE -- Xóa khách sẽ xóa các bản ghi liên quan
);

-- Tạo bảng PhongTro
CREATE TABLE PhongTro (
    maPhong BIGINT PRIMARY KEY AUTO_INCREMENT, -- Mã phòng tự động tăng
    tenPhong VARCHAR(255) NOT NULL,            -- Tên phòng
    dienTich DOUBLE NOT NULL,                  -- Diện tích phòng
    giaThue DOUBLE NOT NULL,                   -- Giá thuê phòng
    trangThai VARCHAR(50) DEFAULT 'Trống',     -- Trạng thái phòng (Trống, Đã thuê)
    maKhach BIGINT,                            -- Mã khách đang thuê phòng (nếu có)
    FOREIGN KEY (maKhach) REFERENCES KhachTro(maKhach) ON DELETE SET NULL -- Xóa khách sẽ đặt NULL
);

-- Thêm cột maPhong vào bảng DongTien để liên kết thanh toán với phòng
ALTER TABLE DongTien
ADD COLUMN maPhong BIGINT,
ADD FOREIGN KEY (maPhong) REFERENCES PhongTro(maPhong) ON DELETE CASCADE;
