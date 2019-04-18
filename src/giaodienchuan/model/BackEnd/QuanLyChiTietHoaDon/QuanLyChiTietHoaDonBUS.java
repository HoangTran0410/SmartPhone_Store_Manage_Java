package giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import java.util.ArrayList;

public class QuanLyChiTietHoaDonBUS {

    ArrayList<ChiTietHoaDon> dscthd = new ArrayList<>();
    private QuanLyChiTietHoaDonDAO qlcthdDAO = new QuanLyChiTietHoaDonDAO();
    private QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();

    public QuanLyChiTietHoaDonBUS() {
        dscthd = qlcthdDAO.readDB();
    }

    public ArrayList<ChiTietHoaDon> getDscthd() {
        return this.dscthd;
    }

    public void readDB() {
        dscthd = qlcthdDAO.readDB();
    }

    public ChiTietHoaDon getChiTiet(String mahd, String masp) {
        for (ChiTietHoaDon ct : dscthd) {
            if (ct.getMaSanPham().equals(masp) && ct.getMaHoaDon().equals(mahd)) {
                return ct;
            }
        }
        return null;
    }

    public Boolean add(ChiTietHoaDon hd) {
        ArrayList<ChiTietHoaDon> toRemove = new ArrayList<>();
        int soLuong = hd.getSoLuong();
        for (ChiTietHoaDon cthd : dscthd) {
            if (cthd.getMaHoaDon().equals(hd.getMaHoaDon()) && cthd.getMaSanPham().equals(hd.getMaSanPham())) {
                soLuong += cthd.getSoLuong();
                toRemove.add(cthd);
            }
        }
        dscthd.removeAll(toRemove);
        hd.setSoLuong(soLuong);

        qlcthdDAO.delete(hd.getMaHoaDon(), hd.getMaSanPham());
        Boolean success = qlcthdDAO.add(hd);
        if (success) {
            dscthd.add(hd);
            dscthd.forEach((t) -> {
                System.out.println(t.getMaHoaDon());
            });
            updateTongTien(hd.getMaHoaDon());
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
        Boolean success = qlcthdDAO.update(hd);
        if (success) {
            for (ChiTietHoaDon cthd : dscthd) {
                if (cthd.getMaHoaDon().equals(hd.getMaHoaDon())) {
                    cthd = hd;
                }
            }
            updateTongTien(hd.getMaHoaDon());
        }

        return success;
    }

    private Boolean updateTongTien(String _mahd) {
        float tong = 0;
        for (ChiTietHoaDon ct : dscthd) {
            if (ct.getMaHoaDon().equals(_mahd)) {
                tong += ct.getSoLuong() * ct.getDonGia();
            }
        }
        Boolean success = qlhdBUS.updateTongTien(_mahd, tong);
        return success;
    }

    public Boolean delete(String _maHoaDon, String _maSanPham) {
        Boolean success = qlcthdDAO.delete(_maHoaDon, _maSanPham);
        if (success) {
            for (ChiTietHoaDon cthd : dscthd) {
                if (cthd.getMaHoaDon().equals(_maHoaDon) && cthd.getMaSanPham().equals(_maSanPham)) {
                    dscthd.remove(cthd);
                    updateTongTien(_maHoaDon);
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean deleteAll(String _maHoaDon) {
        Boolean success = qlcthdDAO.deleteAll(_maHoaDon);
        if (success) {
            for (ChiTietHoaDon cthd : dscthd) {
                if (cthd.getMaHoaDon().equals(_maHoaDon)) {
                    dscthd.remove(cthd);
                }
            }
            updateTongTien(_maHoaDon);
            return true;
        }
        return false;
    }

    public ArrayList<ChiTietHoaDon> search(String type, String keyword) {
        ArrayList<ChiTietHoaDon> result = new ArrayList<>();
        readDB();

        dscthd.forEach((hd) -> {
            switch (type) {
                case "Tất cả":
                    if (hd.getMaHoaDon().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getMaSanPham().toLowerCase().contains(keyword.toLowerCase())
                            || String.valueOf(hd.getSoLuong()).toLowerCase().contains(keyword.toLowerCase())
                            || String.valueOf(hd.getDonGia()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }

                    break;

                case "Mã hóa đơn":
                    if (hd.getMaHoaDon().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Mã sản phẩm":
                    if (hd.getMaSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Số lượng":
                    if (String.valueOf(hd.getSoLuong()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Đơn giá":
                    if (String.valueOf(hd.getDonGia()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;
            }
        });
        return result;
    }

}
