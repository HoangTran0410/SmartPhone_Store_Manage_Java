package giaodienchuan.model.FrontEnd.Form;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
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

    JTextField txMasp = new JTextField(15);
    JTextField txMalsp = new JTextField(15);
    JTextField txTen = new JTextField(15);
    JTextField txGia = new JTextField(15);
    JTextField txSoluong = new JTextField(15);
    JTextField txTim = new JTextField(15);

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnCapnhat = new JButton("Cập nhật");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    JComboBox<String> cbTypeSearch;

    // vị trí cột trong bảng mtb, vị trí bắt đầu từ 0
    final int MASP_I = 1, MALSP_I = 2, TEN_I = 3, GIA_I = 4, SOLUONG_I = 5;

    public QuanLySanPhamForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá (triệu)", "Số lượng"});
        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2, 1});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        mtb.setAlignment(5, JLabel.CENTER);

        // read data from database
        btnReadDBMouseClicked();

        // inputs
        txMasp.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        txMalsp.setBorder(BorderFactory.createTitledBorder("Mã loại"));
        txTen.setBorder(BorderFactory.createTitledBorder("Tên"));
        txGia.setBorder(BorderFactory.createTitledBorder("Đơn Giá"));
        txSoluong.setBorder(BorderFactory.createTitledBorder("Số lượng"));

        JPanel plInput = new JPanel();
        plInput.add(txMasp);
        plInput.add(txMalsp);
        plInput.add(txTen);
        plInput.add(txGia);
        plInput.add(txSoluong);

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
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng"});

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
                    txMasp.setText(mtb.getModel().getValueAt(i, MASP_I).toString());
                    txMalsp.setText(mtb.getModel().getValueAt(i, MALSP_I).toString());
                    txTen.setText(mtb.getModel().getValueAt(i, TEN_I).toString());
                    txGia.setText(mtb.getModel().getValueAt(i, GIA_I).toString());
                    txSoluong.setText(mtb.getModel().getValueAt(i, SOLUONG_I).toString());
                }
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qlsp.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnNhapLaiMouseClicked() {
        txMasp.setText("");
        txMalsp.setText("");
        txTen.setText("");
        txMasp.requestFocus();
    }

    private void btnCapnhatMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String masp = mtb.getModel().getValueAt(i, MASP_I).toString();
            String maloai = txMalsp.getText();
            String ten = txTen.getText();
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoluong.getText());

            if (!txMasp.getText().equals(masp)) {
                JOptionPane.showMessageDialog(null, "Mã sản phẩm là Khóa Chính nên không thể thay đổi, chỉ cập nhật các trường còn lại!");
                txMasp.setText(masp);
            }

            mtb.getModel().setValueAt(masp, i, MASP_I);
            mtb.getModel().setValueAt(maloai, i, MALSP_I);
            mtb.getModel().setValueAt(ten, i, TEN_I);
            mtb.getModel().setValueAt(dongia, i, GIA_I);
            mtb.getModel().setValueAt(soluong, i, SOLUONG_I);

            qlsp.update(masp, maloai, ten, dongia, soluong);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String mssp = mtb.getModel().getValueAt(i, MASP_I).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm " + mssp) == JOptionPane.OK_OPTION) {
                qlsp.delete(mssp);
//                mtb.getModel().removeRow(i);
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
        if (checkEmpty()) {
            String masp = txMasp.getText();
            String maloai = txMalsp.getText();
            String ten = txTen.getText();
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoluong.getText());

            SanPham sp = new SanPham(masp, maloai, ten, dongia, soluong);
            qlsp.add(sp);
            setDataToTable(qlsp.getDssp(), mtb);
        }
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
    // giờ m bê hết code này qua cũng được, xong sửa những chỗ cần thiết là xong ok
    // ví dụ tên biến các tên sp -> ncc
    // ncc có mấy textField bla bla
    // nói chung chỉ sauwr những cái cầnthiết
    // đó. chỉ sửa nhiêu đó, là thành nhà cung cấp
    // sắp tới t sẽ đổi toàn bộ giao diện, nhưng nói chung hiện giờ m cứ bê code qua rồi sửa cho đúgn để hiểu cái đi, tính sau ok thank

    private Boolean checkEmpty() {
        String mssp = txMasp.getText();
        String maloai = txMalsp.getText();
        String ten = txTen.getText();
        String dongia = txGia.getText();
        String soluong = txSoluong.getText();

        if (mssp.trim().equals("")) {
            return showErrorTx(txMasp, "Mã sp không được để trống");

        } else if (maloai.trim().equals("")) {
            return showErrorTx(txMalsp, "Mã loại không được để trống");

        } else if (ten.trim().equals("")) {
            return showErrorTx(txTen, "Tên không được để trống");

        } else if (dongia.trim().equals("")) {
            return showErrorTx(txGia, "Đơn giá không được để trống");

        } else if (soluong.trim().equals("")) {
            return showErrorTx(txSoluong, "Số lượng không được để trống");

        } else {
            try {
                float dg = Float.parseFloat(dongia);
            } catch (NumberFormatException e) {
                return showErrorTx(txGia, "Đơn giá không hợp lệ (phải là số thực)");
            }

            try {
                int sl = Integer.parseInt(soluong);
                if (sl < 0) {
                    return showErrorTx(txSoluong, "Số lượng không hợp lệ (phải là số duơng)");
                }
            } catch (NumberFormatException e) {
                return showErrorTx(txSoluong, "Số lượng không hợp lệ (phải là số nguyên)");
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
