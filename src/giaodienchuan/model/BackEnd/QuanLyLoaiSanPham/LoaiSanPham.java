
package giaodienchuan.model.BackEnd.QuanLyLoaiSanPham;

public class LoaiSanPham {
    String MaLSP, TenLSP, Mota;
    
    public LoaiSanPham(String MaLSP, String TenLSP, String Mota) {
        this.MaLSP = MaLSP;
        this.TenLSP = TenLSP;
        this.Mota = Mota;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public String getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(String MaLSP) {
        this.MaLSP = MaLSP;
    }

    public String getTenLSP() {
        return TenLSP;
    }

    public void setTenLSP(String TenLSP) {
        this.TenLSP = TenLSP;
    }
}
