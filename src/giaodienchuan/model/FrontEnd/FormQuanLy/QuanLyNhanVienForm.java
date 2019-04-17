package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiNhanVien;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaNhanVienForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyNhanVienForm extends JPanel {

    HienThiNhanVien formHienThi = new HienThiNhanVien();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnXoa = new JButton("Xóa");
    JComboBox<String> cbTypeSearch;
    final int MANV_I = 1, MACV_I = 2, TENNV_I = 3, NGAYSINH_I=4, DIACHI_I=5, SDT_I=6;
    public QuanLyNhanVienForm() {
        setLayout(new BorderLayout());

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.setBackground(new Color(150, 150, 150));

        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.SOUTH);

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
        String manv = formHienThi.getSelectedNhanVien();
        if (manv != null) {
            ThemSuaNhanVienForm suanv = new ThemSuaNhanVienForm("Sửa", manv);
            
            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            suanv.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String manv = formHienThi.getSelectedNhanVien();
        if (manv != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên " + manv + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyNhanVienBUS().delete(manv);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaNhanVienForm themnv = new ThemSuaNhanVienForm("Thêm", "");
        themnv.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
