package giaodienchuan.model.BackEnd.QuanLyHoaDon_ChiTietHoaDon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class HoaDon_DAO {

    ConnectDB connection = new ConnectDB("quanlysieuthidienthoai");

    public HoaDon_DAO() {
        connection.logIn("root", "");
    }
    
    public ArrayList<String> getHeaders() {
        return connection.getHeaders("hoadon");
    }

    public ArrayList readDB() {
        ArrayList<HoaDon_DTO> dshd = new ArrayList<>();
        try {
            String qry = "SELECT * FROM hoadon";
            ResultSet rs = connection.sqlQry(qry);
            if (rs != null) {
                while (rs.next()) {
                    HoaDon_DTO hd = new HoaDon_DTO();
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
        }
        return dshd;
    }
    public Boolean add(HoaDon_DTO hd){
        Boolean success = connection.sqlUpdate("INSERT INTO hoadon(MaHD,MaNV,MaKH,NgayLap,GioLap,TongTien) VALUES ('"+hd.getMaHoaDon()+"','"+hd.getMaNhanVien()+"','"+hd.getMaKhachHang()+"','"+hd.getNgayLap()+"','"+hd.getGioLap()+"','"+hd.getTongTien()+"');");
        return success;
    }
    public Boolean del(String mahd){
        ChiTietHoaDon_BUS cthd =new ChiTietHoaDon_BUS();
        if(cthd.del(mahd) && connection.sqlUpdate("DELETE FROM hoadon WHERE MaHD='"+mahd+"';"))
            return true;
        return false;
    }
    public Boolean update(HoaDon_DTO hd){
        Boolean success = connection.sqlUpdate("UPDATE hoadon SET MaNV='"+hd.getMaNhanVien()+"', MaKH='"+hd.getMaKhachHang()+"', NgayLap='"+hd.getNgayLap()+"', GioLap='"+hd.getGioLap()+"', TongTien='"+hd.getTongTien()+"' WHERE MaHD='"+hd.getMaHoaDon()+"';");
        return success;
    }
    public Boolean update(String maHoaDon,String maNhanVien,String maKhachHang,LocalDate ngayLap,LocalTime gioLap,float tongTien){
        HoaDon_DTO hd = new HoaDon_DTO();
        hd.setMaHoaDon(maHoaDon);
        hd.setMaNhanVien(maNhanVien);
        hd.setMaKhachHang(maKhachHang);
        hd.setNgayLap(ngayLap);
        hd.setGioLap(gioLap);
        hd.setTongTien(tongTien);
        return update(hd);
    }
    public void closeConnection(){
        connection.closeConnect();
    }
}
