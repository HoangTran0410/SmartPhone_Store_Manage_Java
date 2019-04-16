package giaodienchuan.model.FrontEnd.Test.QLSV;

import java.util.ArrayList;

public class QuanLySinhVienBUS {
    public ArrayList<SinhVien> dssv = new ArrayList<>();
    
    public QuanLySinhVienBUS() {
        
    }
    
    public void showConsole() {
        dssv.forEach((sv) -> {
            System.out.print(sv.getMssv() + " ");
            System.out.print(sv.getHo() + " ");
            System.out.println(sv.getTen());
        });
    }
    
    public void readDB() {
        QuanLySinhVienDAO qlsvDB = new QuanLySinhVienDAO();
        dssv = qlsvDB.readDB();
        qlsvDB.close();
    }
    
    public Boolean add(SinhVien sv) {
        QuanLySinhVienDAO qlsvDB = new QuanLySinhVienDAO();
        Boolean ok = qlsvDB.add(sv);
        qlsvDB.close();
        
        if (ok) {
            dssv.add(sv);
        }
        return ok;
    }
    
    public Boolean add(String mssv, String ho, String ten) {
        SinhVien sv = new SinhVien(mssv, ho, ten);
        return add(sv);
    }
    
    public Boolean delete(String mssv) {
        QuanLySinhVienDAO qlsvDB = new QuanLySinhVienDAO();
        Boolean ok = qlsvDB.delete(mssv);
        qlsvDB.close();
        
        if (ok) {
            for(int i = (dssv.size() - 1); i >= 0; i--) {
                if(dssv.get(i).getMssv().equals(mssv)) {
                    dssv.remove(i);
                } 
            }
        }
        return ok;
    }
    
    public Boolean update(String mssv, String ho, String ten) {
        QuanLySinhVienDAO qlsvDB = new QuanLySinhVienDAO();
        Boolean ok = qlsvDB.update(mssv, ho, ten);
        qlsvDB.close();
        
        if(ok) {
            dssv.forEach((sv) -> {
                if(sv.getMssv().equals(mssv)) {
                    sv.setHo(ho);
                    sv.setTen(ten);
                }
            });
        }
        
        return ok;
    }
}
