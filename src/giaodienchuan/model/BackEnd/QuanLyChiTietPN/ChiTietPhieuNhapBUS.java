/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyChiTietPN;

import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhapBUS {
    ChiTietPhieuNhapDAO DAO= new ChiTietPhieuNhapDAO();
    ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();
    
    public ChiTietPhieuNhapBUS()
    {
        dsctpn=DAO.readDB();
    }
     public ArrayList<ChiTietPhieuNhap> search(String value, String type) {
        ArrayList<ChiTietPhieuNhap> result = new ArrayList<>();

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
     public boolean add(String ma,String masp,Integer soluong,Float dongia)
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
}
     