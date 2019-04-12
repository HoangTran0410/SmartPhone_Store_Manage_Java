package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.BackEnd.NhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.NhanVien.NhanVien;
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

public class QuanLyNhanVienForm extends JPanel {

    QuanLyNhanVienBUS qlnv = new QuanLyNhanVienBUS();
    MyTable mtb;

    JTextField txManv = new JTextField(12);
    JTextField txMacv = new JTextField(12);
    JTextField txTennv = new JTextField(15);
    JTextField txNgaysinh = new JTextField(12);
    JTextField txDiachi = new JTextField(15);
    JTextField txSDT= new JTextField(12);
    JTextField txTim = new JTextField(15);


    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnCapnhat = new JButton("Cập nhật");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    JComboBox<String> cbTypeSearch;

    // index
    final int MANV_I = 1, MACV_I = 2, TENNV_I = 3, NGAYSINH_I = 4, DIACHI_I = 5,  SDT_I = 6;

    public QuanLyNhanVienForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã nhân viên", "Mã chức vụ", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Số điện thoại"});
        mtb.setColumnsWidth(new double[]{.5, 1.5, 1.5, 2.5, 1.3,3, 1.5});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        mtb.setAlignment(5, JLabel.CENTER);

        // read data from database
        btnReadDBMouseClicked();

        // inputs
        txManv.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        txMacv.setBorder(BorderFactory.createTitledBorder("Mã chức vụ"));
        txTennv.setBorder(BorderFactory.createTitledBorder("Tên nhân viên"));
        txNgaysinh.setBorder(BorderFactory.createTitledBorder("Ngày sinh"));
        txDiachi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));        
        txSDT.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));

        

        JPanel plInput = new JPanel();
        plInput.add(txManv);
        plInput.add(txMacv);
        plInput.add(txTennv);
        plInput.add(txNgaysinh);
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
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã nhân viên", "Mã chức vụ", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Số điện thoại"});

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
                    txManv.setText(mtb.getModel().getValueAt(i, MANV_I).toString());
                    txMacv.setText(mtb.getModel().getValueAt(i, MACV_I).toString());
                    txTennv.setText(mtb.getModel().getValueAt(i, TENNV_I).toString());
                    txNgaysinh.setText(mtb.getModel().getValueAt(i, NGAYSINH_I).toString());
                    txDiachi.setText(mtb.getModel().getValueAt(i, DIACHI_I).toString());
                    txSDT.setText(mtb.getModel().getValueAt(i, SDT_I).toString());
                }
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qlnv.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnNhapLaiMouseClicked() {
        txManv.setText("");
        txMacv.setText("");
        txTennv.setText("");
        txManv.requestFocus();
    }

    private void btnCapnhatMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String manv = mtb.getModel().getValueAt(i, MANV_I).toString();
            String macv = txMacv.getText();
            String ten = txTennv.getText();
            String ngaysinh = txNgaysinh.getText();
            String diachi = txDiachi.getText();
            int sdt = Integer.parseInt(txSDT.getText());

            if (!txManv.getText().equals(manv)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên là Khóa Chính nên không thể thay đổi, chỉ cập nhật các trường còn lại!");
                txManv.setText(manv);
            }

            mtb.getModel().setValueAt(manv, i, MANV_I);
            mtb.getModel().setValueAt(macv, i, MACV_I);
            mtb.getModel().setValueAt(ten, i, TENNV_I);
            mtb.getModel().setValueAt(ngaysinh, i, NGAYSINH_I);
            mtb.getModel().setValueAt(diachi, i, DIACHI_I);
            mtb.getModel().setValueAt(sdt, i, SDT_I);

            qlnv.update(manv, macv, ten, ngaysinh, diachi, sdt);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String manv = mtb.getModel().getValueAt(i, MANV_I).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên " + manv) == JOptionPane.OK_OPTION) {
                qlnv.delete(manv);
//                mtb.getModel().removeRow(i);
                setDataToTable(qlnv.getDsnv(), mtb);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        qlnv.readDB();
        setDataToTable(qlnv.getDsnv(), mtb);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String manv = txManv.getText();
            String macv = txMacv.getText();
            String ten = txTennv.getText();
            String ngaysinh = txNgaysinh.getText();
            String diachi = txDiachi.getText();
            int sdt = Integer.parseInt(txSDT.getText());

            NhanVien nv = new NhanVien(manv, macv, ten, ngaysinh, diachi, sdt);
            qlnv.add(nv);
            setDataToTable(qlnv.getDsnv(), mtb);
        }
    }

    private void setDataToTable(ArrayList<NhanVien> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (NhanVien nv : data) {
            table.addRow(new String[]{String.valueOf(stt), nv.getMaNV(), nv.getMaCV(), nv.getTenNV(),
                nv.getNgaySinh(),nv.getDiaChi(), String.valueOf(nv.getSDT())});
            stt++;
        }
    }

    private Boolean checkEmpty() {
        String manv = txManv.getText();
        String macv = txMacv.getText();
        String ten = txTennv.getText();
        String ngaysinh = txNgaysinh.getText();
        String diachi = txDiachi.getText();
        String sdt = txSDT.getText();

        if (manv.trim().equals("")) {
            return showErrorTx(txManv, "Mã nhân viên không được để trống");

        } else if (macv.trim().equals("")) {
            return showErrorTx(txMacv, "Mã chức vụ không được để trống");

        } else if (ten.trim().equals("")) {
            return showErrorTx(txTennv, "Tên nhân viên không được để trống");

        } else if (ngaysinh.trim().equals("")) {
            return showErrorTx(txNgaysinh, "Ngày sinh không được để trống");
        
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
