package Model;

public class DiaChi {
    private Long id;
    private String soNha;
    private String tenDuong;
    private String tenPhuongXa;
    private String tenQuanHuyen;
    private String tenTinhThanhPho;

    public DiaChi() {
    }

    public DiaChi(Long id, String soNha, String tenDuong, String tenPhuongXa, String tenQuanHuyen, String tenTinhThanhPho) {
        this.id = id;
        this.soNha = soNha;
        this.tenDuong = tenDuong;
        this.tenPhuongXa = tenPhuongXa;
        this.tenQuanHuyen = tenQuanHuyen;
        this.tenTinhThanhPho = tenTinhThanhPho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getTenDuong() {
        return tenDuong;
    }

    public void setTenDuong(String tenDuong) {
        this.tenDuong = tenDuong;
    }

    public String getTenPhuongXa() {
        return tenPhuongXa;
    }

    public void setTenPhuongXa(String tenPhuongXa) {
        this.tenPhuongXa = tenPhuongXa;
    }

    public String getTenQuanHuyen() {
        return tenQuanHuyen;
    }

    public void setTenQuanHuyen(String tenQuanHuyen) {
        this.tenQuanHuyen = tenQuanHuyen;
    }

    public String getTenTinhThanhPho() {
        return tenTinhThanhPho;
    }

    public void setTenTinhThanhPho(String tenTinhThanhPho) {
        this.tenTinhThanhPho = tenTinhThanhPho;
    }

    @Override
    public String toString() {
        return "DiaChi{" +
                "id=" + id +
                ", soNha=" + soNha +
                ", tenDuong='" + tenDuong + '\'' +
                ", tenPhuongXa='" + tenPhuongXa + '\'' +
                ", tenQuanHuyen='" + tenQuanHuyen + '\'' +
                ", tenTinhThanhPho='" + tenTinhThanhPho + '\'' +
                '}';
    }
}
