
package giaodienchuan.model.BackEnd.NhanVien;

public class NhanVien {
    String MaNV,MaCV,TenNV,NgaySinh,DiaChi;
    long SDT;
    public NhanVien(String MaNV, String MaCV, String TenNV, String NgaySinh, String DiaChi, long SDT){
        this.MaNV=MaNV;
        this.MaCV=MaCV;
        this.TenNV=TenNV;
        this.NgaySinh=NgaySinh;
        this.DiaChi=DiaChi;
        this.SDT=SDT;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public long getSDT() {
        return SDT;
    }

    public void setSDT(long SDT) {
        this.SDT = SDT;
    }
    
}
