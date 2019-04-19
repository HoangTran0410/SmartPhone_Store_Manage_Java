package giaodienchuan.model.BackEnd.QuanLySanPham;

import java.util.ArrayList;

public class QuanLySanPhamBUS {

    private ArrayList<SanPham> dssp = new ArrayList<>();
    QuanLySanPhamDAO qlspDAO = new QuanLySanPhamDAO();

    public QuanLySanPhamBUS() {
        dssp = qlspDAO.readDB();
    }

    public void showConsole() {
        dssp.forEach((sp) -> {
            System.out.print(sp.getMaSP() + " ");
            System.out.print(sp.getMaLSP() + " ");
            System.out.println(sp.getTenSP() + " ");
            System.out.println(sp.getDonGia() + " ");
            System.out.println(sp.getSoLuong());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng"};
    }

    public void readDB() {
        dssp = qlspDAO.readDB();
    }

    public ArrayList<SanPham> search(String value, String type) {
        // Phương pháp tìm từ database
//        QuanLySanPhamDAO qlspDB = new QuanLySanPhamDAO();
//        dssp = qlspDB.search(columnName, value);
//        qlspDB.close();

        // phương pháp tìm từ arraylist
        ArrayList<SanPham> result = new ArrayList<>();

        dssp.forEach((sp) -> {
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

    public Boolean add(SanPham sp) {
        Boolean ok = qlspDAO.add(sp);

        if (ok) {
            dssp.add(sp);
        }
        return ok;
    }

    public Boolean add(String masp, String malsp, String tensp, float dongia, int soluong, String url) {
        SanPham sp = new SanPham(masp, malsp, tensp, dongia, soluong, url);
        return add(sp);
    }

    public Boolean delete(String masp) {
        Boolean ok = qlspDAO.delete(masp);

        if (ok) {
            for (int i = (dssp.size() - 1); i >= 0; i--) {
                if (dssp.get(i).getMaSP().equals(masp)) {
                    dssp.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String masp, String malsp, String tensp, float dongia, int soluong, String url) {
        Boolean ok = qlspDAO.update(masp, malsp, tensp, dongia, soluong, url);

        if (ok) {
            dssp.forEach((sp) -> {
                if (sp.getMaSP().equals(masp)) {
                    sp.setMaLSP(malsp);
                    sp.setTenSP(tensp);
                    sp.setDonGia(dongia);
                    sp.setSoLuong(soluong);
                    sp.setUrlHinhAnh(url);
                }
            });
        }

        return ok;
    }

    public Boolean updateSoLuong(String _masp,int _soLuong){
        Boolean ok = qlspDAO.updateSoLuong(_masp, _soLuong);
        if(ok){
            dssp.forEach((sp) -> {
                if(sp.getMaSP().equals(_masp)){
                    sp.setSoLuong(_soLuong);
                }
            });
        }
        return ok;
    }    
    
    public ArrayList<SanPham> getDssp() {
        return dssp;
    }
}
