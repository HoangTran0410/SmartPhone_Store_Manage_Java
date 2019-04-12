package giaodienchuan.model.BackEnd.QuanLyLoaiSanPham;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyLoaiSanPhamDAO {

    ConnectionDB qllspConnection;

    public QuanLyLoaiSanPhamDAO() {

    }

    public ArrayList<LoaiSanPham> readDB() {
        qllspConnection = new ConnectionDB();
        ArrayList<LoaiSanPham> dslsp = new ArrayList<>();
        try {
            String qry = "SELECT * FROM loaisanpham";
            ResultSet r = qllspConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String malsp = r.getString(1);
                    String tenlsp = r.getString(2);
                    String mota = r.getString(3);

                    dslsp.add(new LoaiSanPham(malsp, tenlsp, mota));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng loại sản phẩm");
        } finally {
            qllspConnection.closeConnect();
        }
        return dslsp;
    }

    public ArrayList<LoaiSanPham> search(String columnName, String value) {
        qllspConnection = new ConnectionDB();
        ArrayList<LoaiSanPham> dslsp = new ArrayList<>();

        try {
            String qry = "SELECT * FROM loaisanpham WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = qllspConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String malsp = r.getString(1);
                    String tenlsp = r.getString(2);
                    String mota = r.getString(3);

                    dslsp.add(new LoaiSanPham(malsp, tenlsp, mota));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng loại sản phẩm");
        } finally {
            qllspConnection.closeConnect();
        }

        return dslsp;
    }

    public Boolean add(LoaiSanPham lsp) {
        qllspConnection = new ConnectionDB();
        Boolean ok = qllspConnection.sqlUpdate("INSERT INTO `loaisanpham` (`MaLSP`, `TenLSP`, `MoTa`) VALUES ('"
                + lsp.getMaLSP() + "', '" + lsp.getTenLSP() + "', '" + lsp.getMoTa()+ "');");
        qllspConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String malsp) {
        qllspConnection = new ConnectionDB();
        Boolean ok = qllspConnection.sqlUpdate("DELETE FROM `loaisanpham` WHERE `loaisanpham`.`MaLSP` = '" + malsp + "'");
        qllspConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaLSP, String TenLSP, String Mota) {
        qllspConnection = new ConnectionDB();
        Boolean ok = qllspConnection.sqlUpdate("Update loaisanpham Set TenLSP='" + TenLSP + "', MoTa='" + Mota + "' where MaLSP='" + MaLSP + "'");
        qllspConnection.closeConnect();
        return ok;
    }

    public void close() {
        qllspConnection.closeConnect();
    }
}
