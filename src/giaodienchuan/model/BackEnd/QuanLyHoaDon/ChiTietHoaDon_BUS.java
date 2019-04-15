package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChiTietHoaDon_BUS {

    ArrayList<ChiTietHoaDon_DTO> dscthd = new ArrayList<>();
    private ChiTietHoaDon_DAO qlcthd = new ChiTietHoaDon_DAO();

    public ChiTietHoaDon_BUS() {
        dscthd = qlcthd.readDB();
    }

    public ArrayList<ChiTietHoaDon_DTO> getDshd(){
        return this.dscthd;
    }
    
    public void readDB() {
        dscthd = qlcthd.readDB();
    }

    public Boolean add(ChiTietHoaDon_DTO hd) {
        Boolean success = qlcthd.add(hd);
        if (success) {
            dscthd.add(hd);
            return true;
        }
        return false;
    }

    public Boolean add(String maHoaDon, String maSanPham, int soLuong, float donGia) {
        ChiTietHoaDon_DTO hd = new ChiTietHoaDon_DTO();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaSanPham(maSanPham);
        hd.setSoLuong(soLuong);
        hd.setDonGia(donGia);
        return add(hd);
    }

    public Boolean update(String maHoaDon, String maSanPham, int soLuong, float donGia) {
        ChiTietHoaDon_DTO hd = new ChiTietHoaDon_DTO();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaSanPham(maSanPham);
        hd.setSoLuong(soLuong);
        hd.setDonGia(donGia);
        return update(hd);
    }

    public Boolean update(ChiTietHoaDon_DTO hd) {
        Boolean success = qlcthd.update(hd);
        if (success) {
            for (ChiTietHoaDon_DTO cthd : dscthd) {
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
            for (ChiTietHoaDon_DTO cthd : dscthd) {
                if (cthd.getMaHoaDon().equals(maHoaDon)) {
                    dscthd.remove(cthd);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<ChiTietHoaDon_DTO> search(String type, String keyword) {
        ArrayList<ChiTietHoaDon_DTO> result = new ArrayList<>();
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
