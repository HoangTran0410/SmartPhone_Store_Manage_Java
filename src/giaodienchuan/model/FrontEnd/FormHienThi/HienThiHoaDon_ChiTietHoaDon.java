package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HienThiHoaDon_ChiTietHoaDon extends JPanel {

    QuanLyHoaDonBUS qlhd = new QuanLyHoaDonBUS();
//    QuanLyChiTietHoaDonBUS qlcthd = new QuanLyChiTietHoaDonBUS();

    JTextField txtSearchbox = new JTextField();
    JComboBox timComboBox = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});

    JTextField txtMaHd2 = new JTextField();
    JTextField txtMaSp = new JTextField();
    JTextField txtSoLuong = new JTextField();
    JTextField txtDonGia = new JTextField();

    JTextField txtTim = new JTextField();
    

    JButton btnXoaHoaDon = new JButton("Xóa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnDetails = new JButton("Xem chi tiết");

    MyTable tbHoaDon = new MyTable();

    public HienThiHoaDon_ChiTietHoaDon() {

        JPanel pnlHoaDon = new JPanel();
        pnlHoaDon.setLayout(new BorderLayout());
//        txtMaNv.setEditable(true);
        pnlHoaDon.setPreferredSize(new Dimension(1200 - 250, 350));

        JPanel pnlInput = new JPanel();
        pnlInput.setLayout(new GridLayout(1, 4));
        pnlInput.setPreferredSize(new Dimension(900, 45));
//        txtMaHd.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
//        txtMaNv.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
//        txtMaKh.setBorder(BorderFactory.createTitledBorder("Mã khách hàng"));
//        txtNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập"));
//        txtGioLap.setBorder(BorderFactory.createTitledBorder("Giờ lập"));
//        txtTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
//        pnlInput.add(txtMaHd);
//        pnlInput.add(txtMaNv);
//        pnlInput.add(txtMaKh);
//        pnlInput.add(txtNgayLap);
//        pnlInput.add(txtGioLap);
//        pnlInput.add(txtTongTien);

        JPanel pnlBtn = new JPanel();
        pnlBtn.setLayout(new GridLayout(1, 8));
        txtSearchbox.setBorder(BorderFactory.createTitledBorder("Nhập vào đây đê"));
//        pnlBtn.add(btnThem);
//        pnlBtn.add(btnXoaHoaDon);
        pnlBtn.add(btnDetails);
//        pnlBtn.add(btnNhaplai);
        pnlBtn.add(btnReadDB);
        pnlBtn.add(txtSearchbox);
        pnlBtn.add(timComboBox);
//        pnlBtn.add(btnSearch);

//        JPanel pnlCTHD = new JPanel();
//        pnlCTHD.setLayout(new GridLayout(1, 5));
//        pnlCTHD.setPreferredSize(new Dimension(900, 50));
//        txtMaHd2.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
//        txtMaSp.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
//        txtSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
//        txtDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
//        pnlCTHD.add(txtMaHd2);
//        pnlCTHD.add(txtMaSp);
//        pnlCTHD.add(txtSoLuong);
//        pnlCTHD.add(txtDonGia);
//        pnlCTHD.add(btnThemCTHD);
//        JPanel pnlTimCTHD = new JPanel();
//        pnlTimCTHD.setPreferredSize(new Dimension(400, 50));
//        pnlTimCTHD.setLayout(new GridLayout(1, 3));
//        txtSearchbox2.setBorder(BorderFactory.createTitledBorder("Điền vào đây .-. "));
//        pnlTimCTHD.add(txtSearchbox2);
//        pnlTimCTHD.add(timComboBox2);
//        pnlTimCTHD.add(btnTimCTHD);
//        pnlTimCTHD.add(btnXoaChiTietHoaDon);

        pnlHoaDon.add(tbHoaDon);

        tbHoaDon.setHeaders(new String[]{"STT","Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});
        tbHoaDon.setColumnsWidth(new double[]{.5,1.6,1.6,1.6,1.6,1.5,1.6});
        tbHoaDon.setAlignment(0, JLabel.CENTER);
        tbHoaDon.setAlignment(4, JLabel.CENTER);
        tbHoaDon.setAlignment(5, JLabel.CENTER);
        tbHoaDon.setAlignment(6, JLabel.RIGHT);
        setDataToTable(qlhd.getDshd(), tbHoaDon);
//        tbHoaDon.getTable().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                tableHoaDonRowClicked();
//            }
//        });
//        tbChiTiet.setPreferredSize(new Dimension(950, 200));
//        tbChiTiet.setHeaders(new String[]{"STT","Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});
//        tbChiTiet.getTable().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                tableChiTietRowClicked();
//            }
//        });

//        this.add(pnlInput);
        this.add(pnlBtn);

        this.add(pnlHoaDon);
//        this.add(pnlCTHD);
//        this.add(pnlTimCTHD);

//        btnTimCTHD.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                btnTimKiemCTHDMouseClicked();
//            }
//        });
        
//        this.add(tbChiTiet);

        btnReadDB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refresh();
            }
        });

        btnDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tbHoaDon.getTable().getSelectedRow();
                if(i>=0){
                    HienThiChiTietHoaDon htcthd = new HienThiChiTietHoaDon(tbHoaDon.getModel().getValueAt(i, 1).toString());
                } 
            }
});
//        btnNhaplai.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                btnNhapLaiMouseClicked();
//            }
//        });
//        btnSearch.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                btnTimKiemMouseClicked();
//            }
//        });
//        btnSua.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                btnSuaMouseClicked();
//                btnReadDBMouseClicked();
//            }
//        });
//        btnThem.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                btnThemHoaDonMouseClicked();
//                btnReadDBMouseClicked();
//            }
//        });
//        btnXoaHoaDon.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                btnXoaHoaDonMouseClicked();
//                refresh();
//            }
//        });
        txtSearchbox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txSearchOnChange();
            }
        });

    }

//    private void btnReadDBMouseClicked() {
//        qlhd.readDB();
//        qlcthd.readDB();
//        tbHoaDon.clear();
//        int stt = 1;
//        for (HoaDon hd : qlhd.getDshd()) {
////            System.out.println(hd.getMaHoaDon());
//            String[] row = new String[]{String.valueOf(stt),hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(), hd.getNgayLap().toString(), hd.getGioLap().toString(), String.valueOf(hd.getTongTien())};
//            stt++;
//            tbHoaDon.addRow(row);
//            
//        }
//    }

//    private void tableHoaDonRowClicked() {
//        int i = tbHoaDon.getTable().getSelectedRow();
//        if (i >= 0) {
//            txtMaHd.setText(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString());
//            txtMaNv.setText(tbHoaDon.getTable().getModel().getValueAt(i, 1).toString());
//            txtMaKh.setText(tbHoaDon.getTable().getModel().getValueAt(i, 2).toString());
//            txtNgayLap.setText(tbHoaDon.getTable().getModel().getValueAt(i, 3).toString());
//            txtGioLap.setText(tbHoaDon.getTable().getModel().getValueAt(i, 4).toString());
//            txtTongTien.setText(tbHoaDon.getTable().getModel().getValueAt(i, 5).toString());
//            tbChiTiet.clear();
//            qlcthd.getDshd().forEach((cthd) -> {
//                if (tbHoaDon.getTable().getModel().getValueAt(i, 0).toString().equalsIgnoreCase(cthd.getMaHoaDon())) {
//                    String[] row = new String[]{cthd.getMaHoaDon(), cthd.getMaSanPham(), String.valueOf(cthd.getSoLuong()), String.valueOf(cthd.getDonGia())};
//                    tbChiTiet.addRow(row);
//                }
//            });
//        }
//    }


//    private void tableChiTietRowClicked() {
//        int j = tbChiTiet.getTable().getSelectedRow();
//        if (j >= 0) {
//            txtMaHd2.setText(tbChiTiet.getTable().getModel().getValueAt(j, 0).toString());
//            txtMaSp.setText(tbChiTiet.getTable().getModel().getValueAt(j, 1).toString());
//            txtSoLuong.setText(tbChiTiet.getTable().getModel().getValueAt(j, 2).toString());
//            txtDonGia.setText(tbChiTiet.getTable().getModel().getValueAt(j, 3).toString());
//        }
//    }
//    private Boolean checkInputHoaDon() {
//        try {
//            java.time.LocalDate.parse(txtNgayLap.getText());
//            java.time.LocalTime.parse(txtGioLap.getText());
//            Float.parseFloat(txtTongTien.getText());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Dinh dang ngay nhap: yyyy/mm/dd ; time: hh:mm:ss ; Tong tien phai la so !!");
//            return false;
//        }
//        return true;
//    }

//    private Boolean checkInputChiTiet() {
//        try {
//            Integer.parseInt(txtSoLuong.getText());
//            Float.parseFloat(txtDonGia.getText());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "So luong va don gia phai la so !!");
//            return false;
//        }
//        return true;
//    }

//    private void btnThemHoaDonMouseClicked() {
//        Boolean isValid = true;
//        for (HoaDon hd : qlhd.getDshd()) {
//            if (hd.getMaHoaDon().equalsIgnoreCase(txtMaHd.getText())) {
//                JOptionPane.showMessageDialog(null, "Ma hoa don da ton tai !!");
//                isValid = false;
//            }
//        }
//        if (isValid && checkInputHoaDon()) {
//            qlhd.add(txtMaHd.getText(), txtMaNv.getText(), txtMaKh.getText(), java.time.LocalDate.parse(txtNgayLap.getText()), java.time.LocalTime.parse(txtGioLap.getText()), Float.parseFloat(txtTongTien.getText()));
//        }
//    }

//    private void btnThemChiTietMouseClicked() {
//        Boolean isValid = true;
//        if (isValid && checkInputChiTiet()) {
//            qlcthd.add(txtMaHd2.getText(), txtMaSp.getText(), Integer.parseInt(txtSoLuong.getText()), Float.parseFloat(txtSoLuong.getText()));
//        }
//        tbChiTiet.clear();
//        qlcthd.getDshd().forEach((hd) -> {
////            tbHoaDon.addRow(new String[]{"asdasdas","asdasdasdasd","Asdaiii","asdiiidi"});
//            String[] row = new String[]{hd.getMaHoaDon(), hd.getMaSanPham(), String.valueOf(hd.getSoLuong()), String.valueOf(hd.getDonGia())};
//            tbChiTiet.addRow(row);
////            model.addRow(row);
//        });
//    }

//    private void btnSuaMouseClicked() {
//        int i = tbHoaDon.getTable().getSelectedRow();
//        if (i >= 0) {
//            if (txtMaHd.getText().equalsIgnoreCase(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString())) {
//                qlhd.update(txtMaHd.getText(), txtMaNv.getText(), txtMaKh.getText(), java.time.LocalDate.parse(txtNgayLap.getText()), java.time.LocalTime.parse(txtGioLap.getText()), Float.parseFloat(txtTongTien.getText()));
//            } else {
//                JOptionPane.showMessageDialog(null, "Khong the thay doi ma hoa don !!");
//            }
//        }
//    }

//    private void btnXoaHoaDonMouseClicked() {
//        int i = tbHoaDon.getTable().getSelectedRow();
//        if (i >= 0) {
//            System.out.println(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString());
//            qlhd.delete(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString());
//        }
//    }



//    private void btnNhapLaiMouseClicked() {
//        txtMaHd.setText("");
//        txtMaNv.setText("");
//        txtMaKh.setText("");
//        txtNgayLap.setText("");
//        txtGioLap.setText("");
//        txtTongTien.setText("");
////        popupForm pForm = new popupForm();
////        this.add(pForm);
//    }

//    private void btnTimKiemMouseClicked() {
//        tbHoaDon.clear();
//        qlhd.search(timComboBox.getSelectedItem().toString(), txtSearchbox.getText()).forEach((hd) -> {
//            String[] row = new String[]{hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(), hd.getNgayLap().toString(), hd.getGioLap().toString(), String.valueOf(hd.getTongTien())};
//            tbHoaDon.addRow(row);
//        });
//    }

//    private void btnTimKiemCTHDMouseClicked() {
//        tbChiTiet.clear();
//        qlcthd.search(timComboBox2.getSelectedItem().toString(), txtSearchbox2.getText()).forEach((t) -> {
//            String[] row = new String[]{t.getMaHoaDon(), t.getMaSanPham(), String.valueOf(t.getSoLuong()), String.valueOf(t.getDonGia())};
//            tbChiTiet.addRow(row);
//        });
//    }
        public void refresh() {
        qlhd.readDB();
        setDataToTable(qlhd.getDshd(), tbHoaDon);
    }

    public String getSelectedSanPham() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            return tbHoaDon.getModel().getValueAt(i, 0).toString();
        }
        return null;
    }

    private void txSearchOnChange() {
        setDataToTable(qlhd.search(timComboBox.getSelectedItem().toString(),txtSearchbox.getText()), tbHoaDon);
    }

    private void setDataToTable(ArrayList<HoaDon> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (HoaDon hd : data) {
            table.addRow(new String[]{String.valueOf(stt), hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(),
                String.valueOf(hd.getNgayLap()), String.valueOf(hd.getGioLap()), String.valueOf(hd.getTongTien())});
            stt++;
        }
    }
}
