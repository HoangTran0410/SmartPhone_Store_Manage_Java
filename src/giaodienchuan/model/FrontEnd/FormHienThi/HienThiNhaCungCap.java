/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Admin
 */
public class HienThiNhaCungCap extends FormHienThi {

    QuanLyNhaCungCapBUS BUS = new QuanLyNhaCungCapBUS();
    JTextField txTim = new JTextField(20);
    JComboBox<String> cbTypeSearch;
    JButton btnRefresh = new JButton("Làm mới");
    final int MANCC_I = 1, TENNCC_I = 2, DIACHI_I = 3, SDT_I = 4, FAX_I = 5;

    public HienThiNhaCungCap() {
        setLayout(new BorderLayout());

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã NCC", "Tên NCC", "Địa chỉ", "SDT", "Fax"});// stt dau ara ???
        mtb.setColumnsWidth(new double[]{.5, .5, 2, 3, 2, 2});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(5, JLabel.CENTER);
        mtb.setupSort();
        setDataToTable(BUS.getDsncc(), mtb);

        cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã NCC", "Tên NCC", "Địa chỉ", "Điện thoại", "Fax"});
        JPanel plHeader = new JPanel();
        JPanel plTim = new JPanel();

        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        // nó k đọc dc database cho chut, t dang gap loi

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

    private void setDataToTable(ArrayList<NhaCungCap> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (NhaCungCap ncc : data) {
            table.addRow(new String[]{String.valueOf(stt), ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(),
                String.valueOf(ncc.getSDT()), String.valueOf(ncc.getFax())});
            stt++;
        }
    }

    public void refresh() {
        BUS.readDB();
        setDataToTable(BUS.getDsncc(), mtb);
    }

    private void txSearchOnChange() {
        setDataToTable(BUS.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }
}
