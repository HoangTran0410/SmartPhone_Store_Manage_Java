/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyNCC;

;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */


public class QuanLyNhaCungCapDAO {

    ConnectionDB qlnccConnection;

//    public QuanLyNhaCungCapDAO(){
//        nhaCungCapDB.logIn("root","");
//    }
    public ArrayList<NhaCungCap> readDB() {
        ArrayList<NhaCungCap> dsncc = new ArrayList<>();
        qlnccConnection = new ConnectionDB();
        try {
            String qry = "SELECT * FROM nhacungcap";
            ResultSet r = qlnccConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String ma = r.getString(1);
                    String ten = r.getString(2);
                    String diachi = r.getString(3);
                    String sdt = r.getString(4);
                    String fax = r.getString(5);

                    dsncc.add(new NhaCungCap(ma, ten, diachi, sdt, fax));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");
        } finally {
            qlnccConnection.closeConnect();
        }
        return dsncc;
    }

    public ArrayList<NhaCungCap> search(String columnName, String value) {
        qlnccConnection = new ConnectionDB();
        ArrayList<NhaCungCap> dsncc = new ArrayList<>();

        try {
            String qry = "SELECT * FROM sanpham WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qlnccConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String mancc = r.getString(1);
                    String tenncc = r.getString(2);
                    String diachi = r.getString(3);
                    String sdt = r.getString(4);
                    String fax = r.getString(5);
                    dsncc.add(new NhaCungCap(mancc, tenncc, diachi, sdt, fax));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng sản phẩm");
        } finally {
            qlnccConnection.closeConnect();
        }

        return dsncc;
    }

    public Boolean add(NhaCungCap ncc) {
        qlnccConnection = new ConnectionDB();
        Boolean ok = qlnccConnection.sqlUpdate("INSERT INTO `nhacungcap` (`MaNCC`, `TenNCC`, `DiaChi`,`SDT`,`Fax`) VALUES ('"
                + ncc.getMaNCC() + "', '" 
                + ncc.getTenNCC() + "', '" 
                + ncc.getDiaChi() + "','" 
                + ncc.getSDT() + "','" 
                + ncc.getFax() + "');");

        qlnccConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String mancc) {
        qlnccConnection = new ConnectionDB();
        Boolean ok = qlnccConnection.sqlUpdate("DELETE FROM `nhacungcap` WHERE `nhacungcap`.`MaNCC` = '" + mancc + "'");
        qlnccConnection.closeConnect();
        return ok;
    }

    public Boolean update(String ma, String ten, String diachi, String sdt, String fax) {
        qlnccConnection = new ConnectionDB();
        Boolean ok = qlnccConnection.sqlUpdate("Update NhaCungCap Set MaNCC='" + ma + "',TenNCC='" + ten + "',DiaChi='" + diachi + "',SDT='" + sdt + "',Fax='" + fax + "' where MaNCC='" + ma + "'");
        qlnccConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlnccConnection.closeConnect();
    }

}
