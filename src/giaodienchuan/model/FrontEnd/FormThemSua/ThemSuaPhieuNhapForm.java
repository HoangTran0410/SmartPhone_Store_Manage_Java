/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.PhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.QuanLyPhieuNhapBUS;
import giaodienchuan.model.FrontEnd.FormChon.ChonNhaCungCapForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonNhanVienForm;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class ThemSuaPhieuNhapForm extends JFrame {

    String type;
    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    PhieuNhap pnSua;

    JTextField txtMaPN = new JTextField(15);
    JTextField txtMaNCC = new JTextField(15);
    JTextField txtMaNV = new JTextField(15);
    JTextField txtNgayNhap = new JTextField(15);
    JTextField txtGioNhap = new JTextField(15);
    JTextField txtTongTien = new JTextField(15);

    MoreButton btnChonNhanVien = new MoreButton();
    MoreButton btnChonNhaCungCap = new MoreButton();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaPhieuNhapForm(String _type, String _mapn) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txtMaPN.setBorder(BorderFactory.createTitledBorder("Mã phiếu nhập"));
        txtMaNCC.setBorder(BorderFactory.createTitledBorder(" "));
        txtMaNV.setBorder(BorderFactory.createTitledBorder(" "));
        txtNgayNhap.setBorder(BorderFactory.createTitledBorder("Ngày Nhập"));
        txtGioNhap.setBorder(BorderFactory.createTitledBorder("Giờ Nhập"));
        txtTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));

        JPanel plChonNhanVien = new JPanel();
        plChonNhanVien.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        plChonNhanVien.add(txtMaNV);
        plChonNhanVien.add(btnChonNhanVien);

        JPanel plChonNhaCungCap = new JPanel();
        plChonNhaCungCap.setBorder(BorderFactory.createTitledBorder("Mã nhà cung cấp"));
        plChonNhaCungCap.add(txtMaNCC);
        plChonNhaCungCap.add(btnChonNhaCungCap);

        JPanel plInput = new JPanel();
        plInput.add(txtMaPN);
        plInput.add(plChonNhaCungCap);
        plInput.add(plChonNhanVien);
        plInput.add(txtNgayNhap);
        plInput.add(txtGioNhap);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm phiếu nhập");
            txtMaPN.setText("PN" + String.valueOf(qlpnBUS.getDspn().size() + 1));

            LocalDate ngayNhap = java.time.LocalDate.now();
            LocalTime gioNhap = java.time.LocalTime.now();

            this.txtNgayNhap.setText(String.valueOf(ngayNhap));
            this.txtGioNhap.setText(String.valueOf(gioNhap));

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa phiếu nhập");
            for (PhieuNhap pn : qlpnBUS.getDspn()) {
                if (pn.getMaPN().equals(_mapn)) {
                    this.pnSua = pn;
                }
            }
            if (this.pnSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy hóa đơn");
                this.dispose();
            }

            txtMaPN.setText(this.pnSua.getMaPN());
            txtMaNCC.setText(this.pnSua.getMaNCC());
            txtMaNV.setText(this.pnSua.getMaNV());
            txtNgayNhap.setText(String.valueOf(this.pnSua.getNgayNhap()));
            txtGioNhap.setText(String.valueOf(this.pnSua.getGioNhap()));
            txtTongTien.setText(String.valueOf(this.pnSua.getTongTien()));

            txtMaPN.setEditable(false);

            plInput.add(txtTongTien);

            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            plButton.add(btnSua);
        }

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemMouseClicked();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaMouseClicked();
        });
        btnHuy.addActionListener((ae) -> {
            this.dispose();
        });
        btnChonNhanVien.addActionListener((ActionEvent ae) -> {
            btnChonNhanVienMouseClicked();
        });
        btnChonNhaCungCap.addActionListener((ae) -> {
            btnChonNhaCungCapMouseClicked();
        });

        txtTongTien.setEditable(false);
        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String mapn = txtMaPN.getText();
            String mancc = txtMaNCC.getText();
            String manv = txtMaNV.getText();
            LocalDate ngayNhap = java.time.LocalDate.now();
            LocalTime gioNhap = java.time.LocalTime.now();
            float tongTien = 0;

            this.txtNgayNhap.setText(String.valueOf(ngayNhap));
            this.txtGioNhap.setText(String.valueOf(gioNhap));
            this.txtTongTien.setText(String.valueOf(tongTien));

            if (qlpnBUS.add(mapn, mancc, manv, ngayNhap, gioNhap, tongTien)) {
                JOptionPane.showMessageDialog(this, "Thêm phiếu nhập " + mapn + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String mapn = txtMaPN.getText();
            String mancc = txtMaNCC.getText();
            String manv = txtMaNV.getText();
            LocalDate ngayNhap = java.time.LocalDate.parse(txtNgayNhap.getText());
            LocalTime gioNhap = java.time.LocalTime.parse(txtGioNhap.getText());
            float tongTien = Float.parseFloat(txtTongTien.getText());

            if (qlpnBUS.update(mapn, mancc, manv, ngayNhap, gioNhap, tongTien)) {
                JOptionPane.showMessageDialog(this, "Sửa " + mapn + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnChonNhanVienMouseClicked() {
        ChonNhanVienForm cnv = new ChonNhanVienForm(txtMaNV);
    }

    private void btnChonNhaCungCapMouseClicked() {
        ChonNhaCungCapForm cncc = new ChonNhaCungCapForm(txtMaNCC);
    }

    private Boolean checkEmpty() {
        String mapn = txtMaPN.getText();
        String mancc = txtMaNCC.getText();
        String maNV = txtMaNV.getText();
        String ngayNhap = txtNgayNhap.getText();
        String gioNhap = txtGioNhap.getText();

        if (mapn.trim().equals("")) {
            return showErrorTx(txtMaPN, "Mã phiếu nhập không được để trống");

        } else if (mancc.trim().equals("")) {
            return showErrorTx(txtMaNCC, "Mã phiếu nhập không được để trống");

        } else if (maNV.trim().equals("")) {
            return showErrorTx(txtMaNV, "Mã phiếu nhập không được để trống");

        } else if (ngayNhap.trim().equals("")) {
            return showErrorTx(txtNgayNhap, "Ngày nhập không được để trống");

        } else if (gioNhap.trim().equals("")) {
            return showErrorTx(txtGioNhap, "Giờ nhập không được để trống");

        } else {
            try {
                LocalDate ngay = java.time.LocalDate.parse(ngayNhap);
            } catch (DateTimeParseException e) {
                return showErrorTx(txtNgayNhap, "Ngày lập không hợp lệ yyyy-mm-dd ( ví dụ: 2018-12-31)");
            }

            try {
                LocalTime gio = java.time.LocalTime.parse(gioNhap);
            } catch (DateTimeParseException e) {
                return showErrorTx(txtGioNhap, "Giờ lập không hợp lệ hh:mm (ví dụ: 18:25)");
            }
        }
        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}
