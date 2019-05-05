/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyChiTietPN.ChiTietPhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.QuanLyChiTietPhieuNhapBUS;
import giaodienchuan.model.FrontEnd.FormChon.ChonSanPhamForm;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
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
public class ThemSuaChiTietPhieuNhapForm extends JFrame {

    QuanLyChiTietPhieuNhapBUS qlctpnBUS = new QuanLyChiTietPhieuNhapBUS();
    String type, mapn, masp;
    JTextField txMasp = new JTextField(15);
    JTextField txMapn = new JTextField(15);
    JTextField txGia = new JTextField(15);
    JTextField txSoLuong = new JTextField(15);
    ChiTietPhieuNhap ctpnSua;

    MoreButton btnChonSanPham = new MoreButton();

    JButton btnThem = new JButton("Thêm");
    JButton btnHuy = new JButton("Hủy");
    JButton btnSua = new JButton("Sửa");

    public ThemSuaChiTietPhieuNhapForm(String _type, String _mapn, String _masp) {
        this.setLayout(new BorderLayout());
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.type = _type;
        this.mapn = _mapn;
        this.masp = _masp;

        // inputs
        txMapn.setBorder(BorderFactory.createTitledBorder("Mã phiếu nhập"));
        txMasp.setBorder(BorderFactory.createTitledBorder(" "));
        txGia.setBorder(BorderFactory.createTitledBorder("Đơn Giá (triệu)"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));

        txMapn.setEditable(false);
        txGia.setEditable(false);

        JPanel pnlChonSanPham = new JPanel();
        pnlChonSanPham.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        pnlChonSanPham.add(txMasp);
        pnlChonSanPham.add(btnChonSanPham);

        JPanel plInput = new JPanel();
        plInput.add(txMapn);
        plInput.add(pnlChonSanPham);
        plInput.add(txGia);
        plInput.add(txSoLuong);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm chi tiết phiếu nhập " + this.mapn);
            txMapn.setText(this.mapn);

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa chi tiết " + this.masp + " phiếu nhập " + this.mapn);

            this.ctpnSua = qlctpnBUS.getChiTiet(this.mapn, this.masp); //cai này sao bằng null ??

            if (this.ctpnSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy chi tiết hóa đơn");
                this.dispose();
            }
            txMapn.setText(this.ctpnSua.getMa());
            txMapn.setEditable(false);
            txSoLuong.setText(String.valueOf(this.ctpnSua.getSoLuong()));
            txMasp.setText(this.masp);

            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            plButton.add(btnSua);
        }

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemChiTietPhieuNhapMouseClicked();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaChiTietPhieuNhapMouseClicked();
        });
        btnHuy.addActionListener((ae) -> {
            this.dispose();
        });
        btnChonSanPham.addActionListener((ae) -> {
            btnChonSanPhamMouseClicked();
        });

        this.setVisible(true);
    }

    private void btnThemChiTietPhieuNhapMouseClicked() {
        if (checkEmpty()) {
            String maSP = txMasp.getText();
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoLuong.getText());

            if (soluong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng sản phẩm không hợp lệ");
                return;
            }

            if (qlctpnBUS.add(mapn, maSP, soluong, dongia)) {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết cho " + mapn + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaChiTietPhieuNhapMouseClicked() {
        if (checkEmpty()) {
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoLuong.getText());

            if (qlctpnBUS.update(this.mapn, this.masp, soluong, dongia)) {
                JOptionPane.showMessageDialog(this, "Sửa " + mapn + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnChonSanPhamMouseClicked() {
        ChonSanPhamForm csp = new ChonSanPhamForm(txMasp, null, null, txGia, txSoLuong); // truyền vào textfield
    }

    private Boolean checkEmpty() {
        String mssp = txMasp.getText();
        String mapn = txMapn.getText();
        String dongia = txGia.getText();
        String soluong = txSoLuong.getText();

        if (mssp.trim().equals("")) {
            return showErrorTx(txMasp, "Mã sp không được để trống");

        } else if (mapn.trim().equals("")) {
            return showErrorTx(txMapn, "Mã loại không được để trống");

        } else if (dongia.trim().equals("")) {
            return showErrorTx(txGia, "Đơn giá không được để trống");

        } else if (soluong.trim().equals("")) {
            return showErrorTx(txSoLuong, "Số lượng không được để trống");

        } else {
            try {
                float dg = Float.parseFloat(dongia);
            } catch (NumberFormatException e) {
                return showErrorTx(txGia, "Đơn giá không hợp lệ (phải là số thực)");
            }

            try {
                int sl = Integer.parseInt(soluong);
                if (sl < 0) {
                    return showErrorTx(txSoLuong, "Số lượng không hợp lệ (phải là số duơng)");
                }
            } catch (NumberFormatException e) {
                return showErrorTx(txSoLuong, "Số lượng không hợp lệ (phải là số nguyên)");
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
