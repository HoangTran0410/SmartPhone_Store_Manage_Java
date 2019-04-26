package giaodienchuan.model.FrontEnd.FormChon;

import giaodienchuan.model.FrontEnd.FormHienThi.HienThiLoaiSanPham;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChonLoaiSanPhamForm extends JFrame {

    HienThiLoaiSanPham formHienThi = new HienThiLoaiSanPham();

    JButton btnOK = new JButton("Chọn");
    JButton btnCancel = new JButton("Thoát");
    JTextField txTarget;

    public ChonLoaiSanPhamForm(JTextField _txTarget) {
        this.setTitle("Chọn Loại Sản Phẩm");
        this.setLayout(new BorderLayout());
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        this.txTarget = _txTarget;

        // ======= Buttons Panel ===========
        btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnOK.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_ok_30px.png")));
        
        JPanel plBtns = new JPanel();
        plBtns.add(btnOK);
        plBtns.add(btnCancel);
        
        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtns, BorderLayout.SOUTH);
        this.setVisible(true);

        // actionlistener
        btnOK.addActionListener((ActionEvent ae) -> {
            String masp = formHienThi.getSelectedRow(1);
            if (masp != null) {
                this.txTarget.setText(masp);
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
