package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.QuanLyTaiKhoanBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiTaiKhoan;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaTaiKhoanForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyTaiKhoanForm extends JPanel {

    HienThiTaiKhoan formHienThi = new HienThiTaiKhoan();

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");

    public QuanLyTaiKhoanForm(String quyenHan) {
        setLayout(new BorderLayout());

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
        
        if(!quyenHan.contains("qlTaiKhoan")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);

        //=========== add all to this jpanel ===========
        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.NORTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
        });
    }

    private void btnSuaMouseClicked() {
        String masp = formHienThi.getSelectedSanPham();
        if (masp != null) {
            ThemSuaTaiKhoanForm suatk = new ThemSuaTaiKhoanForm("Sửa", masp);

            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            suatk.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn tài khoản nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String malsp = formHienThi.getSelectedSanPham();
        if (malsp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tài khoản " + malsp + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyTaiKhoanBUS().delete(malsp);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn tài khoản nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaTaiKhoanForm themtk = new ThemSuaTaiKhoanForm("Thêm", "");

        themtk.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
