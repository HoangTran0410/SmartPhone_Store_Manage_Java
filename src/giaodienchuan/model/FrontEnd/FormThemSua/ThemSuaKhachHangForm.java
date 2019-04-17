package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaKhachHangForm extends JFrame {

    String type;
    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();
    KhachHang khSua;

    JTextField txMakh = new JTextField(15);
    JTextField txTenkh = new JTextField(15);
    JTextField txDiachi = new JTextField(15);
    JTextField txSDT = new JTextField(15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaKhachHangForm(String _type, String _makh) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMakh.setBorder(BorderFactory.createTitledBorder("Mã khách hàng"));
        txTenkh.setBorder(BorderFactory.createTitledBorder("Tên khách hàng"));
        txDiachi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));
        txSDT.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));

        JPanel plInput = new JPanel();
        plInput.add(txMakh);
        plInput.add(txTenkh);
        plInput.add(txDiachi);
        plInput.add(txSDT);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm khách hàng");
            txMakh.setText("KH" + String.valueOf(qlkhBUS.getDskh().size() + 1));

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa khách hàng");
            for (KhachHang kh : qlkhBUS.getDskh()) {
                if (kh.getMaKH().equals(_makh)) {
                    this.khSua = kh;
                }
            }
            if (this.khSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy khách hàng");
                this.dispose();
            }

            txMakh.setText(this.khSua.getMaKH());
            txTenkh.setText(this.khSua.getTenKH());
            txDiachi.setText(this.khSua.getDiaChi());
            txSDT.setText(this.khSua.getSDT());

            txMakh.setEditable(false);

            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            plButton.add(btnSua);
        }

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemMouseClicked();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaMouseClicked();
        });
        btnHuy.addActionListener((ae) -> {
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy? Mọi giá trị nhập vào sẽ mất!", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                this.dispose();
            }
        });

        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String makh = txMakh.getText();
            String tenkh = txTenkh.getText();
            String diachi = txDiachi.getText();
            String  sdt = txSDT.getText();

            if (qlkhBUS.add(makh, tenkh, diachi, sdt)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tenkh + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String makh = txMakh.getText();
            String tenkh = txTenkh.getText();
            String diachi = txDiachi.getText();
            String  sdt = txSDT.getText();

            if (qlkhBUS.update(makh, tenkh, diachi, sdt)) {
                JOptionPane.showMessageDialog(this, "Sửa " + makh + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String makh = txMakh.getText();
        String tenkh = txTenkh.getText();
        String diachi = txDiachi.getText();
        String  sdt = txSDT.getText();

        if (makh.trim().equals("")) {
            return showErrorTx(txMakh, "Mã khách hàng không được để trống");

        } else if (tenkh.trim().equals("")) {
            return showErrorTx(txTenkh, "Tên khách hàng không được để trống");
        
        } else if (diachi.trim().equals("")) {
            return showErrorTx(txTenkh, "Địa chỉ không được để trống");

        } else if (sdt.trim().equals("")) {
            return showErrorTx(txTenkh, "Số điện thoại không được để trống");
        }

        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }

    private Boolean showErrorTx(JTextArea tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}
