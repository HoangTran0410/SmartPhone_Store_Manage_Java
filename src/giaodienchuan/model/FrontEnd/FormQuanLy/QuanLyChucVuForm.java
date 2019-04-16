package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.BackEnd.ChucVu.ChucVu;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.BackEnd.ChucVu.ChucVuBUS;
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

public class QuanLyChucVuForm extends JPanel {

    ChucVuBUS qlcv = new ChucVuBUS();
    MyTable mtb;

    JTextField txMacv = new JTextField(15);
    JTextField txTencv = new JTextField(15);
    JTextField txTim = new JTextField(15);
    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnCapnhat = new JButton("Cập nhật");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    JComboBox<String> cbTypeSearch;

    // index
    final int MACV_I = 1, TENCV_I = 2, DIACHI_I = 3, SDT_I = 4;

    public QuanLyChucVuForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã chức vụ", "Tên chức vụ"});
        mtb.setColumnsWidth(new double[]{1, 2, 3 });
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(2, JLabel.CENTER);

        // read data from database
        btnReadDBMouseClicked();

        // inputs
        txMacv.setBorder(BorderFactory.createTitledBorder("Mã chức vụ"));
        txTencv.setBorder(BorderFactory.createTitledBorder("Tên"));

        JPanel plInput = new JPanel();
        plInput.add(txMacv);
        plInput.add(txTencv);  
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
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã chức vụ", "Tên chức vụ"});

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
                    txMacv.setText(mtb.getModel().getValueAt(i, MACV_I).toString());
                    txTencv.setText(mtb.getModel().getValueAt(i, TENCV_I).toString());                  
                }
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qlcv.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnNhapLaiMouseClicked() {
        txMacv.setText("");
        txTencv.setText("");
        txMacv.requestFocus();
    }

    private void btnCapnhatMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String makh = mtb.getModel().getValueAt(i, MACV_I).toString();
            String ten = txTencv.getText();

            if (!txMacv.getText().equals(makh)) {
                JOptionPane.showMessageDialog(null, "Mã chức vụ là Khóa Chính nên không thể thay đổi, chỉ cập nhật các trường còn lại!");
                txMacv.setText(makh);
            }

            mtb.getModel().setValueAt(makh, i, MACV_I);
            mtb.getModel().setValueAt(ten, i, TENCV_I);

            qlcv.update(makh, ten);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn chức vụ nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String macv = mtb.getModel().getValueAt(i, MACV_I).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa chức vụ " + macv) == JOptionPane.OK_OPTION) {
                qlcv.delete(macv);
//                mtb.getModel().removeRow(i);
                setDataToTable(qlcv.getDscv(), mtb);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn chức vụ nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        qlcv.readDB();
        setDataToTable(qlcv.getDscv(), mtb);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String macv = txMacv.getText();
            String tencv = txTencv.getText();
            ChucVu cv =new ChucVu(macv,tencv);
            qlcv.add(cv);
            setDataToTable(qlcv.getDscv(), mtb);
        }
    }

    private void setDataToTable(ArrayList<ChucVu> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (ChucVu cv : data) {
            table.addRow(new String[]{String.valueOf(stt), cv.getMaCV(), cv.getTenCV() });
            stt++;
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
}
