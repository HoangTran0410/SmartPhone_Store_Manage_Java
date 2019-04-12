package giaodienchuan.model.BackEnd.ChucVu;

import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import java.util.ArrayList;

public class ChucVuBUS {

    private ArrayList<ChucVu> dscv = new ArrayList<>();

    public ChucVuBUS() {

    }

    public void showConsole() {
        dscv.forEach((cv) -> {
            System.out.print(cv.getMaCV() + " ");
            System.out.println(cv.getTenCV() + " ");
        });
    }

    public String[] getHeaders() {
        ChucVuDAO cvConnection = new ChucVuDAO();
        cvConnection.close();
        return cvConnection.getHeaders();
    }

    public void readDB() {
        ChucVuDAO cvConnection = new ChucVuDAO();
        dscv = cvConnection.readDB();
        cvConnection.close();
    }

    public ArrayList<ChucVu> search(String value, String type) {
        ArrayList<ChucVu> result = new ArrayList<>();

        dscv.forEach((cv) -> {
            if (type.equals("Tất cả")) {
                if (cv.getMaCV().toLowerCase().contains(value.toLowerCase())
                        || cv.getTenCV().toLowerCase().contains(value.toLowerCase())) {
                    result.add(cv);
                }
            } else {
                switch (type) {
                    case "Mã chức vụ":
                        if (cv.getMaCV().toLowerCase().contains(value.toLowerCase())) {
                            result.add(cv);
                        }
                        break;
                    case "Tên chức vụ":
                        if (cv.getTenCV().toLowerCase().contains(value.toLowerCase())) {
                            result.add(cv);
                        }
                        break;
                }
            }

        });

        return result;
    }

    public Boolean add(ChucVu cv) {
        ChucVuDAO cvConnection = new ChucVuDAO();
        Boolean ok = cvConnection.add(cv);
        cvConnection.close();

        if (ok) {
            dscv.add(cv);
        }
        return ok;
    }

    public Boolean add(String macv, String tencv) {
        ChucVu cv = new ChucVu(macv, tencv);
        return add(cv);
    }

    public Boolean delete(String macv) {
        ChucVuDAO cvConnection = new ChucVuDAO();
        Boolean ok = cvConnection.delete(macv);
        cvConnection.close();

        if (ok) {
            for (int i = (dscv.size() - 1); i >= 0; i--) {
                if (dscv.get(i).getMaCV().equals(macv)) {
                    dscv.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String macv, String tencv) {
        ChucVuDAO cvConnection = new ChucVuDAO();
        Boolean ok = cvConnection.update(macv, tencv);
        cvConnection.close();

        if (ok) {
            dscv.forEach((cv) -> {
                if (cv.getMaCV().equals(macv)) {
                    cv.setTenCV(tencv);

                }
            });
        }

        return ok;
    }

    public ArrayList<ChucVu> getDscv() {
        return dscv;
    }
}
