package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
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

public class ThemChiTietHoaDonForm extends JFrame {

    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();

    JTextField txMasp = new JTextField(15);
    JTextField txMahd = new JTextField(15);
    JTextField txGia = new JTextField(15);
    JTextField txSoLuong = new JTextField(15);

    MoreButton btnChonSanPham = new MoreButton();

    JButton btnThem = new JButton("Thêm");
    JButton btnHuy = new JButton("Hủy");

    public ThemChiTietHoaDonForm(String _mahd) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // inputs
        txMasp.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        txMahd.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        txGia.setBorder(BorderFactory.createTitledBorder("Đơn Giá (triệu)"));

        JPanel pnlChonHoaDon = new JPanel();
        pnlChonHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        pnlChonHoaDon.add(txMahd);

        JPanel pnlChonSanPham = new JPanel();
        pnlChonSanPham.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        pnlChonSanPham.add(txMasp);
        pnlChonSanPham.add(btnChonSanPham);

        JPanel plInput = new JPanel();

//        plInput.add(txMahd);
        plInput.add(pnlChonHoaDon);
//        plInput.add(txMasp);
        plInput.add(pnlChonSanPham);
        plInput.add(txSoLuong);
        plInput.add(txGia);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        this.setTitle("Thêm chi tiết hóa đơn");
        txMahd.setText(_mahd);

        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        plButton.add(btnThem);

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemHoaDonMouseClicked();
        });
        btnHuy.addActionListener((ae) -> {
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy? Mọi giá trị nhập vào sẽ mất!", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                this.dispose();
            }
        });
        btnChonSanPham.addActionListener((ae) -> {
            btnChonSanPhamMouseClicked();
        });

        this.setVisible(true);
    }

    private void btnThemHoaDonMouseClicked() {
        if (checkEmpty()) {
            String mahd = txMahd.getText();
            String masp = txMasp.getText();
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoLuong.getText());

            if (qlcthdBUS.add(mahd, masp, soluong, dongia)) {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết cho " + mahd + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnChonSanPhamMouseClicked() {
        ChonSanPhamForm csp = new ChonSanPhamForm(txMasp,txGia,null,null,null); // truyền vào textfield
    }

    private Boolean checkEmpty() {
        String mssp = txMasp.getText();
        String mahd = txMahd.getText();
        String dongia = txGia.getText();
        String soluong = txSoLuong.getText();

        if (mssp.trim().equals("")) {
            return showErrorTx(txMasp, "Mã sp không được để trống");

        } else if (mahd.trim().equals("")) {
            return showErrorTx(txMahd, "Mã loại không được để trống");

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
