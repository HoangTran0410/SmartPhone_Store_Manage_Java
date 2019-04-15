package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.ChiTietHoaDon_BUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon_BUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon_DTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;

public class HoaDon_ChiTietHoaDon_form extends JPanel {

    HoaDon_BUS qlhd = new HoaDon_BUS();
    ChiTietHoaDon_BUS qlcthd = new ChiTietHoaDon_BUS();

    JTextField txtMaHd = new JTextField();
    JTextField txtMaNv = new JTextField();
    JTextField txtMaKh = new JTextField();
    JTextField txtNgayLap = new JTextField();
    JTextField txtGioLap = new JTextField();
    JTextField txtTongTien = new JTextField();
    JTextField txtSearchbox = new JTextField();
    JComboBox timComboBox = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});

    JTextField txtMaHd2 = new JTextField();
    JTextField txtMaSp = new JTextField();
    JTextField txtSoLuong = new JTextField();
    JTextField txtDonGia = new JTextField();

    JTextField txtTim = new JTextField();
    JTextField txtSearchbox2 = new JTextField();
    JComboBox timComboBox2 = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");
    JButton btnSearch = new JButton("Tìm kiếm");

    JButton btnThemCTHD = new JButton("Thêm");
    JButton btnTimCTHD = new JButton("Tim");

    MyTable tbHoaDon = new MyTable();
    MyTable tbChiTiet = new MyTable();

    public HoaDon_ChiTietHoaDon_form() {

        JPanel pnlHoaDon = new JPanel();
        pnlHoaDon.setLayout(new BorderLayout());
        txtMaNv.setEditable(true);
        pnlHoaDon.setPreferredSize(new Dimension(1200 - 250, 350));

        JPanel pnlInput = new JPanel();
        pnlInput.setLayout(new GridLayout(1, 4));
        pnlInput.setPreferredSize(new Dimension(900, 45));
        txtMaHd.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        txtMaNv.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        txtMaKh.setBorder(BorderFactory.createTitledBorder("Mã khách hàng"));
        txtNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập"));
        txtGioLap.setBorder(BorderFactory.createTitledBorder("Giờ lập"));
        txtTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
        pnlInput.add(txtMaHd);
        pnlInput.add(txtMaNv);
        pnlInput.add(txtMaKh);
        pnlInput.add(txtNgayLap);
        pnlInput.add(txtGioLap);
        pnlInput.add(txtTongTien);

        JPanel pnlBtn = new JPanel();
        pnlBtn.setLayout(new GridLayout(1, 8));
        txtSearchbox.setBorder(BorderFactory.createTitledBorder("Nhập vào đây đê"));
        pnlBtn.add(btnThem);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnNhaplai);
        pnlBtn.add(btnReadDB);
        pnlBtn.add(txtSearchbox);
        pnlBtn.add(timComboBox);
        pnlBtn.add(btnSearch);

        JPanel pnlCTHD = new JPanel();
        pnlCTHD.setLayout(new GridLayout(1, 5));
        pnlCTHD.setPreferredSize(new Dimension(900, 50));
        txtMaHd2.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        txtMaSp.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        txtSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        txtDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
        pnlCTHD.add(txtMaHd2);
        pnlCTHD.add(txtMaSp);
        pnlCTHD.add(txtSoLuong);
        pnlCTHD.add(txtDonGia);
        pnlCTHD.add(btnThemCTHD);
        JPanel pnlTimCTHD = new JPanel();
        pnlTimCTHD.setPreferredSize(new Dimension(400, 50));
        pnlTimCTHD.setLayout(new GridLayout(1, 3));
        txtSearchbox2.setBorder(BorderFactory.createTitledBorder("Điền vào đây .-. "));
        pnlTimCTHD.add(txtSearchbox2);
        pnlTimCTHD.add(timComboBox2);
        pnlTimCTHD.add(btnTimCTHD);

        pnlHoaDon.add(tbHoaDon);

        tbHoaDon.setHeaders(new String[]{"Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});
        tbHoaDon.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableHoaDonRowClicked();
            }
        });
        tbChiTiet.setPreferredSize(new Dimension(950, 200));
        tbChiTiet.setHeaders(new String[]{"Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});
        tbChiTiet.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableChiTietRowClicked();
            }
        });

        this.add(pnlInput);
        this.add(pnlBtn);

        this.add(pnlHoaDon);
        this.add(pnlCTHD);
        this.add(pnlTimCTHD);
//        btnSua.setVisible(false);

//        timComboBox.setEditable(true);
//        timComboBox.getModel()
        btnTimCTHD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnTimKiemCTHDMouseClicked();
            }
        });
        btnThemCTHD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnThemChiTietMouseClicked();
            }
        });
        this.add(tbChiTiet);

        btnReadDB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnReadDBMouseClicked();
            }
        });

        btnNhaplai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnNhapLaiMouseClicked();
            }
        });
        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnTimKiemMouseClicked();
            }
        });
        btnSua.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnSuaMouseClicked();
                btnReadDBMouseClicked();
            }
        });
        btnThem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnThemHoaDonMouseClicked();
                btnReadDBMouseClicked();
            }
        });
        btnXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnXoaMouseClicked();
                btnReadDBMouseClicked();
            }
        });
//        popupform.setLocation(450, 300);
//        popupform.setVisible(true);
//        this.add(popupform);
    }

    private void btnReadDBMouseClicked() {
        qlhd.readDB();
        qlcthd.readDB();
        tbHoaDon.clear();
        qlhd.getDshd().forEach((hd) -> {
            System.out.println(hd.getMaHoaDon());
            String[] row = new String[]{hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(), hd.getNgayLap().toString(), hd.getGioLap().toString(), String.valueOf(hd.getTongTien())};
            tbHoaDon.addRow(row);
        });

    }

    private void tableHoaDonRowClicked() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            txtMaHd.setText(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString());
            txtMaNv.setText(tbHoaDon.getTable().getModel().getValueAt(i, 1).toString());
            txtMaKh.setText(tbHoaDon.getTable().getModel().getValueAt(i, 2).toString());
            txtNgayLap.setText(tbHoaDon.getTable().getModel().getValueAt(i, 3).toString());
            txtGioLap.setText(tbHoaDon.getTable().getModel().getValueAt(i, 4).toString());
            txtTongTien.setText(tbHoaDon.getTable().getModel().getValueAt(i, 5).toString());
            tbChiTiet.clear();
            qlcthd.getDshd().forEach((cthd) -> {
                if (tbHoaDon.getTable().getModel().getValueAt(i, 0).toString().equalsIgnoreCase(cthd.getMaHoaDon())) {
                    String[] row = new String[]{cthd.getMaHoaDon(), cthd.getMaSanPham(), String.valueOf(cthd.getSoLuong()), String.valueOf(cthd.getDonGia())};
                    tbChiTiet.addRow(row);
                }
            });
        }
    }

    private void tableChiTietRowClicked() {
        int j = tbChiTiet.getTable().getSelectedRow();
        if (j >= 0) {
            txtMaHd2.setText(tbChiTiet.getTable().getModel().getValueAt(j, 0).toString());
            txtMaSp.setText(tbChiTiet.getTable().getModel().getValueAt(j, 1).toString());
            txtSoLuong.setText(tbChiTiet.getTable().getModel().getValueAt(j, 2).toString());
            txtDonGia.setText(tbChiTiet.getTable().getModel().getValueAt(j, 3).toString());
        }
    }

    private Boolean checkInputHoaDon() {
        try {
            java.time.LocalDate.parse(txtNgayLap.getText());
            java.time.LocalTime.parse(txtGioLap.getText());
            Float.parseFloat(txtTongTien.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dinh dang ngay nhap: yyyy/mm/dd ; time: hh:mm:ss ; Tong tien phai la so !!");
            return false;
        }
        return true;
    }

    private Boolean checkInputChiTiet() {
        try {
            Integer.parseInt(txtSoLuong.getText());
            Float.parseFloat(txtDonGia.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "So luong va don gia phai la so !!");
            return false;
        }
        return true;
    }

    private void btnThemHoaDonMouseClicked() {
        Boolean isValid = true;
        for (HoaDon_DTO hd : qlhd.getDshd()) {
            if (hd.getMaHoaDon().equalsIgnoreCase(txtMaHd.getText())) {
                JOptionPane.showMessageDialog(null, "Ma hoa don da ton tai !!");
                isValid = false;
            }
        }
        if (isValid && checkInputHoaDon()) {
            qlhd.add(txtMaHd.getText(), txtMaNv.getText(), txtMaKh.getText(), java.time.LocalDate.parse(txtNgayLap.getText()), java.time.LocalTime.parse(txtGioLap.getText()), Float.parseFloat(txtTongTien.getText()));
        }
    }

    private void btnThemChiTietMouseClicked() {
        Boolean isValid = true;
        if (isValid && checkInputChiTiet()) {
            qlcthd.add(txtMaHd2.getText(), txtMaSp.getText(), Integer.parseInt(txtSoLuong.getText()), Float.parseFloat(txtSoLuong.getText()));
        }
        tbChiTiet.clear();
        qlcthd.getDshd().forEach((hd) -> {
//            tbHoaDon.addRow(new String[]{"asdasdas","asdasdasdasd","Asdaiii","asdiiidi"});
            String[] row = new String[]{hd.getMaHoaDon(), hd.getMaSanPham(), String.valueOf(hd.getSoLuong()), String.valueOf(hd.getDonGia())};
            tbChiTiet.addRow(row);
//            model.addRow(row);
        });
    }

    private void btnSuaMouseClicked() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            if (txtMaHd.getText().equalsIgnoreCase(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString())) {
                qlhd.update(txtMaHd.getText(), txtMaNv.getText(), txtMaKh.getText(), java.time.LocalDate.parse(txtNgayLap.getText()), java.time.LocalTime.parse(txtGioLap.getText()), Float.parseFloat(txtTongTien.getText()));
            } else {
                JOptionPane.showMessageDialog(null, "Khong the thay doi ma hoa don !!");
            }
        }
    }

    private void btnXoaMouseClicked() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            System.out.println(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString());
            qlhd.delete(tbHoaDon.getTable().getModel().getValueAt(i, 0).toString());
        }
    }

    private void btnNhapLaiMouseClicked() {
        txtMaHd.setText("");
        txtMaNv.setText("");
        txtMaKh.setText("");
        txtNgayLap.setText("");
        txtGioLap.setText("");
        txtTongTien.setText("");
//        popupForm pForm = new popupForm();
//        this.add(pForm);
    }

    private void btnTimKiemMouseClicked() {
        tbHoaDon.clear();
        qlhd.search(timComboBox.getSelectedItem().toString(), txtSearchbox.getText()).forEach((hd) -> {
            String[] row = new String[]{hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(), hd.getNgayLap().toString(), hd.getGioLap().toString(), String.valueOf(hd.getTongTien())};
            tbHoaDon.addRow(row);
        });
    }

    private void btnTimKiemCTHDMouseClicked() {
        tbChiTiet.clear();
        qlcthd.search(timComboBox2.getSelectedItem().toString(), txtSearchbox2.getText()).forEach((t) -> {
            String[] row = new String[]{t.getMaHoaDon(), t.getMaSanPham(), String.valueOf(t.getSoLuong()), String.valueOf(t.getDonGia())};
            tbChiTiet.addRow(row);
        });
    }
}
