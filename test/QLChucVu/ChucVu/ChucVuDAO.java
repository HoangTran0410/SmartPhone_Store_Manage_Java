package giaodienchuan.model.FrontEnd.Test.QLChucVu.ChucVu;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChucVuDAO {

    ConnectionDB cvConnection;

    public ChucVuDAO() {

    }

    public ArrayList<ChucVu> readDB() {
        cvConnection = new ConnectionDB();
        ArrayList<ChucVu> dscv = new ArrayList<>();
        try {
            String qry = "SELECT * FROM chucvu";
            ResultSet r = cvConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String macv = r.getString(1);
                    String tencv = r.getString(2);

                    dscv.add(new ChucVu(macv, tencv));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi đọc dữ liệu bảng chức vụ");
        } finally {
            cvConnection.closeConnect();
        }
        return dscv;
    }

    public ArrayList<ChucVu> search(String columnName, String value) {
        cvConnection = new ConnectionDB();
        ArrayList<ChucVu> dscv = new ArrayList<>();

        try {
            String qry = "SELECT * FROM chucvu WHERE " + columnName + " LIKE '%" + value + "%'";
            ResultSet r = cvConnection.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String macv = r.getString(1);
                    String tencv = r.getString(2);
                    dscv.add(new ChucVu(macv, tencv));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "-- ERROR! Lỗi tìm dữ liệu " + columnName + " = " + value + " bảng chức vụ");
        } finally {
            cvConnection.closeConnect();
        }

        return dscv;
    }

    public Boolean add(ChucVu cv) {
        cvConnection = new ConnectionDB();
        Boolean ok = cvConnection.sqlUpdate("INSERT INTO `chucvu` (`MaCV`, `TenCV`) VALUES ('"
                + cv.getMaCV() + "', '" + cv.getTenCV() + "');");
        cvConnection.closeConnect();
        return ok;
    }

    public Boolean delete(String macv) {
        cvConnection = new ConnectionDB();
        Boolean ok = cvConnection.sqlUpdate("DELETE FROM `chucvu` WHERE `chucvu`.`MaCV` = '" + macv + "'");
        cvConnection.closeConnect();
        return ok;
    }

    public Boolean update(String MaCV, String TenCV) {
        cvConnection = new ConnectionDB();
        Boolean ok = cvConnection.sqlUpdate("Update ChucVu Set TenCV='" + TenCV + "' WHERE MaCV='" + MaCV + "'");
        cvConnection.closeConnect();
        return ok;
    }

    public void close() {
        cvConnection.closeConnect();
    }
}
