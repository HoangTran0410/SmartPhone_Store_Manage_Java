package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class HoaDon_BUS {

    ArrayList<HoaDon_DTO> dshd = new ArrayList<>();

    public HoaDon_BUS() {
    }

    public ArrayList<String> getHeaders() {
        HoaDon_DAO qlhd = new HoaDon_DAO();
        qlhd.closeConnection();

        return qlhd.getHeaders();

    }

    public void readDB() {
        HoaDon_DAO qlhd = new HoaDon_DAO();
        dshd = qlhd.readDB();
        qlhd.closeConnection();
    }

    public Boolean add(HoaDon_DTO hd) {
        HoaDon_DAO qlhd = new HoaDon_DAO();
        Boolean success = qlhd.add(hd);
        qlhd.closeConnection();
        if (success) {
            dshd.add(hd);
            return true;
        }
        return false;
    }

    public Boolean add(String maHoaDon, String maNhanVien, String maKhachHang, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {
        HoaDon_DTO hd = new HoaDon_DTO(maHoaDon, maNhanVien, maKhachHang, ngayNhap, gioNhap, tongTien);
        return add(hd);
    }

    public Boolean update(String maHoaDon, String maNhanVien, String maKhachHang, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {
        HoaDon_DTO hd = new HoaDon_DTO(maHoaDon, maNhanVien, maKhachHang, ngayNhap, gioNhap, tongTien);
        return update(hd);
    }

    public Boolean update(HoaDon_DTO hd) {
        HoaDon_DAO qlhd = new HoaDon_DAO();
        Boolean success = qlhd.update(hd);
        qlhd.closeConnection();
        if (success) {
            for (HoaDon_DTO cthd : dshd) {
                if (cthd.getMaHoaDon().equals(hd.getMaHoaDon())) {
                    cthd = hd;
                }
            }
        }
        return false;
    }

    public Boolean del(String maHoaDon) {
        HoaDon_DAO qlhd = new HoaDon_DAO();
        Boolean success = qlhd.del(maHoaDon);
        qlhd.closeConnection();
        if (success) {
            System.out.println("succck");
            for (HoaDon_DTO cthd : dshd) {
                if (cthd.getMaHoaDon().equals(maHoaDon)) {
                    dshd.remove(cthd);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<HoaDon_DTO> search(String type, String keyword) {
        ArrayList<HoaDon_DTO> result = new ArrayList<>();
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
