/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyChiTietPhieuNhap;

import giaodienchuan.model.BackEnd.QuanLyChiTietPhieuNhap.ChiTietPhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.QuanLyPhieuNhapDAO;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class QuanLyChiTietPhieuNhapBUS {
    QuanLyChiTietPhieuNhapDAO DAO= new QuanLyChiTietPhieuNhapDAO();
    QuanLyPhieuNhapDAO qlpnDAO=new QuanLyPhieuNhapDAO();
    ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();
    
    public QuanLyChiTietPhieuNhapBUS()
    {
        dsctpn=DAO.readDB();
    }
    
    public ArrayList<ChiTietPhieuNhap> getDsctpn() {
        return this.dsctpn;
    }
    public void readDB() {
        dsctpn = DAO.readDB();
    }
     public ArrayList<ChiTietPhieuNhap> search(String type,String value) {
        ArrayList<ChiTietPhieuNhap> result = new ArrayList<>();
        readDB();
        dsctpn.forEach((ctpn) -> {
            if (type.equals("Tất cả")) {
                if (ctpn.getMa().toLowerCase().contains(value.toLowerCase())
                        || ctpn.getMaSP().toLowerCase().contains(value.toLowerCase())                       
                        || String.valueOf(ctpn.getDonGia()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ctpn.getSoLuong()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(ctpn);
                }
            } else {
                switch (type) {
                    case "Mã phiếu nhập":
                        if (ctpn.getMa().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                    case "Mã sản phẩm":
                        if (ctpn.getMaSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                    case "Đơn giá":
                        if (String.valueOf(ctpn.getDonGia()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                    case "Số lượng":
                        if (String.valueOf(ctpn.getSoLuong()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ctpn);
                        }
                        break;
                }
            }

        });

        return result;
    }
     public boolean add(ChiTietPhieuNhap ctpn)
     {
         Boolean ok=DAO.add(ctpn);
         if(ok)
         {
            dsctpn.add(ctpn);
         }
         return ok;
     }
     public Boolean update(ChiTietPhieuNhap ctpn) {
        Boolean ok = DAO.update(ctpn);
        if (ok) {
            for (ChiTietPhieuNhap pn : dsctpn) {
                if (pn.getMa().equals(ctpn.getMa())) {
                    pn = ctpn;
                }
            }
            updateTongTien(ctpn.getMa());
        }

        return ok;
    }
      private Boolean updateTongTien(String _mapn) {
        float tong = 0;
        for (ChiTietPhieuNhap ct : dsctpn) {
            if (ct.getMa().equals(_mapn)) {
                tong += ct.getSoLuong() * ct.getDonGia();
            }
        }
        Boolean ok = qlpnDAO.updateTongTien(_mapn, tong);
        return ok;
    }
     public boolean add(String ma,String masp,int soluong,float dongia)
     {
         ChiTietPhieuNhap ctpn= new ChiTietPhieuNhap(ma,masp,soluong,dongia);
         return add(ctpn);
     }
     public boolean delete(String ma)
     {    Boolean ok=DAO.delete(ma);
          if(ok){
          for(int i= (dsctpn.size()-1);i>=0;i--)
                 if(dsctpn.get(i).getMa().equals(ma)) dsctpn.remove(i);
          
            
        }
          return ok;
     }
     public Boolean deleteAll(String _maPhieuNhap) {
        Boolean success = DAO.deleteAll(_maPhieuNhap);
        if (success) {
            for (ChiTietPhieuNhap ctpn : dsctpn) {
                if (ctpn.getMa().equals(_maPhieuNhap)) {
                    dsctpn.remove(ctpn);
                }
            }
            updateTongTien(_maPhieuNhap);
            return true;
        }
        return false;
    }
     public Boolean delete(String _maPhieuNhap, String _maSanPham) {
        Boolean success = DAO.delete(_maPhieuNhap, _maSanPham);
        if (success) {
            for (ChiTietPhieuNhap ctpn : dsctpn) {
                if (ctpn.getMa().equals(_maPhieuNhap) && ctpn.getMaSP().equals(_maSanPham)) {
                    dsctpn.remove(ctpn);
                    updateTongTien(_maPhieuNhap);
                    return true;
                }
            }
        }
        return false;
    }
}
     