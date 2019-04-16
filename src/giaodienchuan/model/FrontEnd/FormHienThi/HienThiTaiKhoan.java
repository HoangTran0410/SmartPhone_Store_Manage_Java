package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.QuanLyTaiKhoanBUS;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.TaiKhoan;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HienThiTaiKhoan extends JPanel {

    QuanLyTaiKhoanBUS qltk = new QuanLyTaiKhoanBUS();
    MyTable mtb;

    JTextField txTim = new JTextField(15);
    JComboBox<String> cbTypeSearch;
    JButton btnRefresh = new JButton("Làm mới");

    // index
    final int USERNAME_I = 1, PASSWORD_I = 2, MANV_I = 3, MAQUYEN_I = 4;

    public HienThiTaiKhoan() {
        setLayout(new BorderLayout());

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Tên tài khoản", "Mật khẩu", "Mã nhân viên", "Mã quyền"});
        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2});
        mtb.setAlignment(0, JLabel.CENTER);
        setDataToTable(qltk.getDstk(), mtb);

        // ======== search panel ===========
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Tên tài khoản", "Mật khẩu", "Mã nhân viên", "Mã quyền"});

        JPanel plHeader = new JPanel();
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnRefresh);

        cbTypeSearch.addActionListener((ActionEvent e) -> {
            txTim.requestFocus();
            if (!txTim.getText().equals("")) {
                txSearchOnChange();
            }
        });

        btnRefresh.addActionListener((ae) -> {
            refresh();
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

        //=========== add all to this jpanel ===========
        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);
    }

    public void refresh() {
        qltk.readDB();
        setDataToTable(qltk.getDstk(), mtb);
    }

    public String getSelectedSanPham() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            return mtb.getModel().getValueAt(i, USERNAME_I).toString();
        }
        return null;
    }

    private void txSearchOnChange() {
        setDataToTable(qltk.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void setDataToTable(ArrayList<TaiKhoan> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (TaiKhoan tk : data) {
            table.addRow(new String[]{String.valueOf(stt), tk.getUsername(), tk.getPassword(), tk.getMaNV(), tk.getMaQuyen()});
            stt++;
        }
    }
}
