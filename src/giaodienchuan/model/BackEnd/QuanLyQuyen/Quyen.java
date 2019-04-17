package giaodienchuan.model.BackEnd.QuanLyQuyen;

public class Quyen {
    
    String MaQuyen, ChiTietQuyen;
    
    public Quyen(String maquyen, String chitietquyen) {
        this.MaQuyen = maquyen;
        this.ChiTietQuyen = chitietquyen;
    }

    public String getMaQuyen() {
        return MaQuyen;
    }

    public void setMaQuyen(String MaQuyen) {
        this.MaQuyen = MaQuyen;
    }

    public String getChiTietQuyen() {
        return ChiTietQuyen;
    }

    public void setChiTietQuyen(String ChiTietQuyen) {
        this.ChiTietQuyen = ChiTietQuyen;
    }
}
