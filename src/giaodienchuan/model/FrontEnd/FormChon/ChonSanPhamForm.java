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
    JTextField txTarget1;
    JTextField txTarget2;

    public ChonSanPhamForm(JTextField _txTarget) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn Sản Phẩm");
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        this.txTarget1 = _txTarget;

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
            String masp = formHienThi.getSelectedSanPham();
            if (masp != null) {
                this.txTarget1.setText(masp);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm nào!");
            }
        });
        
        btnCancel.addActionListener((ae) -> {
            this.dispose();
        });
    }
        public ChonSanPhamForm(JTextField _txTarget1,JTextField _txTarget2) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn Sản Phẩm");
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        this.txTarget1 = _txTarget1;
        this.txTarget2 = _txTarget2;

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
            String masp = formHienThi.getSelectedSanPham();
            String donGia = formHienThi.getDonGia();
            if (masp != null) {
                this.txTarget1.setText(masp);
                this.txTarget2.setText(donGia);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm nào!");
            }
        });
        
        btnCancel.addActionListener((ae) -> {
            this.dispose();
        });
    }
}
