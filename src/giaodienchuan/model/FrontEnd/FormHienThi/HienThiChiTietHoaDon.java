package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.PriceFormatter;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Color;
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

public class HienThiChiTietHoaDon extends FormHienThi {

    QuanLyChiTietHoaDonBUS qlcthd = new QuanLyChiTietHoaDonBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();

    JTextField txTim = new JTextField(15);

    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã sản phẩm", "Số lượng", "Đơn giá"});
    JButton btnRefresh = new JButton("Làm mới");
    String mahd;

    JTextField txKhoangSoLuong1 = new JTextField(5);
    JTextField txKhoangSoLuong = new JTextField(5);
    JTextField txKhoangThanhTien1 = new JTextField(5);
    JTextField txKhoangThanhTien2 = new JTextField(5);

    public HienThiChiTietHoaDon(String _mahd) {
        setLayout(new BorderLayout());
        this.mahd = _mahd;

        txKhoangSoLuong1.setBorder(BorderFactory.createTitledBorder("Từ:"));
        txKhoangSoLuong.setBorder(BorderFactory.createTitledBorder("Đến:"));

        mtb = new MyTable();
        mtb.setHeaders(new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        mtb.setColumnsWidth(new double[]{.5, 4, 4, 4, 4, 4});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(1, JLabel.CENTER);
        mtb.setAlignment(3, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        mtb.setAlignment(5, JLabel.RIGHT);
        mtb.setupSort();
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd, -1, -1, -1, -1), mtb);

        JPanel plHeader = new JPanel();

        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder("Tất cả")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);

        // pl tim khoang so luong
        JPanel plTimKiemKhoangSoLuong = new JPanel();
        plTimKiemKhoangSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng:"));
        plTimKiemKhoangSoLuong.add(txKhoangSoLuong1);
        plTimKiemKhoangSoLuong.add(txKhoangSoLuong);
        plHeader.add(plTimKiemKhoangSoLuong);

        // pl tim khoang thanh tien
        JPanel plTimKiemKhoangDonGia = new JPanel();
        plTimKiemKhoangDonGia.setBorder(BorderFactory.createTitledBorder("Thành tiền"));
        txKhoangThanhTien1.setBorder(BorderFactory.createTitledBorder("Từ:"));
        txKhoangThanhTien2.setBorder(BorderFactory.createTitledBorder("Đến:"));
        plTimKiemKhoangDonGia.add(txKhoangThanhTien1);
        plTimKiemKhoangDonGia.add(txKhoangThanhTien2);
        plHeader.add(plTimKiemKhoangDonGia);

        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnRefresh);

        btnRefresh.addActionListener((ae) -> {
            refresh();
        });

        cbTypeSearch.addActionListener((ae) -> {
            txTim.setBorder(BorderFactory.createTitledBorder(String.valueOf(cbTypeSearch.getSelectedItem())));
            txTim.requestFocus();
            if (!txTim.getText().equals("")) {
                txSearchOnChange();
            }
        });

        addDocumentListener(txTim);
        addDocumentListener(txKhoangSoLuong1);
        addDocumentListener(txKhoangSoLuong);
        addDocumentListener(txKhoangThanhTien1);
        addDocumentListener(txKhoangThanhTien2);

        //Add tat ca panel vao frame
        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);
    }

    public void refresh() {
        qlcthd.readDB();
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd, -1, -1, -1, -1), mtb);
        txTim.setText("");
        txKhoangSoLuong1.setText("");
        txKhoangSoLuong.setText("");
        txKhoangThanhTien1.setText("");
        txKhoangThanhTien2.setText("");
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
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
        int soLuong1 = -1, soLuong2 = -1;
        float thanhTien1 = -1, thanhTien2 = -1;
        try {
            soLuong1 = Integer.parseInt(txKhoangSoLuong1.getText());
            txKhoangSoLuong1.setForeground(Color.black);
        } catch (NumberFormatException e) {
            txKhoangSoLuong1.setForeground(Color.red);
        }
        try {
            soLuong2 = Integer.parseInt(txKhoangSoLuong.getText());
            txKhoangSoLuong.setForeground(Color.black);
        } catch (NumberFormatException e) {
            txKhoangSoLuong.setForeground(Color.red);
        }
        try {
            thanhTien1 = Float.parseFloat(txKhoangThanhTien1.getText());
            txKhoangThanhTien1.setForeground(Color.black);
        } catch (NumberFormatException e) {
            txKhoangThanhTien1.setForeground(Color.red);
        }
        try {
            thanhTien2 = Float.parseFloat(txKhoangThanhTien2.getText());
            txKhoangThanhTien2.setForeground(Color.black);
        } catch (NumberFormatException e) {
            txKhoangThanhTien2.setForeground(Color.red);
        }
        ArrayList<ChiTietHoaDon> dscthd = new ArrayList<>();
        qlcthd.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText(), soLuong1, soLuong2, thanhTien1, thanhTien2).forEach((t) -> {
            if (t.getMaHoaDon().equals(mahd)) {
                dscthd.add(t);
            }
        });
        setDataToTable(dscthd, mtb);
    }

    private void setDataToTable(ArrayList<ChiTietHoaDon> data, MyTable mtb) {
        mtb.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (ChiTietHoaDon cthd : data) {
            mtb.addRow(new String[]{
                String.valueOf(stt),
                cthd.getMaSanPham(),
                qlspBUS.getSanPham(cthd.getMaSanPham()).getTenSP(),
                String.valueOf(cthd.getSoLuong()),
                PriceFormatter.format(cthd.getDonGia()),
                PriceFormatter.format(cthd.getSoLuong() * cthd.getDonGia())});
            stt++;
        }
    }
}
