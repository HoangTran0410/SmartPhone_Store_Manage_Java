/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyNCC;;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */
public class NhaCungCapDAO {             
    ConnectionDB nhaCungCapDB= new ConnectionDB();
    
//    public NhaCungCapDAO(){
//        nhaCungCapDB.logIn("root","");
//    }
    public ArrayList<NhaCungCap> readDB()
    {
        ArrayList<NhaCungCap> dsncc=new ArrayList<>();
        try {
            String qry="SELECT * FROM nhacungcap";
            ResultSet r = nhaCungCapDB.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String ma = r.getString(1);
                    String ten = r.getString(2);
                    String diachi = r.getString(3);
                    String sdt = r.getString(4);
                    String fax = r.getString(5);
                    
                    dsncc.add(new NhaCungCap(ma,ten,diachi,sdt,fax));
                }
            }
        }
        
         catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");
        }
        return dsncc;
    }
    public Boolean add(NhaCungCap ncc) {
        Boolean ok = nhaCungCapDB.sqlUpdate("INSERT INTO `nhacungcap` (`MaNCC`, `TenNCC`, `DiaChi`,`SDT`,`Fax`) VALUES ('"
                + ncc.getMaNCC()+ "', '" + ncc.getTenNCC()+ "', '" + ncc.getDiaChi()+"','"+ncc.getSDT()+ "','"+ncc.getFax()+"');");
        return ok;
    }

    public Boolean delete(String mancc) {
        Boolean ok = nhaCungCapDB.sqlUpdate("DELETE FROM `nhacungcap` WHERE `nhacungcap`.`MaNCC` = '" + mancc + "'");
        return ok;
    }

    public Boolean update(String ma, String ten, String diachi,String sdt,String fax) {
        Boolean ok = nhaCungCapDB.sqlUpdate("Update NhaCungCap Set MaNCC='" + ma + "',TenNCC='" + ten + "',DiaChi='" +diachi + "',SDT='" +sdt+ "',Fax='" +fax + "' where MaNCC='" + ma + "'");
        return ok;
    }

    public void close() {
        nhaCungCapDB.closeConnect();
    }


    
}
