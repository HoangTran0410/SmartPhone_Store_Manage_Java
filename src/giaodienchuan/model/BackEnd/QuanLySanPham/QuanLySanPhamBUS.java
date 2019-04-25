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
            System.out.println(sp.getSoLuong() + " ");
            System.out.println(sp.getTrangThai());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng", "Trạng thái"};
    }

    public void readDB() {
        dssp = qlspDAO.readDB();
    }

    public String getNextID() {
        return "SP" + String.valueOf(this.dssp.size() + 1);
    }

    public SanPham getSanPham(String masp) {
        for (SanPham sp : dssp) {
            if (sp.getMaSP().equals(masp)) {
                return sp;
            }
        }
        return null;
    }

    public ArrayList<SanPham> search(String value, String type, int soluong1, int soluong2, float gia1, float gia2, int trangthai) {
        ArrayList<SanPham> result = new ArrayList<>();

        dssp.forEach((sp) -> {
            if (type.equals("Tất cả")) {
                if (sp.getMaSP().toLowerCase().contains(value.toLowerCase())
                        || sp.getMaLSP().toLowerCase().contains(value.toLowerCase())
                        || sp.getTenSP().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(sp.getDonGia()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(sp.getSoLuong()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf((sp.getTrangThai() == 1 ? "Ẩn" : "Hiện")).toLowerCase().contains(value.toLowerCase())) {
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
                    case "Trạng thái":
                        String tt = (sp.getTrangThai() == 1 ? "Ẩn" : "Hiện");
                        if (String.valueOf(tt).toLowerCase().contains(value.toLowerCase())) {
                            result.add(sp);
                        }
                        break;
                }
            }
        });

        for (int i = result.size() - 1; i >= 0; i--) {
            SanPham sp = result.get(i);
            int soluong = sp.getSoLuong();
            float gia = sp.getDonGia();
            int tt = sp.getTrangThai();
            Boolean soLuongKhongThoa = (soluong1 != -1 && soluong < soluong1) || (soluong2 != -1 && soluong > soluong2);
            Boolean giaKhongThoa = (gia1 != -1 && gia < gia1) || (gia2 != -1 && gia > gia2);
            Boolean trangThaiKhongThoa = (trangthai != -1) && tt != trangthai;

            if (soLuongKhongThoa || giaKhongThoa || trangThaiKhongThoa) {
                result.remove(i);
            }
        }

        return result;
    }

    public Boolean add(SanPham sp) {
        Boolean ok = qlspDAO.add(sp);

        if (ok) {
            dssp.add(sp);
        }
        return ok;
    }

    public Boolean add(String masp, String malsp, String tensp, float dongia, int soluong, String filename, int trangthai) {
        SanPham sp = new SanPham(masp, malsp, tensp, dongia, soluong, filename, trangthai);
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

    public Boolean update(String masp, String malsp, String tensp, float dongia, int soluong, String filename, int trangthai) {
        Boolean ok = qlspDAO.update(masp, malsp, tensp, dongia, soluong, filename, trangthai);

        if (ok) {
            dssp.forEach((sp) -> {
                if (sp.getMaSP().equals(masp)) {
                    sp.setMaLSP(malsp);
                    sp.setTenSP(tensp);
                    sp.setDonGia(dongia);
                    sp.setSoLuong(soluong);
                    sp.setFileNameHinhAnh(filename);
                    sp.setTrangThai(trangthai);
                }
            });
        }

        return ok;
    }

    public Boolean updateSoLuong(String masp, int soluong) {
        Boolean ok = qlspDAO.updateSoLuong(masp, soluong);

        if (ok) {
            dssp.forEach((sp) -> {
                if (sp.getMaSP().equals(masp)) {
                    sp.setSoLuong(soluong);
                }
            });
        }

        return ok;
    }

    public Boolean updateTrangThai(String masp, int trangthai) {
        Boolean ok = qlspDAO.updateTrangThai(masp, trangthai);

        if (ok) {
            dssp.forEach((sp) -> {
                if (sp.getMaSP().equals(masp)) {
                    sp.setTrangThai(trangthai);
                }
            });
        }

        return ok;
    }

    public ArrayList<SanPham> getDssp() {
        return dssp;
    }
}
