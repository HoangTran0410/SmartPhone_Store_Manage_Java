package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
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

public class QuanLyKhachHangForm extends JPanel {

    QuanLyKhachHangBUS qlkh = new QuanLyKhachHangBUS();
    MyTable mtb;

    JTextField txMakh = new JTextField(15);
    JTextField txTen = new JTextField(15);
    JTextField txDiachi = new JTextField(15);
    JTextField txSDT = new JTextField(15);
    JTextField txTim = new JTextField(15);

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnCapnhat = new JButton("Cập nhật");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    JComboBox<String> cbTypeSearch;

    // index
    final int MAKH_I = 1, TEN_I = 2, DIACHI_I = 3, SDT_I = 4;

    public QuanLyKhachHangForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại"});
        mtb.setColumnsWidth(new double[]{.5, 1.5, 2.5, 3, 2});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(2, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.CENTER);
        setDataToTable(qlkh.getDskh(), mtb);

        // inputs
        txMakh.setBorder(BorderFactory.createTitledBorder("Mã khách hàng"));
        txTen.setBorder(BorderFactory.createTitledBorder("Tên"));
        txDiachi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));
        txSDT.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));

        JPanel plInput = new JPanel();
        plInput.add(txMakh);
        plInput.add(txTen);
        plInput.add(txDiachi);
        plInput.add(txSDT);

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
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã khách hàng", "Tên", "Địa chỉ", "Số điện thoại"});

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
                    txMakh.setText(mtb.getModel().getValueAt(i, MAKH_I).toString());
                    txTen.setText(mtb.getModel().getValueAt(i, TEN_I).toString());
                    txDiachi.setText(mtb.getModel().getValueAt(i, DIACHI_I).toString());
                    txSDT.setText(mtb.getModel().getValueAt(i, SDT_I).toString());
                }
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qlkh.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnNhapLaiMouseClicked() {
        txMakh.setText("");
        txTen.setText("");
        txMakh.requestFocus();
    }

    private void btnCapnhatMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String makh = mtb.getModel().getValueAt(i, MAKH_I).toString();
            String ten = txTen.getText();
            String diachi = txDiachi.getText();
            String sdt = txSDT.getText();

            if (!txMakh.getText().equals(makh)) {
                JOptionPane.showMessageDialog(null, "Mã khách hàng là Khóa Chính nên không thể thay đổi, chỉ cập nhật các trường còn lại!");
                txMakh.setText(makh);
            }

            mtb.getModel().setValueAt(makh, i, MAKH_I);
            mtb.getModel().setValueAt(ten, i, TEN_I);
            mtb.getModel().setValueAt(diachi, i, DIACHI_I);
            mtb.getModel().setValueAt(sdt, i, SDT_I);

            qlkh.update(makh, ten, diachi, sdt);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn khách hàng nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String mskh = mtb.getModel().getValueAt(i, MAKH_I).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa khách hàng " + mskh) == JOptionPane.OK_OPTION) {
                qlkh.delete(mskh);
//                mtb.getModel().removeRow(i);
                setDataToTable(qlkh.getDskh(), mtb);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn khách hàng nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        qlkh.readDB();
        setDataToTable(qlkh.getDskh(), mtb);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String makh = txMakh.getText();
            String ten = txTen.getText();
            String diachi = txDiachi.getText();
            String sdt = txSDT.getText();

            KhachHang kh = new KhachHang(makh, ten, diachi, sdt);
            qlkh.add(kh);
            setDataToTable(qlkh.getDskh(), mtb);
        }
    }

    private void setDataToTable(ArrayList<KhachHang> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (KhachHang kh : data) {
            table.addRow(new String[]{String.valueOf(stt), kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(),
                 kh.getSDT()});
            stt++;
        }
    }

    private Boolean checkEmpty() {
        String makh = txMakh.getText();
        String ten = txTen.getText();
        String diachi = txDiachi.getText();
        String sdt = txSDT.getText();

        if (makh.trim().equals("")) {
            return showErrorTx(txMakh, "Mã khách hàng không được để trống");

        } else if (ten.trim().equals("")) {
            return showErrorTx(txTen, "Tên khách hàng không được để trống");

        } else if (diachi.trim().equals("")) {
            return showErrorTx(txDiachi, "Địa chỉ không được để trống");

        } else if (sdt.trim().equals("")) {
            return showErrorTx(txSDT, "Số điện thoại không được để trống");

        } else {
           
            try {
                int sl = Integer.parseInt(sdt);
                if (sl < 0) {
                    return showErrorTx(txSDT, "Số điện thoại không hợp lệ (phải là số duơng)");
                }
            } catch (NumberFormatException e) {
                return showErrorTx(txSDT, "Số điện thoại không hợp lệ (phải là số nguyên)");
            }
        }
        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}
