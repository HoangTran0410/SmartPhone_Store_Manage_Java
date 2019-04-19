package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiChiTietHoaDon;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemChiTietHoaDonForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyChiTietHoaDonForm extends JFrame {

    HienThiChiTietHoaDon formHienThi;
    
    String mahd;
    
    JButton btnThem = new JButton("Thêm");
    JButton btnXoa = new JButton("Xóa");

    public QuanLyChiTietHoaDonForm(String _mahd) {
        setLayout(new BorderLayout());
        
        this.mahd = _mahd;
        this.setTitle("Chi tiết hóa đơn " + this.mahd);
        formHienThi = new HienThiChiTietHoaDon(this.mahd);

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);

        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.NORTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    private void btnXoaMouseClicked() {
        String masp = formHienThi.getSelectedChiTietHoaDon(2);
        if (masp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm " + masp + " của hóa đơn "+ this.mahd +"?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyChiTietHoaDonBUS().delete(this.mahd, masp);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn chi tiết nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemChiTietHoaDonForm themcthd = new ThemChiTietHoaDonForm(this.mahd);
        themcthd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                formHienThi.refresh();
            }
        });
    }
}
