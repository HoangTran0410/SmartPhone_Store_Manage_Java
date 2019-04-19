package giaodienchuan.model.BackEnd.QuanLyHoaDon;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class QuanLyHoaDonBUS {

    ArrayList<HoaDon> dshd = new ArrayList<>();
    private QuanLyHoaDonDAO qlhdDAO = new QuanLyHoaDonDAO();

    public QuanLyHoaDonBUS() {
        dshd = qlhdDAO.readDB();
    }

    public ArrayList<HoaDon> getDshd() {
        return this.dshd;
    }

    public void readDB() {
        dshd = qlhdDAO.readDB();
    }

    public Boolean add(HoaDon hd) {
        Boolean success = qlhdDAO.add(hd);
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
        Boolean success = qlhdDAO.update(hd);
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

    public Boolean updateTongTien(String _mahd, float _tongTien) {
        Boolean success = qlhdDAO.updateTongTien(_mahd, _tongTien);
        if (success) {
            for (HoaDon cthd : dshd) {
                if (cthd.getMaHoaDon().equals(_mahd)) {
                    cthd.setTongTien(_tongTien);
                }
            }
            return true;
        }
        return false;
    }

    public Boolean delete(String maHoaDon) {
        Boolean success = qlhdDAO.delete(maHoaDon);
        if (success) {
            for (HoaDon hd : dshd) {
                if (hd.getMaHoaDon().equals(maHoaDon)) {
                    dshd.remove(hd);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<HoaDon> search(String type, String keyword, LocalDate _ngay1, LocalDate _ngay2, int _tong1, int _tong2) {
        ArrayList<HoaDon> tempResult = new ArrayList<>();
        ArrayList<HoaDon> tempResult2 = new ArrayList<>();
        ArrayList<HoaDon> finalResult = new ArrayList<>();

        dshd.forEach((hd) -> {
            switch (type) {
                case "Tất cả":
                    if (hd.getMaHoaDon().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getMaNhanVien().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getMaKhachHang().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getNgayLap().toString().toLowerCase().contains(keyword.toLowerCase())
                            || hd.getGioLap().toString().toLowerCase().contains(keyword.toLowerCase())
                            || String.valueOf(hd.getTongTien()).toLowerCase().contains(keyword.toLowerCase())) {
                        tempResult.add(hd);
                    }

                    break;

                case "Mã hóa đơn":
                    if (hd.getMaHoaDon().toLowerCase().contains(keyword.toLowerCase())) {
                        tempResult.add(hd);
                    }
                    break;

                case "Mã nhân viên":
                    if (hd.getMaNhanVien().toLowerCase().contains(keyword.toLowerCase())) {
                        tempResult.add(hd);
                    }
                    break;

                case "Mã khách hàng":
                    if (hd.getMaKhachHang().toLowerCase().contains(keyword.toLowerCase())) {
                        tempResult.add(hd);
                    }
                    break;

//                case "Ngày lập":
//                    if (hd.getNgayLap().toString().toLowerCase().contains(keyword.toLowerCase())) {
//                        result.add(hd);
//                    }
//                    break;
//
//                case "Giờ lập":
//                    if (hd.getGioLap().toString().toLowerCase().contains(keyword.toLowerCase())) {
//                        result.add(hd);
//                    }
//                    break;
//                case "Tổng tiền":
//                    if (String.valueOf(hd.getTongTien()).toLowerCase().contains(keyword.toLowerCase())) {
//                        result.add(hd);
//                    }
//                    break;
            }
        });
        
        //Ngay lap
        if(_ngay1 != null && _ngay2 != null){
            tempResult.forEach((hd) -> {
                if((hd.getNgayLap().isAfter(_ngay1) || hd.getNgayLap().isEqual(_ngay1)) && (hd.getNgayLap().isBefore(_ngay2) || hd.getNgayLap().isEqual(_ngay2)))
                    tempResult2.add(hd);
            });
        }else if(_ngay1 == null && _ngay2 == null){
            tempResult.forEach((t) -> {
                tempResult2.add(t);
            });
        }
        else if(_ngay2 == null){
            tempResult.forEach((hd) -> {
                if(hd.getNgayLap().isAfter(_ngay1) || hd.getNgayLap().isEqual(_ngay1) )
                    tempResult2.add(hd);
            });
        }else if(_ngay1 == null){
            tempResult.forEach((hd) -> {
                if(hd.getNgayLap().isBefore(_ngay2) || hd.getNgayLap().isEqual(_ngay2))
                    tempResult2.add(hd);
            });
        }
        
        //Tong tien
        if(_tong1 != -1 && _tong2 != -1){
            tempResult2.forEach((hd) -> {
                if(hd.getTongTien() >= _tong1 && hd.getTongTien() <= _tong2)
                    finalResult.add(hd);
            });
        }else if(_tong1 == -1 && _tong2 == -1){
            tempResult2.forEach((t) -> {
                finalResult.add(t);
            });
        }
        else if(_tong2 == -1){
            tempResult2.forEach((hd) -> {
                if(hd.getTongTien() >= _tong1)
                    finalResult.add(hd);
            });
        }else if(_tong1 == -1){
            tempResult2.forEach((hd) -> {
                if(hd.getTongTien() <= _tong2)
                    finalResult.add(hd);
            });
        }
        return finalResult;
    }
}
