package giaodienchuan.model.BackEnd.QuanLyQuyen;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyQuyenDAO {

    ConnectionDB qlqConnection;

    public QuanLyQuyenDAO() {

    }

    public ArrayList<Quyen> readDB() {
        qlqConnection = new ConnectionDB();
        ArrayList<Quyen> dsq = new ArrayList<>();
        try {
            String qry = "SELECT * FROM sanpham";
            ResultSet r = qlqConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String maq = r.getString("MaQuyen");
                    String chitietq = r.getString("ChiTietQuyen");
                    
                    dsq.add(new Quyen(maq, chitietq));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng phân quyền");
        } finally {
            qlqConnection.closeConnect();
        }
        return dsq;
    }

    public ArrayList<Quyen> search(String columnName, String value) {
        qlqConnection = new ConnectionDB();
        ArrayList<Quyen> dsq = new ArrayList<>();

        try {
            String qry = "SELECT * FROM phanquyen WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qlqConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String maq = r.getString("MaQuyen");
                    String chitietq = r.getString("ChiTietQuyen");
                    
                    dsq.add(new Quyen(maq, chitietq));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng phân quyền");
        } finally {
            qlqConnection.closeConnect();
        }

        return dsq;
    }

    public Boolean add(Quyen q) {
        qlqConnection = new ConnectionDB();
        Boolean ok = qlqConnection.sqlUpdate("INSERT INTO `phanquyen` (`MaQuyen`, `ChiTietQuyen`) VALUES ('"
                + q.getMaQuyen()+ "', '" + q.getChiTietQuyen()+ "');");
        qlqConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String maq) {
        qlqConnection = new ConnectionDB();
        Boolean ok = qlqConnection.sqlUpdate("DELETE FROM `phanquyen` WHERE `phanquyen`.`MaQuyen` = '" + maq + "'");
        qlqConnection.closeConnect();
        return ok;
    }

    public Boolean update(String maq, String chitietquyen) {
        qlqConnection = new ConnectionDB();
        Boolean ok = qlqConnection.sqlUpdate("Update phanquyen Set ChiTietQuyen='" + chitietquyen + "' where MaQuyen='" + maq + "'");
        qlqConnection.closeConnect();
        return ok;
    }

    public void close() {
        qlqConnection.closeConnect();
    }
}