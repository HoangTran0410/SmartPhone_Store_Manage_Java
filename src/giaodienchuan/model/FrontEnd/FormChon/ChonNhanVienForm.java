package giaodienchuan.model.FrontEnd.FormChon;


import giaodienchuan.model.FrontEnd.FormHienThi.HienThiNhanVien;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChonNhanVienForm extends JFrame {
    
    HienThiNhanVien formHienThi = new HienThiNhanVien();

    JButton btnOK = new JButton("Chọn");
    JButton btnCancel = new JButton("Thoát");
    JTextField txMaNV;

    public ChonNhanVienForm(JTextField _txMaNV) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn nhân viên");
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        this.txMaNV = _txMaNV;

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
            String manv = formHienThi.getSelectedRow(1);
            if (manv != null) {
                this.txMaNV.setText(manv);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên nào!");
            }
        });
        
        btnCancel.addActionListener((ae) -> {
            this.dispose();
        });
    }
}
