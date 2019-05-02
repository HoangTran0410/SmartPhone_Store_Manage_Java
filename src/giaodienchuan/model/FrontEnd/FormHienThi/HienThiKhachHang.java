package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
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

public class HienThiKhachHang extends FormHienThi {

    QuanLyKhachHangBUS qlkh = new QuanLyKhachHangBUS();

    JTextField txTim = new JTextField(15);
    JComboBox<String> cbTypeSearch;
    JButton btnRefresh = new JButton("Làm mới");

    // index
    final int MAKH_I = 1, TEN_I = 2, DIACHI_I = 3, SDT_I = 4;

    public HienThiKhachHang() {
        setLayout(new BorderLayout());

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Trạng thái"});
        mtb.setColumnsWidth(new double[]{.5, 1.5, 2.5, 3, 2, .5});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(5, JLabel.CENTER);
        mtb.setupSort();
        setDataToTable(qlkh.getDskh(), mtb);

        // ======== search panel ===========
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Trạng thái"});

        JPanel plHeader = new JPanel();
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder("Tất cả")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnRefresh);

        cbTypeSearch.addActionListener((ActionEvent e) -> {
            txTim.setBorder(BorderFactory.createTitledBorder(cbTypeSearch.getSelectedItem().toString()));
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
        qlkh.readDB();
        setDataToTable(qlkh.getDskh(), mtb);
    }

    private void txSearchOnChange() {
        setDataToTable(qlkh.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void setDataToTable(ArrayList<KhachHang> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        Boolean hienKhachHangAn = LoginForm.quyenLogin.getChiTietQuyen().contains("qlKhachHang");
        for (KhachHang kh : data) {
            if (hienKhachHangAn || kh.getTrangThai() == 0) {
                table.addRow(new String[]{
                    String.valueOf(stt),
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getDiaChi(),
                    kh.getSDT(),
                    (kh.getTrangThai() == 0 ? "Hiện" : "Ẩn")
                });
                stt++;
            }
        }
    }
}
