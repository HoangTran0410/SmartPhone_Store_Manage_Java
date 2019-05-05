/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyKhuyenMai;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class QuanLyKhuyenMaiBUS {
    private ArrayList<KhuyenMai> dskm = new ArrayList<>();
    QuanLyKhuyenMaiDAO qlkmDAO = new QuanLyKhuyenMaiDAO();

    public QuanLyKhuyenMaiBUS() {
        dskm = qlkmDAO.readDB();
    }

    public void showConsole() {
        dskm.forEach((km) -> {
            System.out.print(km.getMaKM()+ " ");
            System.out.print(km.getTenKM()+ " ");
            System.out.println(km.getDieuKienKM()+ " ");
            System.out.println(km.getPhanTramKM()+ " ");
            System.out.println(km.getNgayBD()+ " ");
            System.out.println(km.getNgayKT());
        });
    }

    public String[] getHeaders() {
        return new String[]{"Mã khuyến mãi", "Tên khuyến mãi", "Điều kiện khuyến mãi", "Phần trăm khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc"};
    }

    public void readDB() {
        dskm = qlkmDAO.readDB();
    }
    
    public String getNextID() {
        return "KM" + String.valueOf(this.dskm.size() + 1);
    }

    public KhuyenMai getKhuyenMai(String makm) {
        for (KhuyenMai km : dskm) {
            if (km.getMaKM().equals(makm)) {
                return km;
            }
        }
        return null;
    }

    public ArrayList<KhuyenMai> search(String value, String type, int dk1, int dk2, float phantram1, float phantram2, LocalDate ngay1, LocalDate ngay2) {
        ArrayList<KhuyenMai> result = new ArrayList<>();

        dskm.forEach((km) -> {
            if (type.equals("Tất cả")) {
                if (km.getMaKM().toLowerCase().contains(value.toLowerCase())
                        || km.getTenKM().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(km.getDieuKienKM()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(km.getPhanTramKM()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(km.getNgayBD()).toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(km.getNgayKT()).toLowerCase().contains(value.toLowerCase())) {
                    result.add(km);
                }
            } else {
                switch (type) {
                    case "Mã khuyến mãi":
                        if (km.getMaKM().toLowerCase().contains(value.toLowerCase())) {
                            result.add(km);
                        }
                        break;
                    case "Tên khuyến mãi":
                        if (km.getTenKM().toLowerCase().contains(value.toLowerCase())) {
                            result.add(km);
                        }
                        break;
                    case "Điều kiện khuyến mãi":
                        if (String.valueOf(km.getDieuKienKM()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(km);
                        }
                        break;
                    case "Phần trăm khuyến mãi":
                        if (String.valueOf(km.getPhanTramKM()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(km);
                        }
                        break;
                    case "Ngày bắt đầu":
                        if (String.valueOf(km.getNgayBD()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(km);
                        }
                        break;
                    case "Ngày kết thúc":
                        if (String.valueOf(km.getNgayKT()).toLowerCase().contains(value.toLowerCase())) {
                            result.add(km);
                        }
                        break;
                }
            }
        });

        for (int i = result.size() - 1; i >= 0; i--) {
            KhuyenMai km = result.get(i);
            float dkkm = km.getDieuKienKM();
            float phantram = km.getPhanTramKM();
            LocalDate ngaybd = km.getNgayBD();
            LocalDate ngaykt = km.getNgayKT();
            
            Boolean dkKhongThoa = (dk1 != -1 && dkkm < dk1) || (dk2 != -1 && dkkm > dk2);
            Boolean phantramKhongThoa = (phantram1 != -1 && phantram < phantram1) || (phantram2 != -1 && phantram > phantram2);
            Boolean ngayBDKhongThoa = (ngay1 != null && ngaybd.isBefore(ngay1)) || (ngay2 != null && ngaybd.isAfter(ngay2));
            Boolean ngayKTKhongThoa = (ngay1 != null && ngaykt.isBefore(ngay1)) || (ngay2 != null && ngaykt.isAfter(ngay2));

            if (dkKhongThoa || phantramKhongThoa || (ngayBDKhongThoa && ngayKTKhongThoa)) {
                result.remove(i);
            }
        }

        return result;
    }

    public Boolean add(KhuyenMai km) {
        Boolean ok = qlkmDAO.add(km);

        if (ok) {
            dskm.add(km);
        }
        return ok;
    }

    public Boolean add(String makm, String tenkm, float dkkm, float phantramkm, LocalDate ngaybd, LocalDate ngaykt) {
        KhuyenMai sp = new KhuyenMai(makm, tenkm, dkkm, phantramkm, ngaybd, ngaykt);
        return add(sp);
    }

    public Boolean delete(String makm) {
        Boolean ok = qlkmDAO.delete(makm);

        if (ok) {
            for (int i = (dskm.size() - 1); i >= 0; i--) {
                if (dskm.get(i).getMaKM().equals(makm)) {
                    dskm.remove(i);
                }
            }
        }
        return ok;
    }

    public Boolean update(String makm, String tenkm, float dkkm, float phantramkm, LocalDate ngaybd, LocalDate ngaykt) {
        Boolean ok = qlkmDAO.update(makm, tenkm, dkkm, phantramkm, ngaybd, ngaykt);

        if (ok) {
            dskm.forEach((km) -> {
                if (km.getMaKM().equals(makm)) {
                    km.setTenKM(tenkm);
                    km.setDieuKienKM(dkkm);
                    km.setPhanTramKM(phantramkm);
                    km.setNgayBD(ngaybd);
                    km.setNgayKT(ngaykt);
                }
            });
        }

        return ok;
    }

    public ArrayList<KhuyenMai> getDskm() {
        return dskm;
    }
}
