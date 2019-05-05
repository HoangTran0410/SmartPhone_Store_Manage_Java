package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.LoaiSanPham;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaLoaiSanPhamForm extends JFrame {

    String type;
    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();
    LoaiSanPham lspSua;

    JTextField txMalsp = new JTextField(15);
    JTextField txTenlsp = new JTextField(15);
    JTextArea txMota = new JTextArea(3, 15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaLoaiSanPhamForm(String _type, String _malsp) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMalsp.setBorder(BorderFactory.createTitledBorder("Mã loại"));
        txTenlsp.setBorder(BorderFactory.createTitledBorder("Tên loại"));
        txMota.setBorder(BorderFactory.createTitledBorder("Mô tả"));

        JPanel plInput = new JPanel();
        plInput.add(txMalsp);
        plInput.add(txTenlsp);
        plInput.add(txMota);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm loại sản phẩm");
            txMalsp.setText(qllspBUS.getNextID());

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa loại sản phẩm");
            for (LoaiSanPham lsp : qllspBUS.getDslsp()) {
                if (lsp.getMaLSP().equals(_malsp)) {
                    this.lspSua = lsp;
                }
            }
            if (this.lspSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy loại sản phẩm");
                this.dispose();
            }

            txMalsp.setText(this.lspSua.getMaLSP());
            txTenlsp.setText(this.lspSua.getTenLSP());
            txMota.setText(this.lspSua.getMoTa());

            txMalsp.setEditable(false);

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
            this.dispose();
        });

        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String malsp = txMalsp.getText();
            String tenlsp = txTenlsp.getText();
            String mota = txMota.getText();

            if (qllspBUS.add(malsp, tenlsp, mota)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tenlsp + " thành công!");
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String malsp = txMalsp.getText();
            String tenlsp = txTenlsp.getText();
            String mota = txMota.getText();

            if (qllspBUS.update(malsp, tenlsp, mota)) {
                JOptionPane.showMessageDialog(this, "Sửa " + malsp + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String malsp = txMalsp.getText();
        String tenlsp = txTenlsp.getText();
        String mota = txMota.getText();

        if (malsp.trim().equals("")) {
            return showErrorTx(txMalsp, "Mã loại sp không được để trống");

        } else if (tenlsp.trim().equals("")) {
            return showErrorTx(txTenlsp, "Tên loại không được để trống");

        } else if (mota.trim().equals("")) {
            return showErrorTx(txMota, "Mô tả không được để trống");
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
