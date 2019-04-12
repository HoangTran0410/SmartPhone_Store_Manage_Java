package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import java.time.LocalDate;
import java.time.LocalTime;

public class HoaDon_DTO {

    private String maHoaDon = "";
    private String maNhanVien = "";
    private String maKhachHang = "";
    private LocalDate ngayLap ;
    private LocalTime gioLap ;
    private float TongTien = 0;

    public HoaDon_DTO() {
        ngayLap = LocalDate.now();
        gioLap = LocalTime.now();
    }
    
    public HoaDon_DTO(String maHoaDon, String maNhanVien, String maKhachHang,LocalDate ngayNhap,LocalTime gioNhap,float tongTien) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien =maNhanVien;
        this.maKhachHang =  maKhachHang;
        this.ngayLap = ngayNhap;
        this.gioLap = gioNhap;
        this.TongTien =tongTien;
    }// lam di ong :v lam cai nay chi ??

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public LocalTime getGioLap() {
        return gioLap;
    }

    public void setGioLap(LocalTime gioLap) {
        this.gioLap = gioLap;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }
    
    
    
}
