package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiSanPham;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaSanPhamForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLySanPhamForm extends JPanel {

    HienThiSanPham formHienThi = new HienThiSanPham();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnXoa = new JButton("Xóa");

    public QuanLySanPhamForm() {
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
                        formHienThi.refresh();
        });
    }

    private void btnSuaMouseClicked() {
        String masp = formHienThi.getSelectedSanPham(1);
        if (masp != null) {
            ThemSuaSanPhamForm suasp = new ThemSuaSanPhamForm("Sửa", masp);
            
            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            suasp.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String masp = formHienThi.getSelectedSanPham(1);
        if (masp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm " + masp + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLySanPhamBUS().delete(masp);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaSanPhamForm themsp = new ThemSuaSanPhamForm("Thêm", "");
        themsp.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
