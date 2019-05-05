/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormThemSua;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.KhuyenMai;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.QuanLyKhuyenMaiBUS;
import giaodienchuan.model.FrontEnd.MyButton.DateButton;
import java.awt.BorderLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class ThemSuaKhuyenMaiForm extends JFrame {

    String type;
    QuanLyKhuyenMaiBUS qlkhBUS = new QuanLyKhuyenMaiBUS();
    KhuyenMai kmSua;

    JTextField txMaKM = new JTextField(15);
    JTextField txTenKM = new JTextField(15);
    JTextField txDieuKienKhuyenMai = new JTextField(15);
    JTextField txPhanTramKhuyenMai = new JTextField(15);
    JTextField txNgayBD = new JTextField(15);
    JTextField txNgayKT = new JTextField(15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    DatePicker dPickerNgayBD;
    DatePicker dPickerNgayKT;

    public ThemSuaKhuyenMaiForm(String _type, String _makm) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // date picker
        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPickerNgayBD = new DatePicker(pickerSettings);
        dPickerNgayBD.setDateToToday();
        DateButton db = new DateButton(dPickerNgayBD);

        DatePickerSettings pickerSettings2 = new DatePickerSettings();
        pickerSettings2.setVisibleDateTextField(false);
        dPickerNgayKT = new DatePicker(pickerSettings2);
        DateButton dk = new DateButton(dPickerNgayKT);

        JPanel plNgayBD = new JPanel();
        plNgayBD.setBorder(BorderFactory.createTitledBorder("Ngày bắt đầu"));
        plNgayBD.add(txNgayBD);
        plNgayBD.add(dPickerNgayBD);

        JPanel plNgayKT = new JPanel();
        plNgayKT.setBorder(BorderFactory.createTitledBorder("Ngày kết thúc"));
        plNgayKT.add(txNgayKT);
        plNgayKT.add(dPickerNgayKT);

        // inputs
        txMaKM.setBorder(BorderFactory.createTitledBorder("Mã khuyến mãi"));
        txTenKM.setBorder(BorderFactory.createTitledBorder("Tên khuyến mãi"));
        txDieuKienKhuyenMai.setBorder(BorderFactory.createTitledBorder("Điều kiện khuyến mãi"));
        txPhanTramKhuyenMai.setBorder(BorderFactory.createTitledBorder("Phần trăm khuyến mãi"));
        txNgayBD.setBorder(BorderFactory.createTitledBorder(" "));
        txNgayKT.setBorder(BorderFactory.createTitledBorder(" "));

        JPanel plInput = new JPanel();
        plInput.add(txMaKM);
        plInput.add(txTenKM);
        plInput.add(txDieuKienKhuyenMai);
        plInput.add(txPhanTramKhuyenMai);
        plInput.add(plNgayBD);
        plInput.add(plNgayKT);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm khuyến mãi");
            txMaKM.setText(qlkhBUS.getNextID());

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa khuyến mãi");
            for (KhuyenMai km : qlkhBUS.getDskm()) {
                if (km.getMaKM().equals(_makm)) {
                    this.kmSua = km;
                }
            }
            if (this.kmSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy khuyến mãi");
                this.dispose();
            }

            txMaKM.setText(this.kmSua.getMaKM());
            txTenKM.setText(this.kmSua.getTenKM());
            txDieuKienKhuyenMai.setText(String.valueOf(this.kmSua.getDieuKienKM()));
            txPhanTramKhuyenMai.setText(String.valueOf(this.kmSua.getPhanTramKM()));
            txNgayBD.setText(this.kmSua.getNgayBD().toString());
            dPickerNgayBD.setDate(this.kmSua.getNgayBD());
            txNgayKT.setText(this.kmSua.getNgayKT().toString());
            dPickerNgayKT.setDate(this.kmSua.getNgayKT());

            txMaKM.setEditable(false);

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
        dPickerNgayBD.addDateChangeListener((dce) -> {
            txNgayBD.setText(dPickerNgayBD.getDateStringOrEmptyString());
        });
        dPickerNgayKT.addDateChangeListener((dce) -> {
            txNgayKT.setText(dPickerNgayKT.getDateStringOrEmptyString());
        });

        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String makm = txMaKM.getText();
            String tenkm = txTenKM.getText();
            float dieukien = Float.parseFloat(txDieuKienKhuyenMai.getText());
            float phantram = Float.parseFloat(txPhanTramKhuyenMai.getText());
            LocalDate ngaybd = LocalDate.parse(txNgayBD.getText());
            LocalDate ngaykt = LocalDate.parse(txNgayKT.getText());

            if (qlkhBUS.add(makm, tenkm, dieukien, phantram, ngaybd, ngaykt)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tenkm + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String makm = txMaKM.getText();
            String tenkm = txTenKM.getText();
            float dieukien = Float.parseFloat(txDieuKienKhuyenMai.getText());
            float phantram = Float.parseFloat(txPhanTramKhuyenMai.getText());
            LocalDate ngaybd = LocalDate.parse(txNgayBD.getText());
            LocalDate ngaykt = LocalDate.parse(txNgayKT.getText());

            if (qlkhBUS.update(makm, tenkm, dieukien, phantram, ngaybd, ngaykt)) {
                JOptionPane.showMessageDialog(this, "Sửa " + makm + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String makm = txMaKM.getText();
        String tenkm = txTenKM.getText();
        String dieukien = txDieuKienKhuyenMai.getText();
        String phantram = txPhanTramKhuyenMai.getText();
        String ngaybd = txNgayBD.getText();
        String ngaykt = txNgayKT.getText();

        if (makm.trim().equals("")) {
            return showErrorTx(txMaKM, "Mã khuyến mãi không được để trống");

        } else if (tenkm.trim().equals("")) {
            return showErrorTx(txTenKM, "Tên khuyến mãi không được để trống");

        } else if (dieukien.trim().equals("")) {
            return showErrorTx(txTenKM, "Điều kiện khuyến mãi không được để trống");

        } else if (phantram.trim().equals("")) {
            return showErrorTx(txTenKM, "Phần trăm khuyến mãi không được để trống");

        } else {
            try {
                Float.parseFloat(dieukien);
            } catch (NumberFormatException e) {
                return showErrorTx(txDieuKienKhuyenMai, "Điều kiện khuyến mãi là giá hóa đơn tối thiểu để được khuyến mãi, phải là số thực");
            }
            try {
                float fPhanTram = Float.parseFloat(phantram);
                if (fPhanTram > 100) {
                    return showErrorTx(txPhanTramKhuyenMai, "Phần trăm khuyến mãi phải là số thực < 100 (%)");
                }
            } catch (NumberFormatException e) {
                return showErrorTx(txPhanTramKhuyenMai, "Phần trăm khuyến mãi phải là số thực");
            }
            try {
                LocalDate.parse(ngaybd);
            } catch (Exception e) {
                return showErrorTx(txNgayBD, "Ngày bắt đầu không hợp lệ");
            }
            try {
                LocalDate.parse(ngaykt);
            } catch (Exception e) {
                return showErrorTx(txNgayKT, "Ngày kết thúc không hợp lệ");
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
