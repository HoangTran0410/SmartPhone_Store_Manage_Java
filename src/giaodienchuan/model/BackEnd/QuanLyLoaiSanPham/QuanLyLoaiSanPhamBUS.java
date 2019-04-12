
package giaodienchuan.model.BackEnd.QuanLyLoaiSanPham;


import java.util.ArrayList;

public class QuanLyLoaiSanPhamBUS {
    private ArrayList<LoaiSanPham> dslsp = new ArrayList<>();
    private QuanLyLoaiSanPhamDAO qllspDAO = new QuanLyLoaiSanPhamDAO();

    public QuanLyLoaiSanPhamBUS() {

    }

    public void showConsole() {
        dslsp.forEach((lsp) -> {
            System.out.print(lsp.getMaLSP() + " ");
            System.out.print(lsp.getTenLSP());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng"};
    }

    public void readDB() {
        dslsp = qllspDAO.readDB();
    }

    public ArrayList<LoaiSanPham> search(String value, String type) {
        // Phương pháp tìm từ database
//        QuanLySanPhamDAO qlspDB = new QuanLySanPhamDAO();
//        dssp = qlspDB.search(columnName, value);
//        qlspDB.close();

        // phương pháp tìm từ arraylist
        ArrayList<LoaiSanPham> result = new ArrayList<>();

        dslsp.forEach((sp) -> {
            if (type.equals("Tất cả")) {
                if (sp.getMaSP().toLowerCase().contains(value.toLowerCase())
                        || sp.getMaLSP().toLowerCase().contains(value.toLowerCase())
                        || sp.getTenSP().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(sp.getDonGia()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(sp.getSoLuong()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(sp);
                }
            } else {
                switch (type) {
                    case "Mã sản phẩm":
                        if (sp.getMaSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(sp);
                        }
                        break;
                    case "Mã loại":
                        if (sp.getMaLSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(sp);
                        }
                        break;
                    case "Tên":
                        if (sp.getTenSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(sp);
                        }
                        break;
                    case "Đơn giá":
                        if (String.valueOf(sp.getDonGia()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(sp);
                        }
                        break;
                    case "Số lượng":
                        if (String.valueOf(sp.getSoLuong()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(sp);
                        }
                        break;
                }
            }

        });

        return result;
    }

    public Boolean add(LoaiSanPham sp) {
        Boolean ok = qllspDAO.add(sp);

        if (ok) {
            dslsp.add(sp);
        }
        return ok;
    }

    public Boolean add(String masp, String malsp, String tensp, float dongia, int soluong) {
        LoaiSanPham sp = new LoaiSanPham(masp, malsp, tensp, dongia, soluong);
        return add(sp);
    }

    public Boolean delete(String masp) {
        Boolean ok = new QuanLySanPhamDAO().delete(masp);

        if (ok) {
            for (int i = (dslsp.size() - 1); i >= 0; i--) {
                if (dslsp.get(i).getMaSP().equals(masp)) {
                    dslsp.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String masp, String malsp, String tensp, float dongia, int soluong) {
        Boolean ok = new QuanLySanPhamDAO().update(masp, malsp, tensp, dongia, soluong);

        if (ok) {
            dslsp.forEach((sp) -> {
                if (sp.getMaSP().equals(masp)) {
                    sp.setMaLSP(malsp);
                    sp.setTenSP(tensp);
                    sp.setDonGia(dongia);
                    sp.setSoLuong(soluong);
                }
            });
        }

        return ok;
    }

    public ArrayList<SanPham> getDssp() {
        return dslsp;
    }
}
