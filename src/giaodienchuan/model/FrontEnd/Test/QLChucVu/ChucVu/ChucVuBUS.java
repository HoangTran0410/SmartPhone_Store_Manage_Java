package giaodienchuan.model.FrontEnd.Test.QLChucVu.ChucVu;

import java.util.ArrayList;

public class ChucVuBUS {

    private ArrayList<ChucVu> dscv = new ArrayList<>();
    ChucVuDAO qlcvDAO = new ChucVuDAO();

    public ChucVuBUS() {
        dscv = qlcvDAO.readDB();
    }

    public void showConsole() {
        dscv.forEach((cv) -> {
            System.out.print(cv.getMaCV() + " ");
            System.out.println(cv.getTenCV() + " ");
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã chức vụ", "Tên chức vụ"};
    }

    public void readDB() { 
        dscv = qlcvDAO.readDB();
    }
    
    public ChucVu getChucVu(String macv) {
        for(ChucVu cv : dscv) {
            if(cv.getMaCV().equals(macv))
                return cv;
        }
        return null;
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
        Boolean ok = qlcvDAO.add(cv);
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
        Boolean ok = qlcvDAO.delete(macv);
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
        Boolean ok = qlcvDAO.update(macv, tencv);
 
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
