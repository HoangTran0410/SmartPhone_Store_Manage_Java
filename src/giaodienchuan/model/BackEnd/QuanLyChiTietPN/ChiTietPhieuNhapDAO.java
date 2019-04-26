/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyChiTietPN;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhapDAO {

    ConnectionDB qlctpnConnection;

    public ArrayList<ChiTietPhieuNhap> readDB() {
        ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();
        qlctpnConnection = new ConnectionDB();
        try {

            String query = "SELECT * FROM chitietphieunhap";
            ResultSet r = qlctpnConnection.sqlQuery(query);
            if (r != null) {
                while (r.next()) {
                    String ma = r.getString(1);
                    String maSP = r.getString(2);
                    Integer soLuong = r.getInt(3);
                    Float donGia = r.getFloat(4);

                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(ma, maSP, soLuong, donGia);
                    dsctpn.add(ctpn);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");

        } finally {
            qlctpnConnection.closeConnect();
        }
        return dsctpn;

    }

    public ArrayList<ChiTietPhieuNhap> search(String columName, String value) {
        ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();
        qlctpnConnection = new ConnectionDB();
        try {

            String query = "SELECT * FROM chitietphieunhap WHERE" + columName + "LIKE '%" + value + "%'";
            ResultSet r = qlctpnConnection.sqlQuery(query);
            if (r != null) {
                while (r.next()) {
                    String ma = r.getString(1);
                    String maSP = r.getString(2);
                    Integer soLuong = r.getInt(3);
                    Float donGia = r.getFloat(4);

                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(ma, maSP, soLuong, donGia);
                    dsctpn.add(ctpn);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");

        } finally {
            qlctpnConnection.closeConnect();
        }
        return dsctpn;

    }

    public boolean add(ChiTietPhieuNhap ctpn) {
        qlctpnConnection = new ConnectionDB();
        Boolean ok = qlctpnConnection.sqlUpdate("INSERT INTO `chitietphieunhap`(`MaPN`,`MaSP`,`SoLuong`,`DonGia`) VALUE('"
                + ctpn.getMa() + "', '" + ctpn.getMaSP() + "','" + ctpn.getSoLuong() + "','" + ctpn.getDonGia() + "')");
        qlctpnConnection.closeConnect();
        return ok;

    }

    public boolean delete(String mactpn) // đây là hàm xóa tất cả chi tiết thuộc phiếu có mã mactpn mẹ rồi
    {
        qlctpnConnection = new ConnectionDB();
        Boolean ok = qlctpnConnection.sqlUpdate("DELETE *FROM `chitietphieunhap` WHERE `chitietphieunhap`.`MaPN`='" + mactpn + "'");
        qlctpnConnection.closeConnect();
        return ok;
    }

    public Boolean deleteAll(String _mapn) { // 2 câu query éo khác gì nhau
        qlctpnConnection = new ConnectionDB();
        Boolean success = qlctpnConnection.sqlUpdate("DELETE FROM chitietphieunhap WHERE MaPN='" + _mapn + "';");
        qlctpnConnection.closeConnect();
        return success;
    }

    public Boolean delete(String _mapn, String _masp) {// đây mới đúng là xóa 1 chi tiết
        qlctpnConnection = new ConnectionDB();
        Boolean success = qlctpnConnection.sqlUpdate("DELETE FROM chitietphieunhap WHERE MaPN='" + _mapn + "' AND MaSP='" + _masp + "';");
        qlctpnConnection.closeConnect();
        return success;
    }

    public boolean update(String mapn, String masp, int soluong, float dongia) {// thua :)) hom qua t update no bi mat
        qlctpnConnection = new ConnectionDB();
        Boolean ok = qlctpnConnection.sqlUpdate("UPDATE `chitietphieunhap` SET MaPN='" + mapn + "',MaSP='" + masp + "',SoLuong='" + soluong + "',DonGia='" + dongia +"' WHERE MaPN='"+mapn+"'AND MaSP='"+masp+"';");
        qlctpnConnection.closeConnect();
        return ok;
    }
}
