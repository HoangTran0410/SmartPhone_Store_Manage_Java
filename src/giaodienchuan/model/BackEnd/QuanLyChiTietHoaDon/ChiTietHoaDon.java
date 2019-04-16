package giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon;

public class ChiTietHoaDon {

    private String maHoaDon;
    private String maSanPham;
    private int soLuong;
    private float donGia;

    public ChiTietHoaDon(String mahd, String masp, int soluong, float dongia) {
        this.maHoaDon = mahd;
        this.maSanPham = masp;
        this.soLuong = soluong;
        this.donGia = dongia;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

}
