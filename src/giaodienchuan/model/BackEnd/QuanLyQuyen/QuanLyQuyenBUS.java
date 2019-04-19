package giaodienchuan.model.BackEnd.QuanLyQuyen;

import java.util.ArrayList;

public class QuanLyQuyenBUS {

    private ArrayList<Quyen> dsq = new ArrayList<>();
    QuanLyQuyenDAO qlqDAO = new QuanLyQuyenDAO();

    public QuanLyQuyenBUS() {
        dsq = qlqDAO.readDB();
    }

    public void showConsole() {
        dsq.forEach((q) -> {
            System.out.print(q.getMaQuyen() + " ");
            System.out.print(q.getChiTietQuyen());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã quyền", "Chi tiết quyền"};
    }

    public void readDB() {
        dsq = qlqDAO.readDB();
    }

    public Quyen getQuyen(String maquyen) {
        for (Quyen q : dsq) {
            if (q.getMaQuyen().equals(maquyen)) {
                return q;
            }
        }
        return null;
    }

    public ArrayList<Quyen> search(String value, String type) {
        ArrayList<Quyen> result = new ArrayList<>();

        dsq.forEach((q) -> {
            if (type.equals("Tất cả")) {
                if (q.getMaQuyen().toLowerCase().contains(value.toLowerCase())
                        || q.getChiTietQuyen().toLowerCase().contains(value.toLowerCase())) {
                    result.add(q);
                }
            } else {
                switch (type) {
                    case "Mã quyền":
                        if (q.getMaQuyen().toLowerCase().contains(value.toLowerCase())) {
                            result.add(q);
                        }
                        break;
                    case "Chi tiết quyền":
                        if (q.getChiTietQuyen().toLowerCase().contains(value.toLowerCase())) {
                            result.add(q);
                        }
                        break;
                }
            }

        });

        return result;
    }

    public Boolean add(Quyen sp) {
        Boolean ok = qlqDAO.add(sp);

        if (ok) {
            dsq.add(sp);
        }
        return ok;
    }

    public Boolean add(String maquyen, String chitiet) {
        Quyen sp = new Quyen(maquyen, chitiet);
        return add(sp);
    }

    public Boolean delete(String maquyen) {
        Boolean ok = qlqDAO.delete(maquyen);

        if (ok) {
            for (int i = (dsq.size() - 1); i >= 0; i--) {
                if (dsq.get(i).getMaQuyen().equals(maquyen)) {
                    dsq.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String maquyen, String chitiet) {
        Boolean ok = qlqDAO.update(maquyen, chitiet);

        if (ok) {
            dsq.forEach((sp) -> {
                if (sp.getMaQuyen().equals(maquyen)) {
                    sp.setChiTietQuyen(chitiet);
                }
            });
        }

        return ok;
    }

    public ArrayList<Quyen> getDssp() {
        return dsq;
    }
}
