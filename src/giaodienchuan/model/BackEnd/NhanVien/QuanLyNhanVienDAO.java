package giaodienchuan.model.BackEnd.NhanVien;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyNhanVienDAO {

    ConnectionDB qlnvConnection;

    public QuanLyNhanVienDAO() {

    }

    public ArrayList<NhanVien> readDB() {
        ArrayList<NhanVien> dsnv = new ArrayList<>();
        qlnvConnection = new ConnectionDB();
        try {
            String qry = "SELECT * FROM nhanvien";
            ResultSet r = qlnvConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String manv = r.getString(1);
                    String macv = r.getString(2);
                    String tennv = r.getString(3);
                    String ngaysinh = r.getString(4);
                    String diachi = r.getString(5);
                    String sdt = r.getString(6);
                    dsnv.add(new NhanVien(manv, macv, tennv, ngaysinh, diachi, sdt));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng nhân viên");
        } finally {
            qlnvConnection.closeConnect();
        }
        return dsnv;
    }

    public ArrayList<NhanVien> search(String columnName, String value) {
        qlnvConnection = new ConnectionDB();
        ArrayList<NhanVien> dsnv = new ArrayList<>();

        try {
            String qry = "SELECT * FROM nhanvien WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qlnvConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String manv = r.getString(1);
                    String macv = r.getString(2);
                    String tennv = r.getString(3);
                    String ngaysinh = r.getString(4);
                    String diachi = r.getString(5);
                    String sdt = r.getString(6);
                    dsnv.add(new NhanVien(manv, macv, tennv, ngaysinh, diachi, sdt));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng nhân viên");
        } finally {
            qlnvConnection.closeConnect();
        }

        return dsnv;
    }

    public Boolean add(NhanVien nv) {
        qlnvConnection = new ConnectionDB();
        Boolean ok = qlnvConnection.sqlUpdate("INSERT INTO `nhanvien` (`MaNV`,`MaCV`, `TenNV`, `NgaySinh`, `DiaChi`, `SDT`) VALUES ('"
                + nv.getMaNV() + "', '" + nv.getMaCV() + "', '" + nv.getTenNV()
                + "', '" + nv.getNgaySinh() + "', '" + nv.getDiaChi() + "', '" + nv.getSDT() + "');");
        qlnvConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String manv) {
        qlnvConnection = new ConnectionDB();
        Boolean ok = qlnvConnection.sqlUpdate("DELETE FROM `nhanvien` WHERE `nhanvien`.`MaNV` = '" + manv + "'");
        qlnvConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaNV, String MaCV, String TenNV, String NgaySinh, String DiaChi, String SDT) {
        qlnvConnection = new ConnectionDB();
        Boolean ok = qlnvConnection.sqlUpdate("Update NhanVien Set MaCV='" + MaCV + "',TenNV='" + TenNV
                + "',NgaySinh='" + NgaySinh + "',DiaChi='" + DiaChi + "',SDT='" + SDT + "' where MaNV='" + MaNV + "'");
        qlnvConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlnvConnection.closeConnect();
    }
}
