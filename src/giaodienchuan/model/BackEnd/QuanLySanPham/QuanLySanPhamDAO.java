package giaodienchuan.model.BackEnd.QuanLySanPham;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLySanPhamDAO {

    ConnectionDB qlspConnection;

    public QuanLySanPhamDAO() {

    }

    public ArrayList<SanPham> readDB() {
        qlspConnection = new ConnectionDB();
        ArrayList<SanPham> dssp = new ArrayList<>();
        try {
            String qry = "SELECT * FROM sanpham";
            ResultSet r = qlspConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String masp = r.getString(1);
                    String loaisp = r.getString(2);
                    String tensp = r.getString(3);
                    float dongia = r.getFloat(4);
                    int soluong = r.getInt(5);
                    dssp.add(new SanPham(masp, loaisp, tensp, dongia, soluong));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng sản phẩm");
        } finally {
            qlspConnection.closeConnect();
        }
        return dssp;
    }

    public ArrayList<SanPham> search(String columnName, String value) {
        qlspConnection = new ConnectionDB();
        ArrayList<SanPham> dssp = new ArrayList<>();

        try {
            String qry = "SELECT * FROM sanpham WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qlspConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String masp = r.getString(1);
                    String loaisp = r.getString(2);
                    String tensp = r.getString(3);
                    float dongia = r.getFloat(4);
                    int soluong = r.getInt(5);
                    dssp.add(new SanPham(masp, loaisp, tensp, dongia, soluong));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng sản phẩm");
        } finally {
            qlspConnection.closeConnect();
        }

        return dssp;
    }

    public Boolean add(SanPham sp) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("INSERT INTO `sanpham` (`MaSP`, `MaLSP`, `TenSP`, `DonGia`, `SoLuong`) VALUES ('"
                + sp.getMaSP() + "', '" + sp.getMaLSP() + "', '" + sp.getTenSP()
                + "', '" + sp.getDonGia() + "', '" + sp.getSoLuong() + "');");
        qlspConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String masp) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("DELETE FROM `sanpham` WHERE `sanpham`.`MaSP` = '" + masp + "'");
        qlspConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaSP, String MaLSP, String TenSP, float DonGia, int SoLuong) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("Update SanPham Set MaLSP='" + MaLSP + "',TenSP='" + TenSP
                + "',DonGia='" + DonGia + "',SoLuong='" + SoLuong + "' where MaSP='" + MaSP + "'");
        qlspConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlspConnection.closeConnect();
    }
}
