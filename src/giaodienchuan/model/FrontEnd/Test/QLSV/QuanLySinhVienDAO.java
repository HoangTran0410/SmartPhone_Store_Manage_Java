package giaodienchuan.model.FrontEnd.Test.QLSV;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLySinhVienDAO {

    ConnectionDB qlsvDB = new ConnectionDB("qlsv");

    public QuanLySinhVienDAO() {
        qlsvDB.logIn("root", "");
    }

    public ArrayList<SinhVien> readDB() {
        ArrayList<SinhVien> dssv = new ArrayList<>();
        try {
            String qry = "SELECT * FROM sinhvien";
            ResultSet r = qlsvDB.sqlQuery(qry);
            if (r != null) {
                while (r.next()) {
                    String mssv = r.getString(1);
                    String ho = r.getString(2);
                    String ten = r.getString(3);
                    dssv.add(new SinhVien(mssv, ho, ten));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");
        }
        return dssv;
    }

    public Boolean add(SinhVien sv) {
        Boolean ok = qlsvDB.sqlUpdate("INSERT INTO `sinhvien` (`mssv`, `ho`, `ten`) VALUES ('"
                + sv.getMssv() + "', '" + sv.getHo() + "', '" + sv.getTen() + "');");
        return ok;
    }

    public Boolean delete(String mssv) {
        Boolean ok = qlsvDB.sqlUpdate("DELETE FROM `sinhvien` WHERE `sinhvien`.`mssv` = '" + mssv + "'");
        return ok;
    }

    public Boolean update(String mssv, String ho, String ten) {
        Boolean ok = qlsvDB.sqlUpdate("Update SinhVien Set ho='" + ho + "',ten='" + ten + "' where mssv='" + mssv + "'");
        return ok;
    }

    public void close() {
        qlsvDB.closeConnect();
    }
}
