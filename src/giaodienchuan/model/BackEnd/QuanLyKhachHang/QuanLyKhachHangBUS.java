package giaodienchuan.model.BackEnd.QuanLyKhachHang;

import java.util.ArrayList;

public class QuanLyKhachHangBUS {

    private ArrayList<KhachHang> dskh = new ArrayList<>();
    private QuanLyKhachHangDAO qlkhDAO = new QuanLyKhachHangDAO();

    public QuanLyKhachHangBUS() {
        dskh = qlkhDAO.readDB();
    }

    public void showConsole() {
        dskh.forEach((kh) -> {
            System.out.print(kh.getMaKH() + " ");
            System.out.println(kh.getTenKH() + " ");
            System.out.println(kh.getDiaChi() + " ");
            System.out.println(kh.getSDT() + " ");
            System.out.println(kh.getTrangThai());
        });
    }

    // headers của bảng sản phẩm
    public String[] getHeaders() {
        return new String[]{"Mã khách hàng", "Tên khách hàng", "Địa chỉ", "SĐT", "Trạng thái"};
    }

    public void readDB() {
        dskh = qlkhDAO.readDB();
    }
    
    public String getNextID() {
        return "KH" + String.valueOf(this.dskh.size() + 1);
    }
    
    public KhachHang getKhachHang(String makh) {
        for (KhachHang kh : dskh) {
            if (kh.getMaKH().equals(makh)) {
                return kh;
            }
        }
        return null;
    }

    public ArrayList<KhachHang> search(String value, String type) {

        ArrayList<KhachHang> result = new ArrayList<>();

        dskh.forEach((kh) -> {
            if (type.equals("Tất cả")) {
                if (kh.getMaKH().toLowerCase().contains(value.toLowerCase())
                        || kh.getTenKH().toLowerCase().contains(value.toLowerCase())
                        || kh.getDiaChi().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(kh.getSDT()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(kh.getTrangThai() == 1 ? "Ẩn" : "Hiện").toLowerCase().contains(value.toLowerCase())) {
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
                        if (kh.getSDT().toLowerCase().contains(value.toLowerCase())) {
                            result.add(kh);
                        }
                        break;
                    case "Trạng thái":
                        if (String.valueOf(kh.getTrangThai() == 1 ? "Ẩn" : "Hiện").toLowerCase().contains(value.toLowerCase())) {
                            result.add(kh);
                        }
                        break;        
                }
            }

        });

        return result;
    }

    public Boolean add(KhachHang kh) {
        Boolean ok = qlkhDAO.add(kh);

        if (ok) {
            dskh.add(kh);
        }
        return ok;
    }

    public Boolean add(String makh, String tenkh, String diachi, String sdt, int trangthai) {
        KhachHang kh = new KhachHang(makh, tenkh, diachi, sdt, trangthai);
        return add(kh);
    }

    public Boolean delete(String makh) {
        Boolean ok = qlkhDAO.delete(makh);

        if (ok) {
            for (int i = (dskh.size() - 1); i >= 0; i--) {
                if (dskh.get(i).getMaKH().equals(makh)) {
                    dskh.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String makh, String tenkh, String diachi, String sdt, int trangthai) {
        Boolean ok = qlkhDAO.update(makh, tenkh, diachi, sdt, trangthai);

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
    
    public Boolean updateTrangThai(String makh, int trangthai) {
        Boolean ok = qlkhDAO.updateTrangThai(makh, trangthai);

        if (ok) {
            dskh.forEach((kh) -> {
                if (kh.getMaKH().equals(makh)) {
                    kh.setTrangThai(trangthai);
                }
            });
        }

        return ok;
    }

    public ArrayList<KhachHang> getDskh() {
        return dskh;
    }
}
