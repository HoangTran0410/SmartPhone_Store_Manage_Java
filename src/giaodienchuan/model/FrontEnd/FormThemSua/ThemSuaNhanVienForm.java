package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.FrontEnd.FormChon.ChonChucVuForm;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ThemSuaNhanVienForm extends JFrame {

    String type;
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();
    NhanVien nvSua;

    JTextField txManv = new JTextField(12);
    JTextField txMacv = new JTextField(12);
    JTextField txTennv = new JTextField(15);
    JTextField txNgaysinh = new JTextField(12);
    JTextField txDiachi = new JTextField(15);
    JTextField txSDT= new JTextField(12);
    
    MoreButton btnChonChucVu = new MoreButton();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaNhanVienForm(String _type, String _manv) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txManv.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        txMacv.setBorder(BorderFactory.createTitledBorder(" "));        
        txTennv.setBorder(BorderFactory.createTitledBorder("Tên nhân viên"));
        txNgaysinh.setBorder(BorderFactory.createTitledBorder("Ngày sinh"));
        txDiachi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));        
        txSDT.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));
        
        JPanel plChonChucVu = new JPanel();
        plChonChucVu.setBorder(BorderFactory.createTitledBorder("Mã chức vụ"));
        plChonChucVu.add(txMacv);
        plChonChucVu.add(btnChonChucVu);

        JPanel plInput = new JPanel();
        plInput.add(txManv);
        plInput.add(plChonChucVu);
        plInput.add(txTennv);
        plInput.add(txNgaysinh);
        plInput.add(txDiachi);
        plInput.add(txSDT);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm nhân viên");
            txManv.setText("NV" + String.valueOf(qlnvBUS.getDsnv().size() + 1));

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa nhân viên");
            for (NhanVien nv : qlnvBUS.getDsnv()) {
                if (nv.getMaNV().equals(_manv)) {
                    this.nvSua = nv;
                }
            }
            if (this.nvSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy nhân viên");
                this.dispose();
            }

            txManv.setText(this.nvSua.getMaNV());
            txMacv.setText(this.nvSua.getMaCV());
            txTennv.setText(this.nvSua.getTenNV());
            txNgaysinh.setText(this.nvSua.getNgaySinh());
            txDiachi.setText(this.nvSua.getDiaChi());
            txSDT.setText(this.nvSua.getSDT());

            txManv.setEditable(false);

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
        btnChonChucVu.addActionListener((ae) -> {
            ChonChucVuForm clsp = new ChonChucVuForm(txMacv); // truyền vào textfield
        });

        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String manv = txManv.getText();
            String macv = txMacv.getText();
            String tennv = txTennv.getText();
            String ngaysinh = txNgaysinh.getText();
            String diachi = txDiachi.getText();
            String sdt = txSDT.getText();

            if (qlnvBUS.add(manv, macv, tennv, ngaysinh, diachi, sdt)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tennv + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String manv = txManv.getText();
            String macv = txMacv.getText();
            String tennv = txTennv.getText();
            String ngaysinh = txNgaysinh.getText();
            String diachi = txDiachi.getText();
            String sdt = txSDT.getText();

            if (qlnvBUS.update(manv, macv, tennv, ngaysinh, diachi, sdt)) {
                JOptionPane.showMessageDialog(this, "Sửa " + manv + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String manv = txManv.getText();
        String macv = txMacv.getText();
        String tennv = txTennv.getText();
        String ngaysinh = txNgaysinh.getText();
        String diachi = txDiachi.getText();
        String sdt = txSDT.getText();

        if (manv.trim().equals("")) {
            return showErrorTx(txManv, "Mã nhân viên không được để trống");
        } else if (macv.trim().equals("")) {
            return showErrorTx(txTennv, "Mã chức vụ không được để trống");
            
        } else if (tennv.trim().equals("")) {
            return showErrorTx(txTennv, "Tên nhân viên không được để trống");
        
        } else if (ngaysinh.trim().equals("")) {
            return showErrorTx(txTennv, "Ngày sinh không được để trống");
            
        } else if (diachi.trim().equals("")) {
            return showErrorTx(txTennv, "Địa chỉ không được để trống");

        } else if (sdt.trim().equals("")) {
            return showErrorTx(txTennv, "Số điện thoại không được để trống");
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
