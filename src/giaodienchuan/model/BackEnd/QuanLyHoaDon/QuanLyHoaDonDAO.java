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
                    hd.setMaHoaDon(rs.getString(1));
                    hd.setMaNhanVien(rs.getString(2));
                    hd.setMaKhachHang(rs.getString(3));
                    hd.setNgayLap(rs.getDate(4).toLocalDate());
                    hd.setGioLap(rs.getTime(5).toLocalTime());
                    hd.setTongTien(rs.getFloat(6));
                    dshd.add(hd);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Khong tim thay du lieu !!");
        }finally{
            connection.closeConnect();
        }
        return dshd;
    }
    public Boolean add(HoaDon hd){
        connection =  new ConnectionDB();
        Boolean success = connection.sqlUpdate("INSERT INTO hoadon(MaHD,MaNV,MaKH,NgayLap,GioLap,TongTien) VALUES ('"+hd.getMaHoaDon()+"','"+hd.getMaNhanVien()+"','"+hd.getMaKhachHang()+"','"+hd.getNgayLap()+"','"+hd.getGioLap()+"','"+hd.getTongTien()+"');");
        connection.closeConnect();
        return success;
    }
    public Boolean delete(String mahd){
        connection =  new ConnectionDB();
        if(!connection.sqlUpdate("DELETE FROM hoadon WHERE MaHD='"+mahd+"';")){
            JOptionPane.showMessageDialog(null,"Vui long xoa het chi tiet cua hoa don truoc !!!");
            connection.closeConnect();
            return false;
        }
        connection.closeConnect();
        return false;
    }
    public Boolean update(HoaDon hd){
        connection =  new ConnectionDB();
        Boolean success = connection.sqlUpdate("UPDATE hoadon SET MaNV='"+hd.getMaNhanVien()+"', MaKH='"+hd.getMaKhachHang()+"', NgayLap='"+hd.getNgayLap()+"', GioLap='"+hd.getGioLap()+"', TongTien='"+hd.getTongTien()+"' WHERE MaHD='"+hd.getMaHoaDon()+"';");
        connection.closeConnect();
        return success;
    }
    public Boolean update(String maHoaDon,String maNhanVien,String maKhachHang,LocalDate ngayLap,LocalTime gioLap,float tongTien){
        HoaDon hd = new HoaDon();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaNhanVien(maNhanVien);
        hd.setMaKhachHang(maKhachHang);
        hd.setNgayLap(ngayLap);
        hd.setGioLap(gioLap);
        hd.setTongTien(tongTien);
        return update(hd);
    }
}
