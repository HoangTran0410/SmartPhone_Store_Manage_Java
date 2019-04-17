package giaodienchuan.model.FrontEnd.FormChon;


import giaodienchuan.model.FrontEnd.FormHienThi.HienThiSanPham;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChonSanPhamForm extends JFrame {
    
    HienThiSanPham formHienThi = new HienThiSanPham();

    JButton btnOK = new JButton("Chọn");
    JButton btnCancel = new JButton("Thoát");
    JTextField txMasp;
    JTextField txMalsp;
    JTextField txTensp;
    JTextField txDonGia;
    JTextField txSoluong;

    public ChonSanPhamForm(JTextField _masp,JTextField _malsp,JTextField _tensp,JTextField _donGia,JTextField _soLuong) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn Sản Phẩm");
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        this.txMasp = _masp;
        this.txMalsp = _malsp;
        this.txTensp = _tensp;
        this.txDonGia = _donGia;
        this.txSoluong = _soLuong;

        // ======= Buttons Panel ===========
        btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnOK.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_ok_30px.png")));
        
        JPanel plBtns = new JPanel();
        plBtns.add(btnOK);
        plBtns.add(btnCancel);
        
        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtns, BorderLayout.SOUTH);
        this.setVisible(true);

        btnOK.addActionListener((ActionEvent ae) -> {
            if (formHienThi.getSelectedSanPham(1) != null) {
                this.txMasp.setText(formHienThi.getSelectedSanPham(1));
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm nào!");
            }
            if (formHienThi.getSelectedSanPham(2) != null) {
                this.txMalsp.setText(formHienThi.getSelectedSanPham(2));
                this.dispose();
            }
            if (formHienThi.getSelectedSanPham(3) != null) {
                this.txTensp.setText(formHienThi.getSelectedSanPham(3));
                this.dispose();
            }
            if (formHienThi.getSelectedSanPham(4) != null) {
                this.txDonGia.setText(formHienThi.getSelectedSanPham(4));
                this.dispose();
            }
            if (formHienThi.getSelectedSanPham(5) != null) {
                this.txSoluong.setText(formHienThi.getSelectedSanPham(5));
                this.dispose();
            }
        });
        
        btnCancel.addActionListener((ae) -> {
            this.dispose();
        });
    }
}
