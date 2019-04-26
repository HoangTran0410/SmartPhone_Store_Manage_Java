package giaodienchuan.model.FrontEnd.FormChon;

import giaodienchuan.model.FrontEnd.FormHienThi.HienThiQuyen;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChonQuyenForm extends JFrame {
    HienThiQuyen formHienThi = new HienThiQuyen();

    JButton btnOK = new JButton("Chọn");
    JButton btnCancel = new JButton("Thoát");
    JTextField txMaQuyen;
    JTextField txChiTietQuyen;

    public ChonQuyenForm(JTextField _txMaQuyen, JTextField _txChiTietQuyen) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn quyền");
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        this.txMaQuyen = _txMaQuyen;
        this.txChiTietQuyen = _txChiTietQuyen;

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
            String maq = formHienThi.getSelectedRow(1);
            String chitietquyen = formHienThi.getSelectedRow(2);
            if (maq != null) {
                if (this.txMaQuyen != null) {
                    this.txMaQuyen.setText(maq);
                }
                if (this.txChiTietQuyen != null) {
                    this.txChiTietQuyen.setText(chitietquyen);
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn quyền nào!");
            }

        });

        btnCancel.addActionListener((ae) -> {
            this.dispose();
        });
    }
}
