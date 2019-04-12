package giaodienchuan.model.BackEnd.QuanLyNCC;

import giaodienchuan.model.FrontEnd.Form.MyTable;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NhaCungCapFORM extends JPanel {

    NhaCungCapBUS BUS = new NhaCungCapBUS();
    MyTable mtb;

    JTextField txMaNCC = new JTextField(10);
    JTextField txTenNCC = new JTextField(10);
    JTextField txDiaChi = new JTextField(10);
    JTextField txSDT = new JTextField(10);
    JTextField txFax = new JTextField(10);

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    public NhaCungCapFORM() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"Mã NCC", "Tên NCC", "Địa chỉ", "SDT", "Fax"});

        // read data from database
        btnReadDBMouseClicked();

        // inputs
        txMaNCC.setBorder(BorderFactory.createTitledBorder("Mã nhà cung cấp"));
        txTenNCC.setBorder(BorderFactory.createTitledBorder("Tên nhà cung cấp"));
        txDiaChi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));
        txSDT.setBorder(BorderFactory.createTitledBorder("SDT"));
        txFax.setBorder(BorderFactory.createTitledBorder("Fax"));

        JPanel plInput = new JPanel();
        plInput.add(txMaNCC);
        plInput.add(txTenNCC);
        plInput.add(txDiaChi);
        plInput.add(txSDT);
        plInput.add(txFax);

        // buttons
        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnNhaplai);
        plBtn.add(btnReadDB);

        // container
        this.add(plInput);
        this.add(plBtn);
        this.add(mtb);

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

        mtb.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                int i = mtb.getTable().getSelectedRow();
                if (i >= 0) {
                    txMaNCC.setText(mtb.getModel().getValueAt(i, 0).toString());
                    txTenNCC.setText(mtb.getModel().getValueAt(i, 1).toString());
                    txDiaChi.setText(mtb.getModel().getValueAt(i, 2).toString());
                    txSDT.setText(mtb.getModel().getValueAt(i, 3).toString());
                    txFax.setText(mtb.getModel().getValueAt(i, 4).toString());
                }
            }
        });
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

            mtb.getModel().setValueAt(maNCC, i, 0);
            mtb.getModel().setValueAt(tenNCC, i, 1);
            mtb.getModel().setValueAt(diaChi, i, 2);
            mtb.getModel().setValueAt(SDT, i, 3);
            mtb.getModel().setValueAt(Fax, i, 4);

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
                mtb.getModel().removeRow(i);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        BUS.readDB();
        mtb.clear();
        BUS.dsncc.forEach((ncc) -> {
            mtb.addRow(new String[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSDT(), ncc.getFax()});
        });
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            NhaCungCap ncc = new NhaCungCap(txMaNCC.getText(), txTenNCC.getText(), txDiaChi.getText(), txSDT.getText(), txFax.getText());
            BUS.add(ncc);
            mtb.clear();
            BUS.dsncc.forEach((n) -> {
                mtb.addRow(new String[]{n.getMaNCC(), n.getTenNCC(), n.getDiaChi(), n.getSDT(), n.getFax()});
            });
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
