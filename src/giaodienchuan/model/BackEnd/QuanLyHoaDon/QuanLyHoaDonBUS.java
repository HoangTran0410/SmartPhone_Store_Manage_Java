package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuanLyHoaDonBUS {

    ArrayList<HoaDon> dshd = new ArrayList<>();
    private QuanLyHoaDonDAO qlhd = new QuanLyHoaDonDAO();

    public QuanLyHoaDonBUS() {
        dshd = qlhd.readDB();
    }
    
    public ArrayList<HoaDon> getDshd(){
        return this.dshd;
    }

    public void readDB() {
        dshd = qlhd.readDB();
    }

    public Boolean add(HoaDon hd) {
        Boolean success = qlhd.add(hd);
        if (success) {
            dshd.add(hd);
            return true;
        }
        return false;
    }

    public Boolean add(String maHoaDon, String maNhanVien, String maKhachHang, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {
        HoaDon hd = new HoaDon(maHoaDon, maNhanVien, maKhachHang, ngayNhap, gioNhap, tongTien);
        return add(hd);
    }

    public Boolean update(String maHoaDon, String maNhanVien, String maKhachHang, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {
        HoaDon hd = new HoaDon(maHoaDon, maNhanVien, maKhachHang, ngayNhap, gioNhap, tongTien);
        return update(hd);
    }

    public Boolean update(HoaDon hd) {
        Boolean success = qlhd.update(hd);
        if (success) {
            for (HoaDon cthd : dshd) {
                if (cthd.getMaHoaDon().equals(hd.getMaHoaDon())) {
                    cthd = hd;
                }
            }
            return true;
        }
        return false;
    }

    public Boolean delete(String maHoaDon) {
        Boolean success = qlhd.delete(maHoaDon);
        if (success) {
            for (HoaDon cthd : dshd) {
                if (cthd.getMaHoaDon().equals(maHoaDon)) {
                    dshd.remove(cthd);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<HoaDon> search(String type, String keyword) {
        ArrayList<HoaDon> result = new ArrayList<>();
        readDB();
        switch (type) {
            case "Tất cả":
                return dshd;
            case "Mã hóa đơn":
                dshd.forEach((t) -> {
                    if (t.getMaHoaDon().equalsIgnoreCase(keyword)) {
                        result.add(t);
                    }
                });
                break;
            case "Mã nhân viên":
                dshd.forEach((t) -> {
                    if (t.getMaNhanVien().equalsIgnoreCase(keyword)) {
                        result.add(t);
                    }
                });
                break;
            case "Mã khách hàng":
                dshd.forEach((t) -> {
                    if (t.getMaKhachHang().equalsIgnoreCase(keyword)) {
                        result.add(t);
                    }
                });
                break;
            case "Ngày lập":
                try{
                    java.time.LocalDate.parse(keyword);
                    dshd.forEach((t) -> {
                    if (t.getNgayLap().equals(java.time.LocalDate.parse(keyword))) {
                        result.add(t);
                    }});
                }catch(DateTimeParseException e){
                    JOptionPane.showMessageDialog(null, "Dinh dang ngay la : yyyy-mm-dd !!!");
                }
                break;
            case "Giờ lập":
                try{
                    java.time.LocalTime.parse(keyword);
                    dshd.forEach((t) -> {
                    if (t.getGioLap().equals(java.time.LocalTime.parse(keyword))) {
                        result.add(t);
                    }});
                }catch(DateTimeParseException e){
                    JOptionPane.showMessageDialog(null, "Dinh dang gio la: hh:mm !!!");
                }
                break;
            case "Tổng tiền":
                try{
                    Float.parseFloat(keyword);
                    dshd.forEach((t) -> {
                    if (t.getGioLap().equals(Float.parseFloat(keyword))) {
                        result.add(t);
                    }});
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Tong tien phai la so !!!");
                }
                break;
        }
        return result;
    }
}
