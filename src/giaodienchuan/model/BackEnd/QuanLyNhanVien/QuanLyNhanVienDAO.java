package giaodienchuan.model.BackEnd.QuanLyNhanVien;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
                    String manv = r.getString("MaNV");
                    String tennv = r.getString("TenNV");
                    LocalDate ngaysinh = r.getDate("NgaySinh").toLocalDate();
                    String diachi = r.getString("DiaChi");
                    String sdt = r.getString("SDT");
                    int tt = r.getInt("TrangThai");
                    dsnv.add(new NhanVien(manv, tennv, ngaysinh, diachi, sdt, tt));
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
                    String manv = r.getString("MaNV");
                    String tennv = r.getString("TenNV");
                    LocalDate ngaysinh = r.getDate("NgaySinh").toLocalDate();
                    String diachi = r.getString("DiaChi");
                    String sdt = r.getString("SDT");
                    int tt = r.getInt("TrangThai");
                    dsnv.add(new NhanVien(manv, tennv, ngaysinh, diachi, sdt, tt));
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
        Boolean ok = qlnvConnection.sqlUpdate("INSERT INTO `nhanvien` (`MaNV`, `TenNV`, `NgaySinh`, `DiaChi`, `SDT`, `TrangThai`) VALUES ('"
                + nv.getMaNV() + "', '"
                + nv.getTenNV() + "', '" 
                + nv.getNgaySinh() + "', '" 
                + nv.getDiaChi() + "', '" 
                + nv.getSDT() + "', '" 
                + nv.getTrangThai() + "');");
        qlnvConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String manv) {
        qlnvConnection = new ConnectionDB();
        Boolean ok = qlnvConnection.sqlUpdate("DELETE FROM `nhanvien` WHERE `nhanvien`.`MaNV` = '" + manv + "'");
        qlnvConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaNV, String TenNV, LocalDate NgaySinh, String DiaChi, String SDT, int trangthai) {
        qlnvConnection = new ConnectionDB();
        Boolean ok = qlnvConnection.sqlUpdate("Update NhanVien Set "
                + "TenNV='" + TenNV
                + "',NgaySinh='" + NgaySinh 
                + "',DiaChi='" + DiaChi 
                + "',SDT='" + SDT 
                + "',TrangThai='" + trangthai 
                + "' where MaNV='" + MaNV + "'");
        qlnvConnection.closeConnect();
        return ok;
    }
    
    public Boolean updateTrangThai(String manv, int trangthai) {
        qlnvConnection = new ConnectionDB();
        Boolean ok = qlnvConnection.sqlUpdate("Update NhanVien Set "
                + "TrangThai='" + trangthai 
                + "' where MaNV='" + manv + "'");
        qlnvConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlnvConnection.closeConnect();
    }
}
