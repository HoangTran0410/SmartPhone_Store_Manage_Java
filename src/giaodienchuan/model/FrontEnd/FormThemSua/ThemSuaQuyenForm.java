package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyQuyen.QuanLyQuyenBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.Quyen;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ThemSuaQuyenForm extends JFrame {

    String type;
    QuanLyQuyenBUS qlqBUS = new QuanLyQuyenBUS();
    Quyen qSua;

    JTextField txMaQuyen = new JTextField(15);
    JTextField txTenQuyen = new JTextField(15);
    ChiTietQuyenForm chitietForm = new ChiTietQuyenForm();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaQuyenForm(String _type, String _maq) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMaQuyen.setBorder(BorderFactory.createTitledBorder("Mã quyền"));
        txTenQuyen.setBorder(BorderFactory.createTitledBorder("Tên quyền"));

        JPanel plInput = new JPanel();
        plInput.add(txMaQuyen);
        plInput.add(txTenQuyen);
        plInput.add(chitietForm);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm quyền");
            txMaQuyen.setText(qlqBUS.getNextID());

            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

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
            chitietForm.setQuyen(this.qSua.getChiTietQuyen());

            txMaQuyen.setEditable(false);

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
            String maquyen = txMaQuyen.getText();
            String tenquyen = txTenQuyen.getText();
            String chitietquyen = chitietForm.getQuyen();

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
            String chitietquyen = chitietForm.getQuyen();
            System.out.println(chitietquyen);

            if (qlqBUS.update(maquyen, tenquyen, chitietquyen)) {
                JOptionPane.showMessageDialog(this, "Sửa " + maquyen + " thành công!");
                this.dispose();
            }
        }
    }

    private Boolean checkEmpty() {
        String maquyen = txMaQuyen.getText();
        String tenquyen = txTenQuyen.getText();
        String chitietquyen = chitietForm.getQuyen();

        if (maquyen.trim().equals("")) {
            return showErrorTx(txMaQuyen, "Mã quyền không được để trống");

        } else if (tenquyen.trim().equals("")) {
            return showErrorTx(txMaQuyen, "Tên quyền không được để trống");

        } else if (chitietquyen.trim().equals("")) {
            return showErrorTx(txMaQuyen, "Bạn chưa chọn quyền nào cả !!");
        }

        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}

class ChiTietQuyenForm extends JPanel {

    final String[] type = {"Chỉ xem", "Xem và Quản lý"};
    ArrayList<PanelChooseQuyen> dsPanel = new ArrayList<>();

    public ChiTietQuyenForm() {
        setPreferredSize(new Dimension(300, 600));
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Chi tiết quyền: "));

        dsPanel.add(new PanelChooseQuyen("Bán Hàng", new String[]{"Bán hàng"}, new String[]{"qlBanHang"}));
        dsPanel.add(new PanelChooseQuyen("Nhập Hàng", new String[]{"Nhập hàng"}, new String[]{"qlNhapHang"}));
        dsPanel.add(new PanelChooseQuyen("Sản Phẩm", type, new String[]{"xemSanPham", "qlSanPham"}));
        dsPanel.add(new PanelChooseQuyen("Loại Sản Phẩm", type, new String[]{"xemLoaiSanPham", "qlLoaiSanPham"}));
        dsPanel.add(new PanelChooseQuyen("Hóa Đơn", type, new String[]{"xemHoaDon", "qlHoaDon"}));
        dsPanel.add(new PanelChooseQuyen("Khuyến Mãi", type, new String[]{"xemKhuyenMai", "qlKhuyenMai"}));
        dsPanel.add(new PanelChooseQuyen("Nhân Viên", type, new String[]{"xemNhanVien", "qlNhanVien"}));
        dsPanel.add(new PanelChooseQuyen("Khách Hàng", type, new String[]{"xemKhachHang", "qlKhachHang"}));
        dsPanel.add(new PanelChooseQuyen("Phiếu Nhập", type, new String[]{"xemPhieuNhap", "qlPhieuNhap"}));
        dsPanel.add(new PanelChooseQuyen("Nhà Cung Cấp", type, new String[]{"xemNCC", "qlNCC"}));
        dsPanel.add(new PanelChooseQuyen("Tài Khoản", type, new String[]{"xemTaiKhoan", "qlTaiKhoan"}));
        dsPanel.add(new PanelChooseQuyen("Quyền", type, new String[]{"xemQuyen", "qlQuyen"}));

        for (PanelChooseQuyen p : dsPanel) {
            this.add(p);
        }
    }

    public String getQuyen() {
        String result = "";
        for (PanelChooseQuyen p : dsPanel) {
            result += p.getValue();
        }
        return result.trim();
    }

    public void setQuyen(String quyen) {
        for (PanelChooseQuyen p : dsPanel) {
            p.setValue(quyen);
        }
    }
}

class PanelChooseQuyen extends JPanel {

    String name;
    String[] type, value;

    JCheckBox chb;
    JComboBox<String> cb;

    public PanelChooseQuyen(String name, String[] type, String[] value) {
        this.setPreferredSize(new Dimension(250, 50));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.name = name;
        this.type = type;
        this.value = value;

        this.chb = new JCheckBox(this.name);
        this.add(this.chb);

        this.cb = new JComboBox<>(this.type);
        this.cb.setEnabled(false);
        this.add(this.cb);

        chb.addActionListener((ae) -> {
            if (chb.isSelected()) {
                this.cb.setEnabled(true);
            } else {
                this.cb.setEnabled(false);
            }
        });
    }

    public String getValue() {
        String result = "";

        if (chb.isSelected()) {
            result += " " + value[cb.getSelectedIndex()];
        }

        return result;
    }

    public void setValue(String s) {
        if (s.equals("")) {
            chb.setSelected(false);
            return;
        }

        for (int i = 0; i < value.length; i++) {
            if (s.contains(value[i])) {
                cb.setSelectedIndex(i);
                cb.setEnabled(true);
                chb.setSelected(true);
            }
        }
    }
}
