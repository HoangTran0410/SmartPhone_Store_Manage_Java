package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyHoaDonDAO {

    ConnectionDB connection;

    public QuanLyHoaDonDAO() {
    }

    public ArrayList readDB() {
        connection = new ConnectionDB();
        ArrayList<HoaDon> dshd = new ArrayList<>();
        try {
            String qry = "SELECT * FROM hoadon";
            ResultSet rs = connection.sqlQuery(qry);
            if (rs != null) {
                while (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHoaDon(rs.getString("MaHD"));
                    hd.setMaNhanVien(rs.getString("MaNV"));
                    hd.setMaKhachHang(rs.getString("MaKH"));
                    hd.setMaKhuyenMai(rs.getString("MaKM"));
                    hd.setNgayLap(rs.getDate("NgayLap").toLocalDate());
                    hd.setGioLap(rs.getTime("GioLap").toLocalTime());
                    hd.setTongTien(rs.getFloat("TongTien"));
                    dshd.add(hd);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không đọc được dữ liệu bảng hóa đơn !!");
        } finally {
            connection.closeConnect();
        }
        return dshd;
    }

    public Boolean add(HoaDon hd) {
        connection = new ConnectionDB();
        Boolean success = connection.sqlUpdate("INSERT INTO hoadon(MaHD,MaNV,MaKH,MaKM,NgayLap,GioLap,TongTien) VALUES ('" 
                + hd.getMaHoaDon() + "','" 
                + hd.getMaNhanVien() + "','" 
                + hd.getMaKhachHang() + "','" 
                + hd.getMaKhuyenMai()+ "','" 
                + hd.getNgayLap() + "','" 
                + hd.getGioLap() + "','" 
                + hd.getTongTien() + "');");
        connection.closeConnect();
        return success;
    }

    public Boolean delete(String mahd) {
        connection = new ConnectionDB();
        if (!connection.sqlUpdate("DELETE FROM hoadon WHERE MaHD='" + mahd + "';")) {
            JOptionPane.showMessageDialog(null, "Vui long xoa het chi tiet cua hoa don truoc !!!");
            connection.closeConnect();
            return false;
        }
        connection.closeConnect();
        return true;
    }

    public Boolean update(HoaDon hd) {
        connection = new ConnectionDB();
        Boolean success = connection.sqlUpdate("UPDATE hoadon SET "
                + "MaNV='" + hd.getMaNhanVien() 
                + "', MaKH='" + hd.getMaKhachHang() 
                + "', MaKM='" + hd.getMaKhuyenMai()
                + "', NgayLap='" + hd.getNgayLap() 
                + "', GioLap='" + hd.getGioLap() 
                + "', TongTien='" + hd.getTongTien() 
                + "' WHERE MaHD='" + hd.getMaHoaDon() + "';");
        connection.closeConnect();
        return success;
    }
    
    public Boolean updateTongTien(String _mahd,float _tongTien){
        connection = new ConnectionDB();
        Boolean success = connection.sqlUpdate("UPDATE hoadon SET TongTien='" + _tongTien + "' WHERE MaHD='" +_mahd + "';");
        connection.closeConnect();
        return success;
    }

    public Boolean update(String maHoaDon, String maNhanVien, String maKhachHang, String makm, LocalDate ngayLap, LocalTime gioLap, float tongTien) {
        HoaDon hd = new HoaDon();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaNhanVien(maNhanVien);
        hd.setMaKhachHang(maKhachHang);
        hd.setMaKhuyenMai(makm);
        hd.setNgayLap(ngayLap);
        hd.setGioLap(gioLap);
        hd.setTongTien(tongTien);
        return update(hd);
    }
}
