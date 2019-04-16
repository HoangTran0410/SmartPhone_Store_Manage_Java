package giaodienchuan.model.BackEnd.QuanLyHoaDon_ChiTietHoaDon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChiTietHoaDon_DAO {

    ConnectDB connection = new ConnectDB();

    public ChiTietHoaDon_DAO() {
        connection.logIn("root", "");
    }

    public ArrayList<String> getHeaders() {
        return connection.getHeaders("chitiethoadon");
        // mếu ông thay đổi các heađer ở DB, thì ông sẽ luôn phải thay đổi các hàm trong class này right ?
    }

    public ArrayList readDB() {
        connection.setupConnection();
        ArrayList<ChiTietHoaDon_DTO> dshd = new ArrayList<>();
        try {
            String qry = "SELECT * FROM chitiethoadon";
            ResultSet rs = connection.sqlQry(qry);
            if (rs != null) {
                while (rs.next()) {
                    ChiTietHoaDon_DTO hd = new ChiTietHoaDon_DTO();
                    hd.setMaHoaDon(rs.getString(1));
                    hd.setMaSanPham(rs.getString(2));
                    hd.setSoLuong(rs.getInt(3));
                    hd.setDonGia(rs.getFloat(4));
                    dshd.add(hd);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Khong tim thay du lieu !!");
        } finally {
            connection.closeConnect();
        }
        return dshd;
    }

    public Boolean add(ChiTietHoaDon_DTO hd) {
        connection.setupConnection();
        Boolean success = connection.sqlUpdate("INSERT INTO chitiethoadon(MaHD,MaSP,SoLuong,DonGia) VALUES ('" + hd.getMaHoaDon() + "','" + hd.getMaSanPham() + "','" + hd.getSoLuong() + "','" + hd.getDonGia() + "');");
        connection.closeConnect();
        return success;
    }

    public Boolean del(String mahd) {
        connection.setupConnection();
        Boolean success = connection.sqlUpdate("DELETE FROM chitiethoadon WHERE MaHD='" + mahd + "';");
        connection.closeConnect();
        return success;
    }

    public Boolean update(ChiTietHoaDon_DTO hd) {
        Boolean success = connection.sqlUpdate("UPDATE chitiethoadon set MaSP='" + hd.getMaSanPham() + "', SoLuong='" + hd.getSoLuong() + "', DonGia='" + hd.getDonGia() + "' WHERE MaHD='" + hd.getMaHoaDon() + "';");
        return success;
    }

    public Boolean update(String maHoaDon, String maSanPham, int soLuong, float donGia) {
        ChiTietHoaDon_DTO hd = new ChiTietHoaDon_DTO();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaSanPham(maSanPham);
        hd.setSoLuong(soLuong);
        hd.setDonGia(donGia);
        return update(hd);
    }

    public void closeConnection() {
        connection.closeConnect();
    }
}
