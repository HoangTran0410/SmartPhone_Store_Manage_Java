package giaodienchuan.model.BackEnd.QuanLyKhachHang;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyKhachHangDAO {

    ConnectionDB qlkhConnection;

    public QuanLyKhachHangDAO() {

    }

    public ArrayList<KhachHang> readDB() {
        qlkhConnection = new ConnectionDB();
        ArrayList<KhachHang> dskh = new ArrayList<>();
        try {
            String qry = "SELECT * FROM khachhang";
            ResultSet r = qlkhConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String makh = r.getString(1);
                    String tenkh = r.getString(2);
                    String diachi = r.getString(3);
                    String sdt = r.getString(4);
                    dskh.add(new KhachHang(makh, tenkh, diachi, sdt));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng khách hàng");
        } finally {
            qlkhConnection.closeConnect();
        }
        return dskh;
    }

    public ArrayList<KhachHang> search(String columnName, String value) {
        qlkhConnection = new ConnectionDB();
        ArrayList<KhachHang> dskh = new ArrayList<>();

        try {
            String qry = "SELECT * FROM khachhang WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qlkhConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String makh = r.getString(1);
                    String tenkh = r.getString(2);
                    String diachi = r.getString(3);
                    String sdt = r.getString(4);
                    dskh.add(new KhachHang(makh, tenkh, diachi, sdt));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng khách hàng");
        } finally {
            qlkhConnection.closeConnect();
        }

        return dskh;
    }

    public Boolean add(KhachHang kh) {
        qlkhConnection = new ConnectionDB();
        Boolean ok = qlkhConnection.sqlUpdate("INSERT INTO `khachhang` (`MaKH`, `TenKH`, `DiaChi`, `SDT`) VALUES ('"
                + kh.getMaKH() + "', '" + kh.getTenKH() + "', '" + kh.getDiaChi() + "','" + kh.getSDT() + "');");
        qlkhConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String makh) {
        qlkhConnection = new ConnectionDB();
        Boolean ok = qlkhConnection.sqlUpdate("DELETE FROM `khachhang` WHERE `khachhang`.`MaKH` = '" + makh + "'");
        qlkhConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaKH, String TenKH, String DiaChi, String SDT) {
        qlkhConnection = new ConnectionDB();
        Boolean ok = qlkhConnection.sqlUpdate("Update KhachHang Set TenKH='" + TenKH
                + "', DiaChi='" + DiaChi + "', SDT='" + SDT + "' where MaKH='" + MaKH + "'");
        qlkhConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlkhConnection.closeConnect();
    }
}
