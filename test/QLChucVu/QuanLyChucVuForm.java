package giaodienchuan.model.FrontEnd.Test.QLChucVu;

import giaodienchuan.model.FrontEnd.Test.QLChucVu.ChucVu.ChucVuBUS;
import giaodienchuan.model.FrontEnd.Test.QLChucVu.ThemSuaChucVuForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyChucVuForm extends JPanel {

    HienThiChucVu formHienThi = new HienThiChucVu();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnXoa = new JButton("Xóa");

    public QuanLyChucVuForm() {
        setLayout(new BorderLayout());

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);

        this.add(plBtn, BorderLayout.NORTH);
        this.add(formHienThi, BorderLayout.CENTER);

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
        String macv = formHienThi.getSelectedChucVu();
        if (macv != null) {
            ThemSuaChucVuForm suacv = new ThemSuaChucVuForm("Sửa", macv);

            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            suacv.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn chức vụ nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String macv = formHienThi.getSelectedChucVu();
        if (macv != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa chức vụ " + macv + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new ChucVuBUS().delete(macv);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn chức vụ nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaChucVuForm themcv = new ThemSuaChucVuForm("Thêm", "");
        themcv.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
