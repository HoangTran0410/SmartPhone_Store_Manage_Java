
package giaodienchuan.model.BackEnd.KhachHang;

/**
 *
 * @author DELL
 */
public class KhachHang {
    String MaKH;
    String TenKH;
    String DiaChi;
    int SDT;

    public KhachHang(String MaKH, String TenKH, String DiaChi, int SDT) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }
    
}
