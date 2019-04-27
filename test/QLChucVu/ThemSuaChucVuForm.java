package giaodienchuan.model.FrontEnd.Test.QLChucVu;

import giaodienchuan.model.FrontEnd.Test.QLChucVu.ChucVu.ChucVu;
import giaodienchuan.model.FrontEnd.Test.QLChucVu.ChucVu.ChucVuBUS;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaChucVuForm extends JFrame {

    String type;
    ChucVuBUS cvBUS = new ChucVuBUS();
    ChucVu cvSua;

    JTextField txMacv = new JTextField(15);
    JTextField txTencv = new JTextField(15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaChucVuForm(String _type, String _macv) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMacv.setBorder(BorderFactory.createTitledBorder("Mã chức vụ"));
        txTencv.setBorder(BorderFactory.createTitledBorder("Tên chức vụ"));
    
        JPanel plInput = new JPanel();
        plInput.add(txMacv);
        plInput.add(txTencv);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm chức vụ");
            txMacv.setText("CV" + String.valueOf(cvBUS.getDscv().size() + 1));

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa chức vụ");
            for (ChucVu cv : cvBUS.getDscv()) {
                if (cv.getMaCV().equals(_macv)) {
                    this.cvSua = cv;
                }
            }
            if (this.cvSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy chức vụ");
                this.dispose();
            }

            txMacv.setText(this.cvSua.getMaCV());
            txTencv.setText(this.cvSua.getTenCV());
         
            txMacv.setEditable(false);

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
            String macv = txMacv.getText();
            String tencv = txTencv.getText();
            if (cvBUS.add(macv, tencv)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tencv + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String macv = txMacv.getText();
            String tencv = txTencv.getText();

            if (cvBUS.update(macv, tencv)) {
                JOptionPane.showMessageDialog(this, "Sửa " + macv + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String macv = txMacv.getText();
        String tencv = txTencv.getText();

        if (macv.trim().equals("")) {
            return showErrorTx(txMacv, "Mã chức vụ không được để trống");

        } else if (tencv.trim().equals("")) {
            return showErrorTx(txTencv, "Tên chức vụ không được để trống");
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
