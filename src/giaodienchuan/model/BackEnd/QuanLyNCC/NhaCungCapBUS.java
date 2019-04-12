/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyNCC;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class NhaCungCapBUS {

    public ArrayList<NhaCungCap> dsncc = new ArrayList<>();

    public void show() {
        dsncc.forEach((sv) -> {
            System.out.print(sv.getMaNCC() + " ");
            System.out.print(sv.getTenNCC() + " ");
            System.out.println(sv.getDiaChi() + " ");
            System.out.println(sv.getSDT() + " ");
            System.out.println(sv.getFax());
        });
    }

    public void readDB() {
        NhaCungCapDAO DAO = new NhaCungCapDAO();
        dsncc = DAO.readDB();
        DAO.close();
    }

    public Boolean add(NhaCungCap ncc) {
        NhaCungCapDAO DAO = new NhaCungCapDAO();
        Boolean ok = DAO.add(ncc);
        DAO.close();

        if (ok) {
            dsncc.add(ncc);
        }
        return ok;
    }

    public Boolean add(String ma, String ten, String diachi, String sdt, String fax) {
        NhaCungCap sv = new NhaCungCap(ma, ten, diachi, sdt, fax);
        return add(sv);
    }

    public Boolean delete(String mancc) {
        NhaCungCapDAO DAO = new NhaCungCapDAO();
        Boolean ok = DAO.delete(mancc);
        DAO.close();

        if (ok) {
            for (int i = (dsncc.size() - 1); i >= 0; i--) {
                if (dsncc.get(i).getMaNCC().equals(mancc)) {
                    dsncc.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String mancc, String tenncc, String diachi, String sdt, String fax) {
        NhaCungCapDAO DAO = new NhaCungCapDAO();
        Boolean ok = DAO.update(mancc, tenncc, diachi, sdt, fax);
        DAO.close();

        if (ok) {
            dsncc.forEach((ncc) -> {
                if (ncc.getMaNCC().equals(mancc)) {
                    ncc.setTenNCC(tenncc);
                    ncc.setDiaChi(diachi);
                    ncc.setSDT(sdt);
                    ncc.setFax(fax);
                }
            });
        }

        return ok;
    }

}
