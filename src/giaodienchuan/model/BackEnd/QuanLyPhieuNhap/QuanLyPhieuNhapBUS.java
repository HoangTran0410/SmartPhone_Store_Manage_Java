/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyPhieuNhap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class QuanLyPhieuNhapBUS {

    QuanLyPhieuNhapDAO DAO = new QuanLyPhieuNhapDAO();
    ArrayList<PhieuNhap> dspn = new ArrayList<>();

    public QuanLyPhieuNhapBUS() {
        dspn = DAO.readDB();
    }

    public void readDB() {
        dspn = DAO.readDB();
    }
    
    public String getNextID() {
        return "PN" + (dspn.size() + 1);
    }
    
    public PhieuNhap getPhieuNhap(String mapn){
        for (PhieuNhap pn : dspn) {
            if (pn.getMaPN().equals(mapn)) {
                return pn;
            }
        }
        return null;
    }

    public ArrayList<PhieuNhap> getDspn() {
        return this.dspn;
    }

    public ArrayList<PhieuNhap> search(String type, String value, LocalDate _ngay1, LocalDate _ngay2, int _tong1, int _tong2) {
        ArrayList<PhieuNhap> result = new ArrayList<>();

        dspn.forEach((pn) -> {
            if (type.equals("Tất cả")) {
                if (pn.getMaPN().toLowerCase().contains(value.toLowerCase())
                        || pn.getMaNCC().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getMaNV()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getNgayNhap()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getGioNhap()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getTongTien()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(pn);
                }
            } else {
                switch (type) {
                    case "Mã phiếu nhập":
                        if (pn.getMaPN().toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Mã nhà cung cấp":
                        if (pn.getMaNCC().toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Mã nhân viên":
                        if (String.valueOf(pn.getMaNV()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Ngày nhập":
                        if (String.valueOf(pn.getNgayNhap()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Giờ nhập":
                        if (String.valueOf(pn.getGioNhap()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                    case "Tổng tiền":
                        if (String.valueOf(pn.getTongTien()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(pn);
                        }
                        break;
                }
            }

        });
        //Ngay lap, tong tien
        for (int i = result.size() - 1; i >= 0; i--) {
            PhieuNhap pn = result.get(i);
            LocalDate ngaynhap = pn.getNgayNhap();
            float tongtien = pn.getTongTien();

            Boolean ngayKhongThoa = (_ngay1 != null && ngaynhap.isBefore(_ngay1)) || (_ngay2 != null && ngaynhap.isAfter(_ngay2));
            Boolean tienKhongThoa = (_tong1 != -1 && tongtien < _tong1) || (_tong2 != -1 && tongtien > _tong2);

            if (ngayKhongThoa || tienKhongThoa) {
                result.remove(pn);
            }
        }

        return result;
    }

    public boolean add(PhieuNhap pn) {
        Boolean ok = DAO.add(pn);
        if (ok) {
            dspn.add(pn);
        }
        return ok;
    }

    public Boolean update(String maPN, String maNCC, String maNV, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {
        PhieuNhap pn = new PhieuNhap(maPN, maNCC, maNV, ngayNhap, gioNhap, tongTien);
        return update(pn);
    }

    public Boolean update(PhieuNhap pn) {
        Boolean success = DAO.update(pn);
        if (success) {
            for (PhieuNhap cthd : dspn) {
                if (cthd.getMaPN().equals(pn.getMaPN())) {
                    cthd = pn;
                }
            }
            return true;
        }
        return false;
    }

    public Boolean updateTongTien(String _mapn, float _tongTien) {
        Boolean success = DAO.updateTongTien(_mapn, _tongTien);
        if (success) {
            for (PhieuNhap pn : dspn) {
                if (pn.getMaPN().equals(_mapn)) {
                    pn.setTongTien(_tongTien);
                }
            }
            return true;
        }
        return false;
    }

    public boolean add(String maPN, String maNCC, String maNV, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {
        PhieuNhap pn = new PhieuNhap(maPN, maNCC, maNV, ngayNhap, gioNhap, tongTien);
        return add(pn);
    }

    public boolean delete(String ma) {
        Boolean ok = DAO.delete(ma);
        if (ok) {
            for (int i = (dspn.size() - 1); i >= 0; i--) {
                if (dspn.get(i).getMaPN().equals(ma)) {
                    dspn.remove(i);
                }
            }

        }
        return ok;
    }
}
