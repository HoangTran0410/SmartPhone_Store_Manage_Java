package giaodienchuan.model.FrontEnd.FormChon;

import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChonKhachHangForm extends JFrame {

    QuanLyKhachHangBUS qlkh = new QuanLyKhachHangBUS();
    MyTable mtb;
    JTextField txTarget;

    JTextField txTim = new JTextField(15);
    JComboBox<String> cbTypeSearch;

    JButton btnOK = new JButton("Chọn");
    JButton btnCancel = new JButton("Thoát");

    final int MAKH_I = 1;

    public ChonKhachHangForm(JTextField _txTarget) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn Khách Hàng");
        this.txTarget = _txTarget;

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 400));
        mtb.setHeaders(new String[]{"STT", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại"});
        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2});
        mtb.setAlignment(0, JLabel.CENTER);

        // ======== Get Data from DB =======
        setDataToTable(qlkh.getDskh(), mtb);

        // ======== search panel ===========
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(txTim);
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại"});
        plTim.add(cbTypeSearch);

        // ======= Buttons Panel ===========
        btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnOK.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_ok_30px.png")));
        
        JPanel plBtns = new JPanel();
        plBtns.add(btnOK);
        plBtns.add(btnCancel);

        // add to this frame
        JPanel plContainer = new JPanel();
        plContainer.add(plTim);
        plContainer.add(mtb);
        plContainer.add(plBtns);
        
        this.add(plContainer, BorderLayout.CENTER);
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        // actionlistener
        cbTypeSearch.addActionListener((ActionEvent e) -> {
            txTim.requestFocus();
            if (!txTim.getText().equals("")) {
                txSearchOnChange();
            }
        });

        btnOK.addActionListener((ActionEvent ae) -> {
            int i = mtb.getTable().getSelectedRow();
            if (i >= 0) {
                String makh = mtb.getModel().getValueAt(i, MAKH_I).toString();
                this.txTarget.setText(makh);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng nào!");
            }
        });
        
        btnCancel.addActionListener((ae) -> {
            this.dispose();
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
    }

    private void txSearchOnChange() {
        setDataToTable(qlkh.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
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
}
