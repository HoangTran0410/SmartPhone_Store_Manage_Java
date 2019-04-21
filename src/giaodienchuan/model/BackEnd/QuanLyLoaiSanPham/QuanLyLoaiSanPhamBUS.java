package giaodienchuan.model.BackEnd.QuanLyLoaiSanPham;

import java.util.ArrayList;

public class QuanLyLoaiSanPhamBUS {

    private ArrayList<LoaiSanPham> dslsp = new ArrayList<>();
    private QuanLyLoaiSanPhamDAO qllspDAO = new QuanLyLoaiSanPhamDAO();

    public QuanLyLoaiSanPhamBUS() {
        dslsp = qllspDAO.readDB();
    }

    public void showConsole() {
        dslsp.forEach((lsp) -> {
            System.out.print(lsp.getMaLSP() + " ");
            System.out.print(lsp.getTenLSP());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã loại", "Tên loại", "Mô tả"};
    }
    
    public String getNextID() {
        return "LSP" + String.valueOf(this.dslsp.size() + 1);
    }

    public void readDB() {
        dslsp = qllspDAO.readDB();
    }
    
    public LoaiSanPham getLoaiSanPham(String maloai) {
        for (LoaiSanPham lsp : dslsp) {
            if (lsp.getMaLSP().equals(maloai)) {
                return lsp;
            }
        }
        return null;
    }

    public ArrayList<LoaiSanPham> search(String value, String type) {
        // Phương pháp tìm từ database
//        QuanLySanPhamDAO qlspDB = new QuanLySanPhamDAO();
//        dssp = qlspDB.search(columnName, value);
//        qlspDB.close();

        // phương pháp tìm từ arraylist
        ArrayList<LoaiSanPham> result = new ArrayList<>();

        dslsp.forEach((lsp) -> {
            if (type.equals("Tất cả")) {
                if (lsp.getMaLSP().toLowerCase().contains(value.toLowerCase())
                        || lsp.getTenLSP().toLowerCase().contains(value.toLowerCase())
                        || lsp.getMoTa().toLowerCase().contains(value.toLowerCase()))  {
                    result.add(lsp);
                }
            } else {
                switch (type) {
                    case "Mã loại":
                        if (lsp.getMaLSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(lsp);
                        }
                        break;
                    case "Tên loại":
                        if (lsp.getTenLSP().toLowerCase().contains(value.toLowerCase())) {
                            result.add(lsp);
                        }
                        break;
                    case "Mô tả":
                        if (lsp.getMoTa().toLowerCase().contains(value.toLowerCase())) {
                            result.add(lsp);
                        }
                        break;

                }
            }

        });

        return result;
    }

    public Boolean add(LoaiSanPham lsp) {
        Boolean ok = qllspDAO.add(lsp);

        if (ok) {
            dslsp.add(lsp);
        }
        return ok;
    }

    public Boolean add(String malsp, String tenlsp, String mota) {
        LoaiSanPham lsp = new LoaiSanPham(malsp, tenlsp, mota);
        return add(lsp);
    }

    public Boolean delete(String malsp) {
        Boolean ok = qllspDAO.delete(malsp);

        if (ok) {
            for (int i = (dslsp.size() - 1); i >= 0; i--) {
                if (dslsp.get(i).getMaLSP().equals(malsp)) {
                    dslsp.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String malsp, String tenlsp, String mota) {
        Boolean ok = qllspDAO.update(malsp, tenlsp, mota);

        if (ok) {
            dslsp.forEach((lsp) -> {
                if (lsp.getMaLSP().equals(malsp)) {
                    lsp.setTenLSP(tenlsp);
                    lsp.setMoTa(mota);
                }
            });
        }

        return ok;
    }

    public ArrayList<LoaiSanPham> getDslsp() {
        return dslsp;
    }
}
