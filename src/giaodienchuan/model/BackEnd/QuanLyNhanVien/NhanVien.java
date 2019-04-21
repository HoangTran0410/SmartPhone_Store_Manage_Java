
package giaodienchuan.model.BackEnd.QuanLyNhanVien;

import java.time.LocalDate;

public class NhanVien {

    String MaNV, TenNV, DiaChi, SDT;
    LocalDate NgaySinh;
    int TrangThai;

    public NhanVien(String MaNV, String TenNV, LocalDate NgaySinh, String DiaChi, String SDT, int trangthai) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.NgaySinh = NgaySinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.TrangThai = trangthai;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public LocalDate getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(LocalDate NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
}
