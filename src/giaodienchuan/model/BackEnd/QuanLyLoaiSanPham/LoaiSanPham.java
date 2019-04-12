
package giaodienchuan.model.BackEnd.QuanLyLoaiSanPham;

public class LoaiSanPham {
    String MaLSP, TenLSP;
    
    public LoaiSanPham(String MaLSP, String TenLSP) {
        this.MaLSP = MaLSP;
        this.TenLSP = TenLSP;
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
