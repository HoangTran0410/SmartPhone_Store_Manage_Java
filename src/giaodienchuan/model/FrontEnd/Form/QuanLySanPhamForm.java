package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaSanPhamForm;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QuanLySanPhamForm extends JPanel {

    QuanLySanPhamBUS qlsp = new QuanLySanPhamBUS();
    MyTable mtb;

    JTextField txTim = new JTextField(15);

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    JComboBox<String> cbTypeSearch;

    // index
    final int MASP_I = 1, MALSP_I = 2, TEN_I = 3, GIA_I = 4, SOLUONG_I = 5;

    public QuanLySanPhamForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá (triệu)", "Số lượng"});
        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2, 1});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        mtb.setAlignment(5, JLabel.CENTER);
        setDataToTable(qlsp.getDssp(), mtb);

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
        btnReadDB.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_database_restore_30px.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnReadDB);

        // ======== search panel ===========
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng"});

        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);

        //=========== add all to this jpanel ===========
        this.add(plTim);
        this.add(plBtn);
        this.add(mtb);

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
        btnReadDB.addActionListener((ActionEvent ae) -> {
            btnReadDBMouseClicked();
        });

        cbTypeSearch.addActionListener((ActionEvent e) -> {
            txTim.requestFocus();
            if (!txTim.getText().equals("")) {
                txSearchOnChange();
            }
        });

        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        txTim.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txSearchOnChange();
            }
        });

        mtb.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                int i = mtb.getTable().getSelectedRow();
                if (i >= 0) {
                    // show hình
                }
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qlsp.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnSuaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String masp = mtb.getModel().getValueAt(i, MASP_I).toString();
            ThemSuaSanPhamForm suasp = new ThemSuaSanPhamForm("Sửa", masp);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String masp = mtb.getModel().getValueAt(i, MASP_I).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm " + masp) == JOptionPane.OK_OPTION) {
                qlsp.delete(masp);
                setDataToTable(qlsp.getDssp(), mtb);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        qlsp.readDB();
        setDataToTable(qlsp.getDssp(), mtb);
    }

    private void btnThemMouseClicked() {
        ThemSuaSanPhamForm themsp = new ThemSuaSanPhamForm("Thêm", "");
    }

    private void setDataToTable(ArrayList<SanPham> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (SanPham sp : data) {
            table.addRow(new String[]{String.valueOf(stt), sp.getMaSP(), sp.getMaLSP(), sp.getTenSP(),
                String.valueOf(sp.getDonGia()), String.valueOf(sp.getSoLuong())});
            stt++;
        }
    }
}
