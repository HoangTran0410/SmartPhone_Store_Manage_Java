/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyPN;

import giaodienchuan.model.BackEnd.QuanLyPN.PhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPN.QuanLyPhieuNhapDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class QuanLyPhieuNhapBUS {
    QuanLyPhieuNhapDAO DAO= new QuanLyPhieuNhapDAO();
    ArrayList<PhieuNhap> dspn = new ArrayList<>();
    
    public QuanLyPhieuNhapBUS()
    {
        dspn=DAO.readDB();
    }
    public void readDB()
    {
        dspn=DAO.readDB();
    }
    public ArrayList<PhieuNhap> getDspn() {
        return this.dspn;
    }
     public ArrayList<PhieuNhap> search(String value, String type) {
        // Phương pháp tìm từ database
//        QuanLySanPhamDAO qlspDB = new QuanLySanPhamDAO();
//        dssp = qlspDB.search(columnName, value);
//        qlspDB.close();

        // phương pháp tìm từ arraylist
        ArrayList<PhieuNhap> result = new ArrayList<>();

        dspn.forEach((pn) -> {
            if (type.equals("Tất cả")) {
                if (pn.getMaPN().toLowerCase().contains(value.toLowerCase())
                        || pn.getMaNCC().toLowerCase().contains(value.toLowerCase())
                        
                        || String.valueOf(pn.getMaNV()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getNgayNhap()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getGioNhap()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(pn.getTongTien()).toLowerCase().contains(value.toLowerCase()))
                {
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

        return result;
    }
     public boolean add(PhieuNhap pn)
     {
         Boolean ok=DAO.add(pn);
         if(ok)
         {
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
     public Boolean updateTongTien(String _mapn, Float _tongTien) {
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
     public boolean add(String maPN,String maNCC,String maNV,LocalDate ngayNhap,LocalTime gioNhap,Float tongTien)
     {
         PhieuNhap pn= new PhieuNhap(maPN,maNCC,maNV,ngayNhap,gioNhap,tongTien);
         return add(pn);
     }
     public boolean delete(String ma)
     {    Boolean ok=DAO.delete(ma);
          if(ok){
          for(int i= (dspn.size()-1);i>=0;i--)
                 if(dspn.get(i).getMaPN().equals(ma)) dspn.remove(i);
          
            
        }
          return ok;
     }
}
