package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.LoaiSanPham;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
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

public class QuanLyLoaiSanPhamForm extends JPanel {

    QuanLyLoaiSanPhamBUS qllsp = new QuanLyLoaiSanPhamBUS();
    MyTable mtb;

    JTextField txMalsp = new JTextField(15);
    JTextField txTenlsp = new JTextField(15);
    JTextField txMota = new JTextField(15);
    JTextField txTim = new JTextField(15);

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnCapnhat = new JButton("Cập nhật");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    JComboBox<String> cbTypeSearch;

    // index
    final int MALSP_I = 1, TENLSP_I = 2, MOTA_I = 3;

    public QuanLyLoaiSanPhamForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã loại", "Tên loại", "Mô tả"});
        mtb.setColumnsWidth(new double[]{.5, 3, 4, 5});
        mtb.setAlignment(0, JLabel.CENTER);

        // read data from database
        btnReadDBMouseClicked();

        // inputs
        txMalsp.setBorder(BorderFactory.createTitledBorder("Mã loại"));
        txTenlsp.setBorder(BorderFactory.createTitledBorder("Tên loại"));
        txMota.setBorder(BorderFactory.createTitledBorder("Mô tả"));

        JPanel plInput = new JPanel();
        plInput.add(txMalsp);
        plInput.add(txTenlsp);
        plInput.add(txMota);

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnCapnhat.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
        btnNhaplai.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_replay_30px.png")));
        btnReadDB.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_database_restore_30px.png")));

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnCapnhat);
        plBtn.add(btnNhaplai);
        plBtn.add(btnReadDB);

        // ======== search panel ===========
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã loại", "Tên loại", "Mô tả"});

        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);

        //=========== add all to this jpanel ===========
        this.add(plInput);
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
        btnCapnhat.addActionListener((ActionEvent ae) -> {
            btnCapnhatMouseClicked();
        });
        btnNhaplai.addActionListener((ActionEvent ae) -> {
            btnNhapLaiMouseClicked();
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
                    txMalsp.setText(mtb.getModel().getValueAt(i, MALSP_I).toString());
                    txTenlsp.setText(mtb.getModel().getValueAt(i, TENLSP_I).toString());
                    txMota.setText(mtb.getModel().getValueAt(i, MOTA_I).toString());
                }
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qllsp.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnNhapLaiMouseClicked() {
        txMalsp.setText("");
        txTenlsp.setText("");
        txMota.setText("");
    }

    private void btnCapnhatMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String malsp = mtb.getModel().getValueAt(i, MALSP_I).toString();;
            String tenlsp = txTenlsp.getText();
            String mota = txMota.getText();

            if (!txMalsp.getText().equals(malsp)) {
                JOptionPane.showMessageDialog(null, "Mã loại sản phẩm là Khóa Chính nên không thể thay đổi, chỉ cập nhật các trường còn lại!");
                txMalsp.setText(malsp);
            }

            mtb.getModel().setValueAt(malsp, i, MALSP_I);
            mtb.getModel().setValueAt(tenlsp, i, TENLSP_I);
            mtb.getModel().setValueAt(mota, i, MOTA_I);

            qllsp.update(malsp, tenlsp, mota);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn loại sản phẩm nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String malsp = mtb.getModel().getValueAt(i, MALSP_I).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa loại sản phẩm " + malsp) == JOptionPane.OK_OPTION) {
                qllsp.delete(malsp);
//                mtb.getModel().removeRow(i);
                setDataToTable(qllsp.getDssp(), mtb);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn loại sản phẩm nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        qllsp.readDB();
        setDataToTable(qllsp.getDssp(), mtb);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String malsp = txMalsp.getText();
            String tenlsp = txTenlsp.getText();
            String mota = txMota.getText();

            LoaiSanPham lsp = new LoaiSanPham(malsp, tenlsp, mota);
            qllsp.add(lsp);
            setDataToTable(qllsp.getDssp(), mtb);
        }
    }

    private void setDataToTable(ArrayList<LoaiSanPham> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (LoaiSanPham lsp : data) {
            table.addRow(new String[]{String.valueOf(stt), lsp.getMaLSP(), lsp.getTenLSP(), lsp.getMoTa()});
            stt++;
        }
    }

    private Boolean checkEmpty() {
        String malsp = txMalsp.getText();
        String tenlsp = txTenlsp.getText();

        if (malsp.trim().equals("")) {
            return showErrorTx(txMalsp, "Mã loại sp không được để trống");

        } else if (tenlsp.trim().equals("")) {
            return showErrorTx(txTenlsp, "Tên loại không được để trống");
        }

        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}
