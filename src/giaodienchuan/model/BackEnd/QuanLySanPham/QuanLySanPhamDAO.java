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
                    String masp = r.getString("MaSP");
                    String loaisp = r.getString("MaLSP");
                    String tensp = r.getString("TenSP");
                    float dongia = r.getFloat("DonGia");
                    int soluong = r.getInt("SoLuong");
                    String url = r.getString("HinhAnh");
                    int trangthai = r.getInt("TrangThai");
                    dssp.add(new SanPham(masp, loaisp, tensp, dongia, soluong, url, trangthai));
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
                    String masp = r.getString("MaSP");
                    String loaisp = r.getString("MaLSP");
                    String tensp = r.getString("TenSP");
                    float dongia = r.getFloat("DonGia");
                    int soluong = r.getInt("SoLuong");
                    String url = r.getString("HinhAnh");
                    int trangthai = r.getInt("TrangThai");
                    dssp.add(new SanPham(masp, loaisp, tensp, dongia, soluong, url, trangthai));
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
        Boolean ok = qlspConnection.sqlUpdate("INSERT INTO `sanpham` (`MaSP`, `MaLSP`, `TenSP`, `DonGia`, `SoLuong`, `HinhAnh`, `TrangThai`) VALUES ('"
                + sp.getMaSP() + "', '"
                + sp.getMaLSP() + "', '"
                + sp.getTenSP() + "', '"
                + sp.getDonGia() + "', '"
                + sp.getSoLuong() + "', '"
                + sp.getFileNameHinhAnh() + "', '"
                + sp.getTrangThai()+ "');");
        qlspConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String masp) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("DELETE FROM `sanpham` WHERE `sanpham`.`MaSP` = '" + masp + "'");
        qlspConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaSP, String MaLSP, String TenSP, float DonGia, int SoLuong, String filename, int trangthai) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("Update SanPham Set "
                + "MaLSP='" + MaLSP
                + "',TenSP='" + TenSP
                + "',DonGia='" + DonGia
                + "',SoLuong='" + SoLuong
                + "',HinhAnh='" + filename
                + "',TrangThai='" + trangthai
                + "' where MaSP='" + MaSP + "'");
        qlspConnection.closeConnect();
        return ok;
    }

    public Boolean updateSoLuong(String MaSP, int SoLuong) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("Update SanPham Set "
                + "SoLuong='" + SoLuong
                + "' where MaSP='" + MaSP + "'");
        qlspConnection.closeConnect();
        return ok;
    }
    
    public Boolean updateTrangThai(String MaSP, int trangthai) {
        qlspConnection = new ConnectionDB();
        Boolean ok = qlspConnection.sqlUpdate("Update SanPham Set "
                + "TrangThai='" + trangthai
                + "' where MaSP='" + MaSP + "'");
        qlspConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlspConnection.closeConnect();
    }
}
