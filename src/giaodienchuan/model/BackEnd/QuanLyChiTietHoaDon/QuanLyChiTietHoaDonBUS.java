package giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyChiTietHoaDonBUS {

    ArrayList<ChiTietHoaDon> dscthd = new ArrayList<>();
    private QuanLyChiTietHoaDonDAO qlcthd = new QuanLyChiTietHoaDonDAO();

    public QuanLyChiTietHoaDonBUS() {
        dscthd = qlcthd.readDB();
    }

    public ArrayList<ChiTietHoaDon> getDshd(){
        return this.dscthd;
    }
    
    public void readDB() {
        dscthd = qlcthd.readDB();
    }

    public Boolean add(ChiTietHoaDon hd) {
        Boolean success = qlcthd.add(hd);
        if (success) {
            dscthd.add(hd);
            return true;
        }
        return false;
    }

    public Boolean add(String maHoaDon, String maSanPham, int soLuong, float donGia) {
        ChiTietHoaDon hd = new ChiTietHoaDon(maHoaDon, maSanPham, soLuong, donGia);
        return add(hd);
    }

    public Boolean update(String maHoaDon, String maSanPham, int soLuong, float donGia) {
        ChiTietHoaDon hd = new ChiTietHoaDon(maHoaDon, maSanPham, soLuong, donGia);
        return update(hd);
    }

    public Boolean update(ChiTietHoaDon hd) {
        Boolean success = qlcthd.update(hd);
        if (success) {
            for (ChiTietHoaDon cthd : dscthd) {
                if (cthd.getMaHoaDon().equals(hd.getMaHoaDon())) {
                    cthd = hd;
                }
            }
        }
        return false;
    }

    public Boolean delelte(String maHoaDon) {
        Boolean success = qlcthd.delelte(maHoaDon);
        if (success) {
            for (ChiTietHoaDon cthd : dscthd) {
                if (cthd.getMaHoaDon().equals(maHoaDon)) {
                    dscthd.remove(cthd);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<ChiTietHoaDon> search(String type, String keyword) {
        ArrayList<ChiTietHoaDon> result = new ArrayList<>();
        readDB();
        if (type.equals("Tất cả")) {
            return dscthd;
        }
        switch (type) {
            case "Mã hóa đơn":
                dscthd.forEach((hd) -> {
                    if (hd.getMaHoaDon().equalsIgnoreCase(keyword)) {
                        result.add(hd);
                    }
                });
                break;
            case "Mã sản phẩm":
                dscthd.forEach((hd) -> {
                    if (hd.getMaSanPham().equalsIgnoreCase(keyword)) {
                        result.add(hd);
                    }
                });
                break;
            case "Số lượng":
                try {
                    Integer.parseInt(keyword);
                    dscthd.forEach((t) -> {
                        if (t.getSoLuong() == Integer.parseInt(keyword)) {
                            result.add(t);
                        }
                    });
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "So Luong phai la so !!!");
                }
                break;
            case "Đơn giá":
                try {
                    Float.parseFloat(keyword);
                    dscthd.forEach((t) -> {
                        if (t.getDonGia() == Float.parseFloat(keyword)) {
                            result.add(t);
                        }
                    });
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Don gia phai la so !!!");
                }
                break;
            default:
                break;
        }
        return result;
    }

}
