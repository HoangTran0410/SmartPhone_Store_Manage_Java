package giaodienchuan.model.FrontEnd.FormQuanLy;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.ChiTietPhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.ChiTietPhieuNhapBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.PhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.QuanLyPhieuNhapBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.FrontEnd.MyButton.DateButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author nguye
 */
public class ThongKeForm extends JPanel {

    public ThongKeForm() {
        this.setBackground(Color.darkGray);

        ThongKeSanPham tksp = new ThongKeSanPham();
        ThongKeNhanVien tknv = new ThongKeNhanVien();
        ThongKeKhachHang tkkh = new ThongKeKhachHang();
        ThongKeNhaCungCap tkncc = new ThongKeNhaCungCap();
        ThongKeThuNhap tktn = new ThongKeThuNhap();
        ThongKe_Hoang tkH = new ThongKe_Hoang();

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setPreferredSize(new Dimension(1100, 720));

        //add tab thong ke san pham
        tabs.addTab("Sản phẩm", getIcon("icons8_multiple_smartphones_30px.png"), tksp);
        tabs.addTab("Nhân viên", getIcon("icons8_assistant_30px.png"), tknv);
        tabs.addTab("Khách hàng", getIcon("icons8_user_30px.png"), tkkh);
        tabs.addTab("Nhà cung cấp", getIcon("icons8_company_30px.png"), tkncc);
        tabs.addTab("Thu nhập", getIcon("icons8_us_dollar_30px.png"), tktn);
        tabs.addTab("Hoàng's Thống kê", getIcon("icons8_pie_chart_30px.png"), tkH);
        this.add(tabs);
        this.setVisible(true);
    }

    private ImageIcon getIcon(String filename) {
        return new ImageIcon(getClass().getResource("/giaodienchuan/images/" + filename));
    }
}

class ThongKeSanPham extends JPanel {

    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    ArrayList<ChiTietHoaDon> dscthd;

    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    ArrayList<HoaDon> dshd;

    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    ArrayList<SanPham> dssp;

    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    ArrayList<PhieuNhap> dspn;

    ChiTietPhieuNhapBUS qlctpnBUS = new ChiTietPhieuNhapBUS();
    ArrayList<ChiTietPhieuNhap> dsctpn;

    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
    ArrayList<NhaCungCap> dsncc;
    
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;

    MyTable tb;

    public ThongKeSanPham() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.CYAN);
        dssp = qlspBUS.getDssp();
        dshd = qlhdBUS.getDshd();
        dscthd = qlcthdBUS.getDscthd();
        dspn = qlpnBUS.getDspn();

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel tieu chi
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Số lượng nhập", "Số lượng bán"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new MyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    private void soLuongSanPhamNhap() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã phiếu nhập", "Tên nhà cung cấp", "Ngày nhập", "Số lượng"});
        LocalDate ngay1 = null, ngay2 = null;
        String khoangTG = "";

        try {
            ngay1 = LocalDate.parse(txKhoangNgayTu.getText());
            txKhoangNgayTu.setForeground(Color.black);
            khoangTG += String.valueOf(ngay1);
        } catch (DateTimeParseException e) {
            txKhoangNgayTu.setForeground(Color.red);
            khoangTG += "Từ ngày đầu";
        }
        try {
            ngay2 = LocalDate.parse(txKhoangNgayDen.getText());
            txKhoangNgayDen.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(ngay2);
        } catch (DateTimeParseException e) {
            txKhoangNgayDen.setForeground(Color.red);
            khoangTG += " đến nay";
        }

        for (SanPham sp : dssp) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{sp.getMaSP(), sp.getTenSP(), "", "", "", ""});
            for (PhieuNhap pn : qlpnBUS.search("Tất cả", "", ngay1, ngay2, -1, -1)) {
                ChiTietPhieuNhap ctpn = qlctpnBUS.getChiTiet(pn.getMaPN(), sp.getMaSP());
                if (ctpn != null) {
                    tb.addRow(new String[]{"", "", pn.getMaPN(), qlnccBUS.getNhaCungCap(pn.getMaNCC()).getTenNCC(), String.valueOf(pn.getNgayNhap()), String.valueOf(ctpn.getSoLuong())});
                    tongSoLuong += ctpn.getSoLuong();
                }
            }
            tb.addRow(new String[]{"", "", "", "", khoangTG, String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "","", "", "", ""});
        }
    }

    private void soLuongSanPhamBan() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã hóa đơn", "Tên nhân viên", "Ngày lập", "Số lượng"});
        LocalDate ngay1 = null, ngay2 = null;
        String khoangTG = "";

        try {
            ngay1 = LocalDate.parse(txKhoangNgayTu.getText());
            txKhoangNgayTu.setForeground(Color.black);
            khoangTG += String.valueOf(ngay1);
        } catch (DateTimeParseException e) {
            txKhoangNgayTu.setForeground(Color.red);
            khoangTG += "Từ ngày đầu";
        }
        try {
            ngay2 = LocalDate.parse(txKhoangNgayDen.getText());
            txKhoangNgayDen.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(ngay2);
        } catch (DateTimeParseException e) {
            txKhoangNgayDen.setForeground(Color.red);
            khoangTG += " đến nay";
        }

        for (SanPham sp : dssp) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{sp.getMaSP(), sp.getTenSP(), "", "", "", ""});
            for (HoaDon hd : qlhdBUS.search("Tất cả", "", ngay1, ngay2, -1, -1)) {
                ChiTietHoaDon cthd = qlcthdBUS.getChiTiet(hd.getMaHoaDon(), sp.getMaSP());
                if (cthd != null) {
                    tb.addRow(new String[]{"", "", hd.getMaHoaDon(), qlnvBUS.getNhanVien(hd.getMaNhanVien()).getTenNV(), String.valueOf(hd.getNgayLap()), String.valueOf(cthd.getSoLuong())});
                    tongSoLuong += cthd.getSoLuong();
                }
            }
            tb.addRow(new String[]{"", "", "", "", khoangTG, String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "","", "", "", ""});
        }
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Số lượng nhập")) {
            soLuongSanPhamNhap();
        }
        if (cbTieuChi.getSelectedItem().equals("Số lượng bán")) {
            soLuongSanPhamBan();
        }
    }
}

class ThongKeNhanVien extends JPanel {

    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    ArrayList<ChiTietHoaDon> dscthd;

    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    ArrayList<HoaDon> dshd;

    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    ArrayList<SanPham> dssp;

    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();
    ArrayList<NhanVien> dsnv;

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;

    MyTable tb;

    public ThongKeNhanVien() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.CYAN);
        dssp = qlspBUS.getDssp();
        dshd = qlhdBUS.getDshd();
        dsnv = qlnvBUS.getDsnv();

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel tieu chi
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Tổng tiền", "Số lượng sản phẩm"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new MyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    public void tongTienTungNhanVien_searchOnChange() {
        tb.setHeaders(new String[]{"Mã nhân viên", "Tên nhân viên", "Mã hóa đơn", "Ngày lập", "Tổng tiền hóa đơn"});
        tb.clear();
        LocalDate ngay1;
        LocalDate ngay2;
        String khoangTG = "";
        try {
            ngay1 = LocalDate.parse(txKhoangNgayTu.getText());
            txKhoangNgayTu.setForeground(Color.black);
            khoangTG += String.valueOf(ngay1);
        } catch (DateTimeParseException e) {
            txKhoangNgayTu.setForeground(Color.red);
            ngay1 = null;
            khoangTG += "Từ ngày đầu";
        }
        try {
            ngay2 = LocalDate.parse(txKhoangNgayDen.getText());
            txKhoangNgayDen.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(ngay2);
        } catch (DateTimeParseException e) {
            txKhoangNgayDen.setForeground(Color.red);
            ngay2 = null;
            khoangTG += " đến nay";
        }

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        for (NhanVien nv : dsnv) {
            float tong = 0;
            tb.addRow(new String[]{nv.getMaNV(), nv.getTenNV(), "", ""});
            for (HoaDon hd : dshd) {
                if (nv.getMaNV().equals(hd.getMaNhanVien())) {
                    for (HoaDon hhd : qlhdBUS.search("Mã hóa đơn", hd.getMaHoaDon(), ngay1, ngay2, -1, -1)) {
                        tb.addRow(new String[]{"", "", hd.getMaHoaDon(), String.valueOf(hhd.getNgayLap()), String.valueOf(hhd.getTongTien())});
                        tong += hhd.getTongTien();
                    }
                }
            }
            tb.addRow(new String[]{"", "", "", khoangTG, String.valueOf(tong)});
            tb.addRow(new String[]{"", "","", "", "", ""});
        }
    }

    public void sanPhamCuaTungNhanVien_searchOnChange() {
        tb.setHeaders(new String[]{"Mã nhân viên", "Tên nhân viên", "Mã hóa đơn", "Ngày lập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng sản phẩm"});
        tb.clear();
        LocalDate ngay1;
        LocalDate ngay2;
        String khoangTG = "";
        try {
            ngay1 = LocalDate.parse(txKhoangNgayTu.getText());
            txKhoangNgayTu.setForeground(Color.black);
            khoangTG += String.valueOf(ngay1);
        } catch (DateTimeParseException e) {
            txKhoangNgayTu.setForeground(Color.red);
            ngay1 = null;
            khoangTG += "Từ ngày đầu";
        }
        try {
            ngay2 = LocalDate.parse(txKhoangNgayDen.getText());
            txKhoangNgayDen.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(ngay2);
        } catch (DateTimeParseException e) {
            txKhoangNgayDen.setForeground(Color.red);
            ngay2 = null;
            khoangTG += " đến nay";
        }

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        for (NhanVien nv : dsnv) {
            float tong = 0;
            tb.addRow(new String[]{nv.getMaNV(), nv.getTenNV(), "", "", "", "", ""});
            for (HoaDon hd : qlhdBUS.search("Mã nhân viên", nv.getMaNV(), ngay1, ngay2, -1, -1)) {
                tb.addRow(new String[]{"", "", hd.getMaHoaDon(), String.valueOf(hd.getNgayLap()), "", "", ""});
                for (ChiTietHoaDon cthd : qlcthdBUS.search("Mã hóa đơn", hd.getMaHoaDon(), -1, -1, -1, -1)) {
                    tong += cthd.getSoLuong();
                    tb.addRow(new String[]{"", "", "", "", cthd.getMaSanPham(), qlspBUS.getSanPham(cthd.getMaSanPham()).getTenSP(), String.valueOf(cthd.getSoLuong())});
                }
            }
            tb.addRow(new String[]{"", "", "", khoangTG, "", "Tổng số sản phẩm", String.valueOf(tong)});
            tb.addRow(new String[]{"", "","", "", "", ""});
        }
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Tổng tiền")) {
            tongTienTungNhanVien_searchOnChange();
        }
        if (cbTieuChi.getSelectedItem().equals("Số lượng sản phẩm")) {
            sanPhamCuaTungNhanVien_searchOnChange();
        }
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }
}

class ThongKeKhachHang extends JPanel {

    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    ArrayList<HoaDon> dshd;

    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    ArrayList<ChiTietHoaDon> dscthd;

    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    ArrayList<SanPham> dssp;

    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();
    ArrayList<KhachHang> dskh;

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;

    MyTable tb;

    public ThongKeKhachHang() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.CYAN);
        dssp = qlspBUS.getDssp();
        dshd = qlhdBUS.getDshd();
        dskh = qlkhBUS.getDskh();
        dscthd = qlcthdBUS.getDscthd();

        DatePickerSettings pickerSettings = new DatePickerSettings();
        pickerSettings.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(pickerSettings);
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgayTu.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2 = new DatePicker(pickerSettings.copySettings());
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgayDen.setText(dPicker2.getDateStringOrEmptyString());
        });

        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        //Panel tieu chi
        JPanel plTieuchi = new JPanel();
        plTieuchi.setLayout(new FlowLayout());

        cbTieuChi = new JComboBox(new String[]{"Tổng tiền", "Số lượng sản phẩm"});
        cbTieuChi.addActionListener((ae) -> {
            cbSearchOnChange();
        });
        plTieuchi.add(cbTieuChi);

        JPanel plKhoangNgay1 = new JPanel();
        txKhoangNgayTu.setBorder(BorderFactory.createTitledBorder("Từ:"));
        addDocumentListener(txKhoangNgayTu);
        plKhoangNgay1.add(txKhoangNgayTu);
        plKhoangNgay1.add(dPicker1);
        JPanel plKhoangNgay2 = new JPanel();
        txKhoangNgayDen.setBorder(BorderFactory.createTitledBorder("Đến"));
        addDocumentListener(txKhoangNgayDen);
        plKhoangNgay2.add(txKhoangNgayDen);
        plKhoangNgay2.add(dPicker2);

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new MyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    //Thong ke tong tien hoa don cua tung khach hang
    public void tongTienTungKhachHang_searchOnChange() {
        tb.setHeaders(new String[]{"Mã khách hàng", "Tên khách hàng", "Mã hóa đơn", "Ngày lập", "Tổng tiền hóa đơn"});
        tb.clear();
        LocalDate ngay1;
        LocalDate ngay2;
        String khoangTG = "";
        try {
            ngay1 = LocalDate.parse(txKhoangNgayTu.getText());
            txKhoangNgayTu.setForeground(Color.black);
            khoangTG += String.valueOf(ngay1);
        } catch (DateTimeParseException e) {
            txKhoangNgayTu.setForeground(Color.red);
            ngay1 = null;
            khoangTG += "Từ ngày đầu";
        }
        try {
            ngay2 = LocalDate.parse(txKhoangNgayDen.getText());
            txKhoangNgayDen.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(ngay2);
        } catch (DateTimeParseException e) {
            txKhoangNgayDen.setForeground(Color.red);
            ngay2 = null;
            khoangTG += " đến nay";
        }

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        for (KhachHang kh : dskh) {
            float tong = 0;
            tb.addRow(new String[]{kh.getMaKH(), kh.getTenKH(), "", "", ""});
            for (HoaDon hd : dshd) {
                if (kh.getMaKH().equals(hd.getMaKhachHang())) {
                    for (HoaDon hhd : qlhdBUS.search("Mã hóa đơn", hd.getMaHoaDon(), ngay1, ngay2, -1, -1)) {
                        tb.addRow(new String[]{"", "", 
                            hd.getMaHoaDon(), 
                            String.valueOf(hhd.getNgayLap()), 
                            String.valueOf(hhd.getTongTien())
                        });
                        tong += hhd.getTongTien();
                    }
                }
            }
            tb.addRow(new String[]{"", "", "", khoangTG, String.valueOf(tong)});
            tb.addRow(new String[]{"", "","", "", "", ""});
        }
    }

    //Thong ke san pham va so luong mua cua tung khach hang
    public void sanPhamCuaTungKhachHang_searchOnChange() {
        tb.setHeaders(new String[]{"Mã khách hàng", "Tên khách hàng", "Mã hóa đơn", "Ngày lập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng sản phẩm"});
        tb.clear();
        LocalDate ngay1;
        LocalDate ngay2;
        String khoangTG = "";
        try {
            ngay1 = LocalDate.parse(txKhoangNgayTu.getText());
            txKhoangNgayTu.setForeground(Color.black);
            khoangTG += String.valueOf(ngay1);
        } catch (DateTimeParseException e) {
            txKhoangNgayTu.setForeground(Color.red);
            ngay1 = null;
            khoangTG += "Từ ngày đầu";
        }
        try {
            ngay2 = LocalDate.parse(txKhoangNgayDen.getText());
            txKhoangNgayDen.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(ngay2);
        } catch (DateTimeParseException e) {
            txKhoangNgayDen.setForeground(Color.red);
            ngay2 = null;
            khoangTG += " đến nay";
        }

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        for (KhachHang kh : dskh) {
            float tong = 0;
            tb.addRow(new String[]{kh.getMaKH(), kh.getTenKH(), "", "", "", "", ""});
            for (HoaDon hd : qlhdBUS.search("Mã khách hàng", kh.getMaKH(), ngay1, ngay2, -1, -1)) {
                tb.addRow(new String[]{"", "", hd.getMaHoaDon(), String.valueOf(hd.getNgayLap()), "", "", ""});
                for (ChiTietHoaDon cthd : qlcthdBUS.search("Mã hóa đơn", hd.getMaHoaDon(), -1, -1, -1, -1)) {
                    tong += cthd.getSoLuong();
                    tb.addRow(new String[]{"", "", "", "", cthd.getMaSanPham(), qlspBUS.getSanPham(cthd.getMaSanPham()).getTenSP(), String.valueOf(cthd.getSoLuong())});
                }
            }
            tb.addRow(new String[]{"", "", "", khoangTG, "", "Tổng số sản phẩm", String.valueOf(tong)});
            tb.addRow(new String[]{"", "","", "", "", ""});
        }
    }

    public void cbSearchOnChange() {
        if (cbTieuChi.getSelectedItem().equals("Tổng tiền")) {
            tongTienTungKhachHang_searchOnChange();
        }
        if (cbTieuChi.getSelectedItem().equals("Số lượng sản phẩm")) {
            sanPhamCuaTungKhachHang_searchOnChange();
        }
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                cbSearchOnChange();
            }
        });
    }
}

class ThongKeNhaCungCap extends JPanel {

    public ThongKeNhaCungCap() {
    }

}

class ThongKeThuNhap extends JPanel {

    public ThongKeThuNhap() {
    }

}

class ThongKe_Hoang extends JPanel {

    public ThongKe_Hoang() {

    }
}
