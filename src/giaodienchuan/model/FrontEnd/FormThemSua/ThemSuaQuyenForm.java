package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyQuyen.QuanLyQuyenBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.Quyen;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaQuyenForm extends JFrame {

    String type;
    QuanLyQuyenBUS qlqBUS = new QuanLyQuyenBUS();
    Quyen qSua;

    JTextField txMaQuyen = new JTextField(15);
    JTextField txTenQuyen = new JTextField(15);
    JTextArea txChiTietQuyen = new JTextArea(4, 15);

    JButton btnThem = new JButton("Thêm");
    JButton btnThoat = new JButton("Thoát");

    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaQuyenForm(String _type, String _maq) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMaQuyen.setBorder(BorderFactory.createTitledBorder("Mã quyền"));
        txTenQuyen.setBorder(BorderFactory.createTitledBorder("Tên quyền"));
        txChiTietQuyen.setBorder(BorderFactory.createTitledBorder("Chi tiết quyền"));

        JPanel plInput = new JPanel();
        plInput.add(txMaQuyen);
        plInput.add(txTenQuyen);
        plInput.add(txChiTietQuyen);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm quyền");
            txMaQuyen.setText("Q" + String.valueOf(qlqBUS.getDsq().size() + 1));

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            btnThoat.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
            plButton.add(btnThem);
            plButton.add(btnThoat);

        } else {
            this.setTitle("Sửa quyền");
            for (Quyen q : qlqBUS.getDsq()) {
                if (q.getMaQuyen().equals(_maq)) {
                    this.qSua = q;
                }
            }
            if (this.qSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy quyền");
                this.dispose();
            }

            txMaQuyen.setText(this.qSua.getMaQuyen());
            txTenQuyen.setText(this.qSua.getTenQuyen());
            txChiTietQuyen.setText(this.qSua.getChiTietQuyen());

            txMaQuyen.setEditable(false);

            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
            plButton.add(btnSua);
            plButton.add(btnHuy);
        }

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemMouseClicked();
        });
        btnThoat.addActionListener((ae) -> {
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy? Mọi giá trị nhập vào sẽ mất!", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                this.dispose();
            }
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
            String maquyen = txMaQuyen.getText();
            String tenquyen = txTenQuyen.getText();
            String chitietquyen = txChiTietQuyen.getText();

            if (qlqBUS.add(maquyen, tenquyen, chitietquyen)) {
                JOptionPane.showMessageDialog(this, "Thêm " + maquyen + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String maquyen = txMaQuyen.getText();
            String tenquyen = txTenQuyen.getText();
            String chitietquyen = txChiTietQuyen.getText();

            if (qlqBUS.update(maquyen, tenquyen, chitietquyen)) {
                JOptionPane.showMessageDialog(this, "Sửa " + maquyen + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String maquyen = txMaQuyen.getText();
        String tenquyen = txTenQuyen.getText();
        String chitietquyen = txChiTietQuyen.getText();

        if (maquyen.trim().equals("")) {
            return showErrorTx(txMaQuyen, "Mã quyền không được để trống");
            
        } else if(tenquyen.trim().equals("")) {
            return showErrorTx(txMaQuyen, "Tên quyền không được để trống");
            
        } else if (chitietquyen.trim().equals("")) {
            return showErrorTx(txChiTietQuyen, "Chi tiết quyền không được để trống");
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
