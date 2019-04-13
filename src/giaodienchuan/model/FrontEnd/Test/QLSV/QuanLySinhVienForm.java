package giaodienchuan.model.FrontEnd.Test.QLSV;

import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.FrontEnd.Test.QLSV.QuanLySinhVienBUS;
import giaodienchuan.model.FrontEnd.Test.QLSV.SinhVien;
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

public class QuanLySinhVienForm extends JPanel {

    QuanLySinhVienBUS qlsv = new QuanLySinhVienBUS();
    MyTable mtb;

    JTextField txMssv = new JTextField(20);
    JTextField txHo = new JTextField(20);
    JTextField txTen = new JTextField(20);

    JButton btnXoa = new JButton("Xóa");
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnNhaplai = new JButton("Nhập lại");

    public QuanLySinhVienForm() {
        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"Mã", "Họ", "Tên"});

        // read data from database
        btnReadDBMouseClicked();

        // inputs
        txMssv.setBorder(BorderFactory.createTitledBorder("Mã số sinh viên"));
        txHo.setBorder(BorderFactory.createTitledBorder("Họ sinh viên"));
        txTen.setBorder(BorderFactory.createTitledBorder("Tên sinh viên"));

        JPanel plInput = new JPanel();
        plInput.add(txMssv);
        plInput.add(txHo);
        plInput.add(txTen);

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
                    txMssv.setText(mtb.getModel().getValueAt(i, 0).toString());
                    txHo.setText(mtb.getModel().getValueAt(i, 1).toString());
                    txTen.setText(mtb.getModel().getValueAt(i, 2).toString());
                }
            }
        });
    }

    private void btnNhapLaiMouseClicked() {
        txMssv.setText("");
        txHo.setText("");
        txTen.setText("");
        txMssv.requestFocus();
    }

    private void btnSuaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0 && checkEmpty()) {
            String mssv = mtb.getModel().getValueAt(i, 0).toString();
            String ho = txHo.getText();
            String ten = txTen.getText();

            if (!txMssv.getText().equals(mssv)) {
                JOptionPane.showMessageDialog(null, "Mã số sinh viên là Khóa Chính nên không thể thay đổi, chỉ cập nhật Họ và Tên!");
                txMssv.setText(mssv);
            }

            mtb.getModel().setValueAt(mssv, i, 0);
            mtb.getModel().setValueAt(ho, i, 1);
            mtb.getModel().setValueAt(ten, i, 2);

            qlsv.update(mssv, ho, ten);

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            String mssv = mtb.getModel().getValueAt(i, 0).toString();
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sinh viên " + mssv) == JOptionPane.OK_OPTION) {
                qlsv.delete(mssv);
                mtb.getModel().removeRow(i);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên nào để xóa");
        }
    }

    private void btnReadDBMouseClicked() {
        qlsv.readDB();
        mtb.clear();
        qlsv.dssv.forEach((sv) -> {
            mtb.addRow(new String[]{sv.getMssv(), sv.getHo(), sv.getTen()});
        });
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            SinhVien sv = new SinhVien("" + txMssv.getText(), "" + txHo.getText(), "" + txTen.getText());
            qlsv.add(sv);
            mtb.clear();
            qlsv.dssv.forEach((si) -> {
                mtb.addRow(new String[]{si.getMssv(), si.getHo(), si.getTen()});
            });
        }
    }

    private Boolean checkEmpty() {
        String mssv = txMssv.getText();
        String ho = txHo.getText();
        String ten = txTen.getText();
        if (mssv.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mã sinh viên không được để trống");
            txMssv.requestFocus();
            return false;
        } else if (ho.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Họ sinh viên không được để trống");
            txHo.requestFocus();
            return false;
        } else if (ten.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Tên sinh viên không được để trống");
            txTen.requestFocus();
            return false;
        }
        return true;
    }
}
