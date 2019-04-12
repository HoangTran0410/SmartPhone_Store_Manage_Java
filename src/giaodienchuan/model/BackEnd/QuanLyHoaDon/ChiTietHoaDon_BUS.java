package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChiTietHoaDon_BUS {

    ArrayList<ChiTietHoaDon_DTO> dshd = new ArrayList<>();
    private ChiTietHoaDon_DAO qlhd = new ChiTietHoaDon_DAO();

    public ChiTietHoaDon_BUS() {
    }

    public ArrayList<ChiTietHoaDon_DTO> getDshd(){
        return this.dshd;
    }
    
    public void readDB() {
        dshd = qlhd.readDB();
    }

    public Boolean add(ChiTietHoaDon_DTO hd) {
        Boolean success = qlhd.add(hd);
        if (success) {
            dshd.add(hd);
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
        Boolean success = qlhd.update(hd);
        if (success) {
            for (ChiTietHoaDon_DTO cthd : dshd) {
                if (cthd.getMaHoaDon().equals(hd.getMaHoaDon())) {
                    cthd = hd;
                }
            }
        }
        return false;
    }

    public Boolean delelte(String maHoaDon) {
        Boolean success = qlhd.delelte(maHoaDon);
        if (success) {
            for (ChiTietHoaDon_DTO cthd : dshd) {
                if (cthd.getMaHoaDon().equals(maHoaDon)) {
                    dshd.remove(cthd);
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
            return dshd;
        }
        switch (type) {
            case "Mã hóa đơn":
                dshd.forEach((hd) -> {
                    if (hd.getMaHoaDon().equalsIgnoreCase(keyword)) {
                        result.add(hd);
                    }
                });
                break;
            case "Mã sản phẩm":
                dshd.forEach((hd) -> {
                    if (hd.getMaSanPham().equalsIgnoreCase(keyword)) {
                        result.add(hd);
                    }
                });
                break;
            case "Số lượng":
                try {
                    Integer.parseInt(keyword);
                    dshd.forEach((t) -> {
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
                    dshd.forEach((t) -> {
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
