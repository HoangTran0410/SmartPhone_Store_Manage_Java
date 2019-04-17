package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCapBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiNhaCungCap;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class NhaCungCapFORM extends JPanel {

//    NhaCungCapBUS BUS = new NhaCungCapBUS();
//    MyTable mtb;

//    JTextField txMaNCC = new JTextField(10);
//    JTextField txTenNCC = new JTextField(10);
//    JTextField txDiaChi = new JTextField(10);
//    JTextField txSDT = new JTextField(10);
//    JTextField txFax = new JTextField(10);
//    JTextField txTim = new JTextField(15);
    
    HienThiNhaCungCap formHienThi=new HienThiNhaCungCap();
    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");
    
//    JComboBox<String> cbTypeSearch;
    
//    final int MANCC_I = 1,  TENNCC_I = 2, DIACHI_I = 3, SDT_I = 4, FAX_I = 5;
    public NhaCungCapFORM() {
//        mtb = new MyTable();
//        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
//        mtb.setHeaders(new String[]{"STT","Mã NCC", "Tên NCC", "Địa chỉ", "SDT", "Fax"});// stt dau ara ???
//        
//        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2, 1});
//        mtb.setAlignment(0, JLabel.CENTER);
//        mtb.setAlignment(4, JLabel.RIGHT);
//        mtb.setAlignment(5, JLabel.CENTER);

        // read data from database
//        btnReadDBMouseClicked();

        // inputs
//        txMaNCC.setBorder(BorderFactory.createTitledBorder("Mã nhà cung cấp"));
//        txTenNCC.setBorder(BorderFactory.createTitledBorder("Tên nhà cung cấp"));
//        txDiaChi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));
//        txSDT.setBorder(BorderFactory.createTitledBorder("SDT"));
//        txFax.setBorder(BorderFactory.createTitledBorder("Fax"));
//
//        JPanel plInput = new JPanel();
//        plInput.add(txMaNCC);
//        plInput.add(txTenNCC);
//        plInput.add(txDiaChi);
//        plInput.add(txSDT);
//        plInput.add(txFax);

        // buttons
        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
        btnNhaplai.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_replay_30px.png")));
        btnReadDB.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_database_restore_30px.png")));
        
        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnNhaplai);
        plBtn.add(btnReadDB);
        
         this.add(formHienThi, BorderLayout.CENTER);
         this.add(plBtn, BorderLayout.SOUTH);
//         // ======== search panel ===========
//        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã NCC", "Tên NCC", "Địa chỉ", "Điện thoại", "Fax"});
//
//        JPanel plTim = new JPanel();
//        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
//        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
//        plTim.add(cbTypeSearch);
//        plTim.add(txTim);
//
//        // container
//        this.add(plInput);
//        this.add(plTim);
//        this.add(plBtn);
//        this.add(mtb);

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnThemMouseClicked();
            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnXoaMouseClicked();
            }
        });
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnSuaMouseClicked();
            }
        });
        btnNhaplai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnNhapLaiMouseClicked();
            }
        });
        btnReadDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnReadDBMouseClicked();
            }
        });
//        cbTypeSearch.addActionListener((ActionEvent e) -> {
//            txTim.requestFocus();
//            if (!txTim.getText().equals("")) {
//                txSearchOnChange();
//            }
//        });
//        txTim.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                txSearchOnChange();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                txSearchOnChange();
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                txSearchOnChange();
//            }
//        });
//
//
//        mtb.getTable().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent me) {
//                int i = mtb.getTable().getSelectedRow();
//                if (i >= 0) {
//                    txMaNCC.setText(mtb.getModel().getValueAt(i, MANCC_I).toString());
//                    txTenNCC.setText(mtb.getModel().getValueAt(i,TENNCC_I).toString());
//                    txDiaChi.setText(mtb.getModel().getValueAt(i,DIACHI_I).toString());
//                    txSDT.setText(mtb.getModel().getValueAt(i, SDT_I).toString());
//                    txFax.setText(mtb.getModel().getValueAt(i, FAX_I).toString());
//                }
//            }
//        });

    }
    private void txSearchOnChange() {
        setDataToTable(BUS.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void btnNhapLaiMouseClicked() {
        txMaNCC.setText("");
        txTenNCC.setText("");
        txDiaChi.setText("");
        txSDT.setText("");
        txFax.setText("");

        txMaNCC.requestFocus();
    }

    private void btnSuaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String maNCC = mtb.getModel().getValueAt(i, 0).toString();
            String tenNCC = txTenNCC.getText();
            String diaChi = txDiaChi.getText();
            String SDT = txSDT.getText();
            String Fax = txFax.getText();

            if (!txMaNCC.getText().equals(maNCC)) {
                JOptionPane.showMessageDialog(null, "Mã số sinh viên là Khóa Chính nên không thể thay đổi, chỉ cập nhật Họ và Tên!");
                txMaNCC.setText(maNCC);
            }

            mtb.getModel().setValueAt(maNCC, i, MANCC_I);
            mtb.getModel().setValueAt(tenNCC, i, TENNCC_I);
            mtb.getModel().setValueAt(diaChi, i, DIACHI_I);
           mtb.getModel().setValueAt(SDT, i, SDT_I);
            mtb.getModel().setValueAt(Fax, i, FAX_I);

            BUS.update(maNCC, tenNCC, diaChi, SDT, Fax);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String mancc = mtb.getModel().getValueAt(i, 0).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sinh viên " + mancc) == JOptionPane.OK_OPTION) {
                BUS.delete(mancc);
//                mtb.getModel().removeRow(i);
            setDataToTable(BUS.getDsncc(), mtb);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        BUS.readDB();
//        mtb.clear();
//        BUS.dsncc.forEach((ncc) -> {
//            mtb.addRow(new String[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSDT(), ncc.getFax()});
//        });
        setDataToTable(BUS.getDsncc(), mtb);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            NhaCungCap ncc = new NhaCungCap(txMaNCC.getText(), txTenNCC.getText(), txDiaChi.getText(), txSDT.getText(), txFax.getText());
            BUS.add(ncc);
//            mtb.clear();
//            BUS.dsncc.forEach((n) -> {
//                mtb.addRow(new String[]{n.getMaNCC(), n.getTenNCC(), n.getDiaChi(), n.getSDT(), n.getFax()});
//            });
        setDataToTable(BUS.getDsncc(), mtb);
        }
    }
    private void setDataToTable(ArrayList<NhaCungCap> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (NhaCungCap ncc : data) {
            table.addRow(new String[]{String.valueOf(stt), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(),
                String.valueOf(ncc.getSDT()), String.valueOf(ncc.getFax())});
            stt++;
        }
    }

    private Boolean checkEmpty() {
        String ma = txMaNCC.getText();
        String ten = txTenNCC.getText();
        String diachi = txDiaChi.getText();
        String sdt = txSDT.getText();
        String fax = txFax.getText();
        if (ma.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mã sinh viên không được để trống");
            txMaNCC.requestFocus();
            return false;
        } else if (ten.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Họ sinh viên không được để trống");
            txTenNCC.requestFocus();
            return false;
        } else if (diachi.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
            txDiaChi.requestFocus();
            return false;
        } else if (sdt.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
            txSDT.requestFocus();
            return false;
        } else if (fax.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
            txFax.requestFocus();
            return false;
        }
        return true;
    }

   
}
