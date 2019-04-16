package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiHoaDon_ChiTietHoaDon;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaHoaDonForm;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaSanPhamForm;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class HoaDon_ChiTietHoaDon_form extends JPanel {

    HienThiHoaDon_ChiTietHoaDon formHienThi = new HienThiHoaDon_ChiTietHoaDon();
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnXoaHoaDon = new JButton("Xóa");
    public HoaDon_ChiTietHoaDon_form(){
        setLayout(new BorderLayout());

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoaHoaDon.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoaHoaDon);
        plBtn.add(btnSua);
        plBtn.setBackground(new Color(150, 150, 150));
        
        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.SOUTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoaHoaDon.addActionListener((ActionEvent ae) -> {
            btnXoaHoaDonMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
                        formHienThi.refresh();
        });
    }

    private void btnSuaMouseClicked() {
        String masp = formHienThi.getSelectedSanPham();
        if (masp != null) {
            ThemSuaSanPhamForm suasp = new ThemSuaSanPhamForm("Sửa", masp);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để sửa");
        }
    }

    private void btnXoaHoaDonMouseClicked() {
        String masp = formHienThi.getSelectedSanPham();
        if (masp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa hóa đơn " + masp + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyHoaDonBUS().delete(masp);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaHoaDonForm themsp = new ThemSuaHoaDonForm("Thêm", "");
    }
}
