package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyChiTietHoaDonForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import giaodienchuan.model.BackEnd.PriceFormatter;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.QuanLyKhuyenMaiBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.WritePDF.WritePDF;
import giaodienchuan.model.FrontEnd.MyButton.DateButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HienThiHoaDon extends FormHienThi {

    WritePDF pdfWriter;

    QuanLyHoaDonBUS qlhd = new QuanLyHoaDonBUS();
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();
    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();
    QuanLyKhuyenMaiBUS qlkmBUS = new QuanLyKhuyenMaiBUS();

    JTextField txTim = new JTextField(10);
    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã khuyến mãi", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});

    JTextField txMaHoaDon = new JTextField(15);
    JTextField txNhanVien = new JTextField(15);
    JTextField txNgayLap = new JTextField(15);
    JTextField txKhuyenMai = new JTextField(15);
    JTextField txGioLap = new JTextField(15);
    JTextField txKhachHang = new JTextField(15);
    JTextField txTongTien = new JTextField(15);

    JButton btnRefresh = new JButton("Làm mới");
    
    JButton btnDetails = new JButton("Xem chi tiết");
    JTextField txKhoangNgay1 = new JTextField(8);
    JTextField txKhoangNgay2 = new JTextField(8);
    JTextField txKhoangTien1 = new JTextField(5);
    JTextField txKhoangTien2 = new JTextField(5);

    DatePicker dPicker1;
    DatePicker dPicker2;

    public HienThiHoaDon() {
        setLayout(new BorderLayout());

        // khoang ngay
        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker1.setDateToToday();
        dPicker2.setDateToToday();

        // calendar icon
        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        txKhoangNgay1.setBorder(BorderFactory.createTitledBorder("Từ:"));
        txKhoangNgay2.setBorder(BorderFactory.createTitledBorder("Đến:"));

        //Tao taoble hien thi thong tin hoa don
        mtb = new MyTable();
        mtb.setHeaders(new String[]{"STT", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Mã khuyến mãi", "Ngày lập", "Giờ lập", "Tổng tiền"});
        mtb.setColumnsWidth(new double[]{.5, 1, 1, 1, 1, 1, 1, 1});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(7, JLabel.RIGHT);
        mtb.setupSort();
        setDataToTable(qlhd.getDshd(), mtb);

        // Tao panel header chuaw
        JPanel plHeader = new JPanel();

        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder("Tất cả")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);

        // pl tim khoang ngay
        JPanel plTimKiemKhoangNgay = new JPanel();
        plTimKiemKhoangNgay.setBorder(BorderFactory.createTitledBorder("Ngày lập:"));
        plTimKiemKhoangNgay.add(txKhoangNgay1);
        plTimKiemKhoangNgay.add(dPicker1);
        plTimKiemKhoangNgay.add(txKhoangNgay2);
        plTimKiemKhoangNgay.add(dPicker2);
        plHeader.add(plTimKiemKhoangNgay);

        // pl tim khoang tien
        JPanel plKhoangTien = new JPanel();
        plKhoangTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
        txKhoangTien1.setBorder(BorderFactory.createTitledBorder("Từ:"));
        txKhoangTien2.setBorder(BorderFactory.createTitledBorder("Đến"));
        plKhoangTien.add(txKhoangTien1);
        plKhoangTien.add(txKhoangTien2);
        plHeader.add(plKhoangTien);

        // button detail refresh
        btnDetails.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_show_property_30px.png")));
        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnDetails);
        plHeader.add(btnRefresh);

        // panel hiển thị các thông tin hóa đơn - copy from BanHangForm
        JPanel plThongTin = new JPanel();
        plThongTin.setPreferredSize(new Dimension(300, 170));
        // set border
        txMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn:"));
        txNhanVien.setBorder(BorderFactory.createTitledBorder("Nhân viên:"));
        txNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập:"));
        txKhuyenMai.setBorder(BorderFactory.createTitledBorder("Khuyến mãi:"));
        txGioLap.setBorder(BorderFactory.createTitledBorder("Giờ lập:"));
        txKhachHang.setBorder(BorderFactory.createTitledBorder("Khách hàng:"));
        txTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền (triệu vnd):"));

        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaHoaDon.setFont(f);
        txNhanVien.setFont(f);
        txNgayLap.setFont(f);
        txGioLap.setFont(f);
        txKhachHang.setFont(f);
        txKhuyenMai.setFont(f);
        txTongTien.setFont(f);

        // add to panel
        plThongTin.add(txMaHoaDon);
        plThongTin.add(txNhanVien);
        plThongTin.add(txKhachHang);
        plThongTin.add(txKhuyenMai);
        plThongTin.add(txTongTien);
        plThongTin.add(txNgayLap);
        plThongTin.add(txGioLap);

        //=========== add all to this jpanel ===========
        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);
        this.add(plThongTin, BorderLayout.SOUTH);

        // add action listener
        // event
        mtb.getTable().addMouseListener(new MouseAdapter() { // copy từ HienThiSanPham
            @Override
            public void mouseReleased(MouseEvent me) {
                String mahd = getSelectedRow(1);
                if (mahd != null) {
                    showInfo(mahd);
                }
            }
        });
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

        btnDetails.addActionListener((ae) -> {
            btnDetailsMouseClicked();
        });

        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgay1.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgay2.setText(dPicker2.getDateStringOrEmptyString());
        });
        addDocumentListener(txTim);
        addDocumentListener(txKhoangNgay1);
        addDocumentListener(txKhoangNgay2);
        addDocumentListener(txKhoangTien1);
        addDocumentListener(txKhoangTien2);
    }

    private void showInfo(String mahd) {
        if (mahd != null) {
            // show hình
            for (HoaDon hd : qlhd.getDshd()) {
                if (hd.getMaHoaDon().equals(mahd)) {
                    // show info
                    String tennhanvien = qlnvBUS.getNhanVien(hd.getMaNhanVien()).getTenNV();
                    String tenkhach = qlkhBUS.getKhachHang(hd.getMaKhachHang()).getTenKH();
                    String tenkhuyenmai = qlkmBUS.getKhuyenMai(hd.getMaKhuyenMai()).getTenKM();

                    txMaHoaDon.setText(hd.getMaHoaDon());
                    txNhanVien.setText(tennhanvien + " - " + hd.getMaNhanVien());
                    txKhachHang.setText(tenkhach + " - " + hd.getMaKhachHang());
                    txKhuyenMai.setText(tenkhuyenmai + " - " + hd.getMaKhuyenMai());
                    txNgayLap.setText(hd.getNgayLap().toString());
                    txGioLap.setText(hd.getGioLap().toString());
                    txTongTien.setText(PriceFormatter.format(hd.getTongTien()));
                    return;
                }
            }
        }
    }

    public void refresh() {
        qlhd.readDB();
        setDataToTable(qlhd.getDshd(), mtb);
        dPicker1.setDate(null);
        dPicker2.setDate(null);
        txTim.setText("");
        txKhoangNgay1.setText("");
        txKhoangNgay2.setText("");
        txKhoangTien1.setText("");
        txKhoangTien2.setText("");
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
        LocalDate ngay1 = null, ngay2 = null;
        int tong1 = -1, tong2 = -1;
        try {
            ngay1 = java.time.LocalDate.parse(txKhoangNgay1.getText());
            txKhoangNgay1.setForeground(Color.black);
        } catch (DateTimeParseException e) {
            txKhoangNgay1.setForeground(Color.red);
        }
        try {
            ngay2 = java.time.LocalDate.parse(txKhoangNgay2.getText());
            txKhoangNgay2.setForeground(Color.black);
        } catch (DateTimeParseException e) {
            txKhoangNgay2.setForeground(Color.red);
        }
        try {
            tong1 = Integer.parseInt(txKhoangTien1.getText());
            txKhoangTien1.setForeground(Color.black);
        } catch (NumberFormatException e) {
            txKhoangTien1.setForeground(Color.red);
        }
        try {
            tong2 = Integer.parseInt(txKhoangTien2.getText());
            txKhoangTien2.setForeground(Color.black);
        } catch (NumberFormatException e) {
            txKhoangTien2.setForeground(Color.red);
        }
        setDataToTable(qlhd.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText(), ngay1, ngay2, tong1, tong2), mtb);
    }

    private void btnDetailsMouseClicked() {
        String mahd = getSelectedRow(1);
        if (mahd != null) {
            QuanLyChiTietHoaDonForm htcthd = new QuanLyChiTietHoaDonForm(mahd);
            htcthd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refresh();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn nào để xem!");
        }
    }

    private void setDataToTable(ArrayList<HoaDon> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (HoaDon hd : data) {
            table.addRow(new String[]{
                String.valueOf(stt),
                hd.getMaHoaDon(),
                hd.getMaNhanVien(),
                hd.getMaKhachHang(),
                hd.getMaKhuyenMai(),
                String.valueOf(hd.getNgayLap()),
                String.valueOf(hd.getGioLap()),
                PriceFormatter.format(hd.getTongTien())});
            stt++;
        }
    }
}
