package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.WorkWithExcel.XuatExcel;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiNhanVien;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaNhanVienForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import giaodienchuan.model.FrontEnd.MyButton.ExportExcelButton;
import giaodienchuan.model.FrontEnd.MyButton.SuaButton;
import giaodienchuan.model.FrontEnd.MyButton.ThemButton;
import giaodienchuan.model.FrontEnd.MyButton.XoaButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyNhanVienForm extends JPanel {

    HienThiNhanVien formHienThi = new HienThiNhanVien();

    ThemButton btnThem = new ThemButton();
    SuaButton btnSua = new SuaButton();
    XoaButton btnXoa = new XoaButton();
    ExportExcelButton btnXuatExcel = new ExportExcelButton();

    public QuanLyNhanVienForm() {
        setLayout(new BorderLayout());

        // buttons
        if (!LoginForm.quyenLogin.getChiTietQuyen().contains("qlNhanVien")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnXuatExcel);

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
        btnXuatExcel.addActionListener((ActionEvent ae) -> {
            new XuatExcel().xuatFileExcelNhanVien();
        });
    }

    private void btnSuaMouseClicked() {
        String manv = formHienThi.getSelectedRow(1);
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
        String manv = formHienThi.getSelectedRow(1);
        if (manv != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên " + manv + " ? Nhân viên sẽ được Ẩn.", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyNhanVienBUS().updateTrangThai(manv, 1);
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
