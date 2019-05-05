package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ThemSuaNhaCungCapForm extends JFrame {

    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
    String type;

    NhaCungCap nccSua;
    JTextField txMaNCC = new JTextField(10);
    JTextField txTenNCC = new JTextField(10);
    JTextField txDiaChi = new JTextField(10);
    JTextField txSDT = new JTextField(10);
    JTextField txFax = new JTextField(10);
    JTextField txTim = new JTextField(15);

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaNhaCungCapForm(String _type, String _mancc) {

        this.setLayout(new BorderLayout());
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        txMaNCC.setBorder(BorderFactory.createTitledBorder("Mã nhà cung cấp"));
        txTenNCC.setBorder(BorderFactory.createTitledBorder("Tên nhà cung cấp"));
        txDiaChi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));
        txSDT.setBorder(BorderFactory.createTitledBorder("SDT"));
        txFax.setBorder(BorderFactory.createTitledBorder("Fax"));

        JPanel plInput = new JPanel();
        plInput.add(txMaNCC);
        plInput.add(txTenNCC);
        plInput.add(txDiaChi);
        plInput.add(txSDT);
        plInput.add(txFax);

        JPanel plButton = new JPanel();
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm nhà cung cấp");
            txMaNCC.setText(qlnccBUS.getNextID());

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa nhà cung cấp");
            for (NhaCungCap ncc : qlnccBUS.getDsncc()) {
                if (ncc.getMaNCC().equals(_mancc)) {
                    this.nccSua = ncc;
                }
            }
            if (this.nccSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy nhà cung cấp");
                this.dispose();
            }

            txMaNCC.setText(this.nccSua.getMaNCC());
            txTenNCC.setText(this.nccSua.getTenNCC());
            txDiaChi.setText(this.nccSua.getDiaChi());
            txSDT.setText(String.valueOf(this.nccSua.getSDT()));
            txFax.setText(String.valueOf(this.nccSua.getFax()));

            txMaNCC.setEditable(false);

            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            plButton.add(btnSua);
        }
        
        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

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

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String maNCC = txMaNCC.getText();
            String tenNCC = txTenNCC.getText();
            String diaChi = txDiaChi.getText();
            String SDT = txSDT.getText();
            String Fax = txFax.getText();

            if (qlnccBUS.update(maNCC, tenNCC, diaChi, SDT, Fax)) {
                JOptionPane.showMessageDialog(this, "Sửa " + maNCC + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            NhaCungCap ncc = new NhaCungCap(txMaNCC.getText(), txTenNCC.getText(), txDiaChi.getText(), txSDT.getText(), txFax.getText());
            if (qlnccBUS.add(ncc)) {
                JOptionPane.showMessageDialog(this, "Thêm " + txTenNCC.getText() + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String ma = txMaNCC.getText();
        String ten = txTenNCC.getText();
        String diachi = txDiaChi.getText();
        String sdt = txSDT.getText();
        String fax = txFax.getText();
        if (ma.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mã nhà cung cấp không được để trống");
            txMaNCC.requestFocus();
            return false;
        } else if (ten.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên nhà cung cấp không được để trống");
            txTenNCC.requestFocus();
            return false;
        } else if (diachi.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Địa chỉ nhà cung cấp không được để trống");
            txDiaChi.requestFocus();
            return false;
        } else if (sdt.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại nhà cung cấp không được để trống");
            txSDT.requestFocus();
            return false;
        } else if (fax.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Số fax nhà cung cấp không được để trống");
            txFax.requestFocus();
            return false;
        }
        return true;
    }
}
