package giaodienchuan.model.BackEnd.KhachHang;

import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import java.util.ArrayList;

public class QuanLyKhachHangBUS {

    private ArrayList<KhachHang> dskh = new ArrayList<>();

    public QuanLyKhachHangBUS() {

    }

    public void showConsole() {
        dskh.forEach((kh) -> {
            System.out.print(kh.getMaKH() + " ");
            System.out.println(kh.getTenKH() + " ");
            System.out.println(kh.getDiaChi()+" ");
            System.out.println(kh.getSDT()+" ");
        });
    }
    
    public String[] getHeaders() {
        QuanLyKhachHangDAO qlkhConnection = new QuanLyKhachHangDAO();
        qlkhConnection.close();
        return qlkhConnection.getHeaders();
    }

    public void readDB() {
        dskh = new QuanLyKhachHangDAO().readDB();
    }
    
    public ArrayList<KhachHang> search(String value, String type) {
        
        ArrayList<KhachHang> result = new ArrayList<>();

        dskh.forEach((kh) -> {
            if (type.equals("Tất cả")) {
                if (kh.getMaKH().toLowerCase().contains(value.toLowerCase())
                        || kh.getTenKH().toLowerCase().contains(value.toLowerCase())
                        || kh.getDiaChi().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(kh.getSDT()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(kh);
                }
            } else {
                switch (type) {
                    case "Mã khách hàng":
                        if (kh.getMaKH().toLowerCase().contains(value.toLowerCase())) {
                            result.add(kh);
                        }
                        break;
                    case "Tên khách hàng":
                        if (kh.getTenKH().toLowerCase().contains(value.toLowerCase())) {
                            result.add(kh);
                        }
                        break;
                    case "Địa chỉ":
                        if (kh.getDiaChi().toLowerCase().contains(value.toLowerCase())) {
                            result.add(kh);
                        }
                        break;
                    case "Số điện thoại":
                        if (String.valueOf(kh.getSDT()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(kh);
                        }
                        break;
                }
            }

        });

        return result;
    }

    public Boolean add(KhachHang kh) {
        QuanLyKhachHangDAO qlkhConnection = new QuanLyKhachHangDAO();
        Boolean ok = qlkhConnection.add(kh);
        qlkhConnection.close();

        if (ok) {
            dskh.add(kh);
        }
        return ok;
    }

    public Boolean add(String makh, String tenkh, String diachi, int sdt) {
        KhachHang kh = new KhachHang(makh,  tenkh, diachi, sdt);
        return add(kh);
    }

    public Boolean delete(String makh) {
        QuanLyKhachHangDAO qlkhConnection = new QuanLyKhachHangDAO();
        Boolean ok = qlkhConnection.delete(makh);
        qlkhConnection.close();

        if (ok) {
            for (int i = (dskh.size() - 1); i >= 0; i--) {
                if (dskh.get(i).getMaKH().equals(makh)) {
                    dskh.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String makh,  String tenkh, String diachi, int sdt) {
        QuanLyKhachHangDAO qlkhConnection = new QuanLyKhachHangDAO();
        Boolean ok = qlkhConnection.update(makh, tenkh, diachi, sdt);
        qlkhConnection.close();

        if (ok) {
            dskh.forEach((kh) -> {
                if (kh.getMaKH().equals(makh)) {                
                    kh.setTenKH(tenkh);
                    kh.setDiaChi(diachi);
                    kh.setSDT(sdt);
                }
            });
        }

        return ok;
    }

    public ArrayList<KhachHang> getDskh() {
        return dskh;
    }
}
