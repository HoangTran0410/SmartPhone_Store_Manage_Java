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

//    public String[] getHeaders() {
//        return new String[]{"Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày nhập", "Giờ nhập", "Tổng tiền"};
//    }
 
    public ArrayList<HoaDon> getDshd() {
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

        dshd.forEach((hd) -> {
            switch (type) {
                case "Tất cả":
                    if (hd.getMaHoaDon().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getMaNhanVien().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getMaKhachHang().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getNgayLap().toString().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getGioLap().toString().toLowerCase().contains(keyword.toLowerCase())
                            || String.valueOf(hd.getTongTien()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }

                    break;

                case "Mã hóa đơn":
                    if (hd.getMaHoaDon().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Mã nhân viên":
                    if (hd.getMaNhanVien().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Mã khách hàng":
                    if (hd.getMaKhachHang().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Ngày lập":
                    if (hd.getNgayLap().toString().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Giờ lập":
                    if (hd.getGioLap().toString().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;

                case "Tổng tiền":
                    if (String.valueOf(hd.getTongTien()).toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(hd);
                    }
                    break;
            }
        });
        return result;
    }
}
