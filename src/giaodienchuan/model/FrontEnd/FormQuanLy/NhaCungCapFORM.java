package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCapBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiNhaCungCap;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaNhaCungCapForm;
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
    
    HienThiNhaCungCap formHienThi=new HienThiNhaCungCap();
    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
//    JButton btnReadDB = new JButton("Đọc DB");
//    JButton btnNhaplai = new JButton("Nhập lại");

    

    public NhaCungCapFORM() {
        setLayout(new BorderLayout());

        // read data from database
//        btnReadDBMouseClicked();

        
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
//        btnNhaplai.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_replay_30px.png")));
//        btnReadDB.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_database_restore_30px.png")));
        
        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);

         this.add(formHienThi, BorderLayout.CENTER);
         this.add(plBtn, BorderLayout.NORTH);

         btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
            formHienThi.refresh();
        });

    }
     private void btnSuaMouseClicked() {
        String mancc = formHienThi.getSelectedNhaCungCap(1);
        if (mancc != null) {
            ThemSuaNhaCungCapForm suancc = new ThemSuaNhaCungCapForm("Sửa", mancc);

            suancc.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhà cung cấp nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String mancc = formHienThi.getSelectedNhaCungCap(1);
        if (mancc != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà cung cấp " + mancc + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new NhaCungCapBUS().delete(mancc);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhà cung cấp nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaNhaCungCapForm themncc = new ThemSuaNhaCungCapForm("Thêm", "");
        themncc.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
//    private void txSearchOnChange() {
//        setDataToTable(BUS.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
//    }

    
//    private void btnNhapLaiMouseClicked() {
//        txMaNCC.setText("");
//        txTenNCC.setText("");
//        txDiaChi.setText("");
//        txSDT.setText("");
//        txFax.setText("");
//
//        txMaNCC.requestFocus();
//    }

//    private void btnSuaMouseClicked() {
//        int i = mtb.getTable().getSelectedRow();
//        if (i >= 0 && checkEmpty()) {
//            String maNCC = mtb.getModel().getValueAt(i, 0).toString();
//            String tenNCC = txTenNCC.getText();
//            String diaChi = txDiaChi.getText();
//            String SDT = txSDT.getText();
//            String Fax = txFax.getText();
//
//            if (!txMaNCC.getText().equals(maNCC)) {
//                JOptionPane.showMessageDialog(null, "Mã số sinh viên là Khóa Chính nên không thể thay đổi, chỉ cập nhật Họ và Tên!");
//                txMaNCC.setText(maNCC);
//            }
//
//            mtb.getModel().setValueAt(maNCC, i, MANCC_I);
//            mtb.getModel().setValueAt(tenNCC, i, TENNCC_I);
//            mtb.getModel().setValueAt(diaChi, i, DIACHI_I);
//           mtb.getModel().setValueAt(SDT, i, SDT_I);
//            mtb.getModel().setValueAt(Fax, i, FAX_I);
//
//            BUS.update(maNCC, tenNCC, diaChi, SDT, Fax);
//
//        } else {
//            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để sửa");
//        }
//    }
//
//    private void btnXoaMouseClicked() {
//        int i = mtb.getTable().getSelectedRow();
//        if (i >= 0) {
//            String mancc = mtb.getModel().getValueAt(i, 0).toString();
//            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sinh viên " + mancc) == JOptionPane.OK_OPTION) {
//                BUS.delete(mancc);
////                mtb.getModel().removeRow(i);
//            setDataToTable(BUS.getDsncc(), mtb);
//            }
//
//        } else {
//            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để xóa");
//        }
//    }
//
//    private void btnReadDBMouseClicked() {
//        BUS.readDB();
////        mtb.clear();
////        BUS.dsncc.forEach((ncc) -> {
////            mtb.addRow(new String[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSDT(), ncc.getFax()});
////        });
//        setDataToTable(BUS.getDsncc(), mtb);
//    }
//
//    private void btnThemMouseClicked() {
//        if (checkEmpty()) {
//            NhaCungCap ncc = new NhaCungCap(txMaNCC.getText(), txTenNCC.getText(), txDiaChi.getText(), txSDT.getText(), txFax.getText());
//            BUS.add(ncc);
////            mtb.clear();
////            BUS.dsncc.forEach((n) -> {
////                mtb.addRow(new String[]{n.getMaNCC(), n.getTenNCC(), n.getDiaChi(), n.getSDT(), n.getFax()});
////            });
//        setDataToTable(BUS.getDsncc(), mtb);
//        }
//    }
//    private void setDataToTable(ArrayList<NhaCungCap> data, MyTable table) {
//        table.clear();
//        int stt = 1; // lưu số thứ tự dòng hiện tại
//        for (NhaCungCap ncc : data) {
//            table.addRow(new String[]{String.valueOf(stt), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(),
//                String.valueOf(ncc.getSDT()), String.valueOf(ncc.getFax())});
//            stt++;
//        }
//    }
//
//    private Boolean checkEmpty() {
//        String ma = txMaNCC.getText();
//        String ten = txTenNCC.getText();
//        String diachi = txDiaChi.getText();
//        String sdt = txSDT.getText();
//        String fax = txFax.getText();
//        if (ma.trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Mã sinh viên không được để trống");
//            txMaNCC.requestFocus();
//            return false;
//        } else if (ten.trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Họ sinh viên không được để trống");
//            txTenNCC.requestFocus();
//            return false;
//        } else if (diachi.trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
//            txDiaChi.requestFocus();
//            return false;
//        } else if (sdt.trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
//            txSDT.requestFocus();
//            return false;
//        } else if (fax.trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
//            txFax.requestFocus();
//            return false;
//        }
//        return true;
//    }

   
}
