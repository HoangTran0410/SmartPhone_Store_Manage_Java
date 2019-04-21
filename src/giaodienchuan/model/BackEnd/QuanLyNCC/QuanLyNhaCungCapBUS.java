package giaodienchuan.model.BackEnd.QuanLyNCC;

import java.util.ArrayList;

public class QuanLyNhaCungCapBUS {

    public ArrayList<NhaCungCap> dsncc = new ArrayList<>();
    QuanLyNhaCungCapDAO qlnccDAO = new QuanLyNhaCungCapDAO();
    
    public QuanLyNhaCungCapBUS() {
        dsncc = qlnccDAO.readDB();
    }

    public void show() {
        dsncc.forEach((ncc) -> {
            System.out.print(ncc.getMaNCC() + " ");
            System.out.print(ncc.getTenNCC() + " ");
            System.out.println(ncc.getDiaChi() + " ");
            System.out.println(ncc.getSDT() + " ");
            System.out.println(ncc.getFax());
        });
    }

    public void readDB() {
        dsncc = qlnccDAO.readDB();
    }
    
    public String getNextID() {
        return "NCC" + String.valueOf(this.dsncc.size() + 1);
    }

    public NhaCungCap getNhaCungCap(String mancc) {
        for (NhaCungCap ncc : dsncc) {
            if (ncc.getMaNCC().equals(mancc)) {
                return ncc;
            }
        }
        return null;
    }

    public ArrayList<NhaCungCap> search(String value, String type) {
        ArrayList<NhaCungCap> result = new ArrayList<>();

        dsncc.forEach((ncc) -> {
            if (type.equals("Tất cả")) {
                if (ncc.getMaNCC().toLowerCase().contains(value.toLowerCase())
                        || ncc.getTenNCC().toLowerCase().contains(value.toLowerCase())
                        || ncc.getDiaChi().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ncc.getSDT()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(ncc.getFax()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(ncc);
                }
            } else {
                switch (type) {
                    case "Mã nhà cung cấp":
                        if (ncc.getMaNCC().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "Tên nhà cung cấp":
                        if (ncc.getTenNCC().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "Địa chỉ":
                        if (ncc.getDiaChi().toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "SĐT":
                        if (String.valueOf(ncc.getSDT()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                    case "Fax":
                        if (String.valueOf(ncc.getFax()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(ncc);
                        }
                        break;
                }
            }

        });

        return result;
    }

    public Boolean add(NhaCungCap ncc) {
        qlnccDAO = new QuanLyNhaCungCapDAO();
        Boolean ok = qlnccDAO.add(ncc);

        if (ok) {
            dsncc.add(ncc);
        }
        return ok;
    }

    public Boolean add(String ma, String ten, String diachi, String sdt, String fax) {
        NhaCungCap ncc = new NhaCungCap(ma, ten, diachi, sdt, fax);
        return add(ncc);
    }

    public Boolean delete(String mancc) {
        qlnccDAO = new QuanLyNhaCungCapDAO();
        Boolean ok = qlnccDAO.delete(mancc);

        if (ok) {
            for (int i = (dsncc.size() - 1); i >= 0; i--) {
                if (dsncc.get(i).getMaNCC().equals(mancc)) {
                    dsncc.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String mancc, String tenncc, String diachi, String sdt, String fax) {
        qlnccDAO = new QuanLyNhaCungCapDAO();
        Boolean ok = qlnccDAO.update(mancc, tenncc, diachi, sdt, fax);

        if (ok) {
            dsncc.forEach((ncc) -> {
                if (ncc.getMaNCC().equals(mancc)) {
                    ncc.setTenNCC(tenncc);
                    ncc.setDiaChi(diachi);
                    ncc.setSDT(sdt);
                    ncc.setFax(fax);
                }
            });
        }

        return ok;
    }

    public ArrayList<NhaCungCap> getDsncc() {
        return dsncc;
    }
}
