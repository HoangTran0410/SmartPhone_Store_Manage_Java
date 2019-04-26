package giaodienchuan.model.FrontEnd.FormQuanLy;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import giaodienchuan.model.BackEnd.PriceFormatter;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.ChiTietPhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.QuanLyChiTietPhieuNhapBUS;
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
import giaodienchuan.model.FrontEnd.FormChon.ChonKhachHangForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonNhaCungCapForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonNhanVienForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonSanPhamForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.FrontEnd.MyButton.DateButton;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    
    public static final int width = 1120, height = 740;

    public ThongKeForm() {
        this.setBackground(Color.darkGray);

        ThongKeSanPham tksp = new ThongKeSanPham();
        ThongKeNhanVien tknv = new ThongKeNhanVien();
        ThongKeKhachHang tkkh = new ThongKeKhachHang();
        ThongKeNhaCungCap tkncc = new ThongKeNhaCungCap();
        ThongKe_Hoang tkH = new ThongKe_Hoang();

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setPreferredSize(new Dimension(width, height));

        //add tab thong ke san pham
        tabs.addTab("Thống kê tổng quát", getIcon("icons8_pie_chart_30px.png"), tkH);
        tabs.addTab("Sản phẩm", getIcon("icons8_multiple_smartphones_30px.png"), tksp);
        tabs.addTab("Nhân viên", getIcon("icons8_assistant_30px.png"), tknv);
        tabs.addTab("Khách hàng", getIcon("icons8_user_30px.png"), tkkh);
        tabs.addTab("Nhà cung cấp", getIcon("icons8_company_30px.png"), tkncc);

        this.add(tabs);
    }

    private ImageIcon getIcon(String filename) {
        return new ImageIcon(getClass().getResource("/giaodienchuan/images/" + filename));
    }
}

class ThongKeSanPham extends JPanel {

    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    QuanLyChiTietPhieuNhapBUS qlctpnBUS = new QuanLyChiTietPhieuNhapBUS();
    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    MyTable tb;

    public ThongKeSanPham() {
        this.setLayout(new BorderLayout());

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

        btnRefresh.setIcon(new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        btnRefresh.addActionListener((ae) -> {
            qlspBUS.readDB();
            qlpnBUS.readDB();
            qlctpnBUS.readDB();
            qlnccBUS.readDB();
            qlnvBUS.readDB();
            qlhdBUS.readDB();
            qlcthdBUS.readDB();
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);

        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new MyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    private void soLuongSanPhamNhap() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã phiếu nhập", "Tên nhà cung cấp", "Ngày nhập", "Số lượng"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        int tongTatCa = 0;
        for (SanPham sp : qlspBUS.getDssp()) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{sp.getMaSP(), sp.getTenSP(), "", "", "", ""});

            for (PhieuNhap pn : qlpnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                ChiTietPhieuNhap ctpn = qlctpnBUS.getChiTiet(pn.getMaPN(), sp.getMaSP());
                if (ctpn != null) {
                    tb.addRow(new String[]{"", "",
                        pn.getMaPN(),
                        qlnccBUS.getNhaCungCap(pn.getMaNCC()).getTenNCC(),
                        String.valueOf(pn.getNgayNhap()),
                        String.valueOf(ctpn.getSoLuong())
                    });
                    tongSoLuong += ctpn.getSoLuong();
                }
            }

            tb.addRow(new String[]{"", "", "", "", mcd.getKhoangTG(), String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)});
    }

    private void soLuongSanPhamBan() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã hóa đơn", "Tên nhân viên", "Ngày lập", "Số lượng"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        int tongTatCa = 0;
        for (SanPham sp : qlspBUS.getDssp()) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{sp.getMaSP(), sp.getTenSP(), "", "", "", ""});

            for (HoaDon hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayTu(), -1, -1)) {
                ChiTietHoaDon cthd = qlcthdBUS.getChiTiet(hd.getMaHoaDon(), sp.getMaSP());
                if (cthd != null) {
                    tb.addRow(new String[]{"", "",
                        hd.getMaHoaDon(),
                        qlnvBUS.getNhanVien(hd.getMaNhanVien()).getTenNV(),
                        String.valueOf(hd.getNgayLap()),
                        String.valueOf(cthd.getSoLuong())
                    });
                    tongSoLuong += cthd.getSoLuong();
                }
            }

            tb.addRow(new String[]{"", "", "", "", mcd.getKhoangTG(), String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "", "", "", "", ""});
            tongTatCa += tongSoLuong;
        }

        tb.addRow(new String[]{"", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)});
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
    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    MyTable tb;

    public ThongKeNhanVien() {
        this.setLayout(new BorderLayout());

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

        btnRefresh.setIcon(new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        btnRefresh.addActionListener((ae) -> {
            qlspBUS.readDB();
            qlnvBUS.readDB();
            qlhdBUS.readDB();
            qlcthdBUS.readDB();
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);

        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new MyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    public void tongTienTungNhanVien_searchOnChange() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nhân viên", "Tên nhân viên", "Mã hóa đơn", "Ngày lập", "Tổng tiền hóa đơn"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        float tongTatCa = 0;
        for (NhanVien nv : qlnvBUS.getDsnv()) {
            float tongTien = 0;
            tb.addRow(new String[]{nv.getMaNV(), nv.getTenNV(), "", ""});

            for (HoaDon hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (nv.getMaNV().equals(hd.getMaNhanVien())) {
                    tb.addRow(new String[]{"", "",
                        hd.getMaHoaDon(),
                        String.valueOf(hd.getNgayLap()),
                        PriceFormatter.format(hd.getTongTien())
                    });
                    tongTien += hd.getTongTien();
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), PriceFormatter.format(tongTien)});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongTien;
        }
        tb.addRow(new String[]{"", "", "", "Tổng tất cả", PriceFormatter.format(tongTatCa)});
    }

    public void sanPhamCuaTungNhanVien_searchOnChange() {
        tb.setHeaders(new String[]{"Mã nhân viên", "Tên nhân viên", "Mã hóa đơn", "Ngày lập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng sản phẩm"});
        tb.clear();

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        int tongTatCa = 0;

        for (NhanVien nv : qlnvBUS.getDsnv()) {
            float tongSoLuong = 0;
            tb.addRow(new String[]{nv.getMaNV(), nv.getTenNV(), "", "", "", "", ""});

            for (HoaDon hd : qlhdBUS.search("Mã nhân viên", nv.getMaNV(), mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) { // tương đối -> sai
                tb.addRow(new String[]{"", "", hd.getMaHoaDon(), String.valueOf(hd.getNgayLap()), "", "", ""});

                for (ChiTietHoaDon cthd : qlcthdBUS.search("Mã hóa đơn", hd.getMaHoaDon(), -1, -1, -1, -1)) { // tương đối -> sai
                    tongSoLuong += cthd.getSoLuong();
                    tb.addRow(new String[]{"", "", "", "",
                        cthd.getMaSanPham(),
                        qlspBUS.getSanPham(cthd.getMaSanPham()).getTenSP(),
                        String.valueOf(cthd.getSoLuong())
                    });
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "Tổng số sản phẩm", String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)});
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
    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    JButton btnRefresh = new JButton("Làm mới");
    MyTable tb;

    public ThongKeKhachHang() {
        this.setLayout(new BorderLayout());

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

        btnRefresh.setIcon(new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        btnRefresh.addActionListener((ae) -> {
            qlspBUS.readDB();
            qlcthdBUS.readDB();
            qlhdBUS.readDB();
            qlkhBUS.readDB();
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);

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

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        float tongTatCa = 0;
        for (KhachHang kh : qlkhBUS.getDskh()) {
            float tongTien = 0;
            tb.addRow(new String[]{kh.getMaKH(), kh.getTenKH(), "", "", ""});

            for (HoaDon hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (kh.getMaKH().equals(hd.getMaKhachHang())) {
                    tb.addRow(new String[]{"", "",
                        hd.getMaHoaDon(),
                        String.valueOf(hd.getNgayLap()),
                        PriceFormatter.format(hd.getTongTien())
                    });
                    tongTien += hd.getTongTien();
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), PriceFormatter.format(tongTien)});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongTien;
        }

        tb.addRow(new String[]{"", "", "", "Tổng tất cả", PriceFormatter.format(tongTatCa)});
    }

    //Thong ke san pham va so luong mua cua tung khach hang
    public void sanPhamCuaTungKhachHang_searchOnChange() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã khách hàng", "Tên khách hàng", "Mã hóa đơn", "Ngày lập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng sản phẩm"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        //Tim hoa don cua tung nhan vien, sau do xuat tong tien cac hoa don len table
        int tongTatCa = 0;
        for (KhachHang kh : qlkhBUS.getDskh()) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{kh.getMaKH(), kh.getTenKH(), "", "", "", "", ""});

            for (HoaDon hd : qlhdBUS.search("Mã khách hàng", kh.getMaKH(), mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) { // tương đối -> sai 
                tb.addRow(new String[]{"", "", hd.getMaHoaDon(), String.valueOf(hd.getNgayLap()), "", "", ""});

                for (ChiTietHoaDon cthd : qlcthdBUS.search("Mã hóa đơn", hd.getMaHoaDon(), -1, -1, -1, -1)) { // tương đối -> sai
                    tongSoLuong += cthd.getSoLuong();
                    tb.addRow(new String[]{"", "", "", "",
                        cthd.getMaSanPham(),
                        qlspBUS.getSanPham(cthd.getMaSanPham()).getTenSP(),
                        String.valueOf(cthd.getSoLuong())
                    });
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "Tổng số sản phẩm", String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "", "", "", "", ""});

            tongTatCa += tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "", "Tổng tất cả", String.valueOf(tongTatCa)});
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

    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    QuanLyChiTietPhieuNhapBUS qlctpnBUS = new QuanLyChiTietPhieuNhapBUS();
    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();

    JTextField txKhoangNgayTu = new JTextField(15);
    JTextField txKhoangNgayDen = new JTextField(15);
    DatePicker dPicker1;
    DatePicker dPicker2;

    JComboBox cbTieuChi;
    MyTable tb;
    JButton btnRefresh = new JButton("Làm mới");

    public ThongKeNhaCungCap() {
        this.setLayout(new BorderLayout());

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

        cbTieuChi = new JComboBox(new String[]{"Số lượng sản phẩm", "Tổng thành tiền"});
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

        btnRefresh.setIcon(new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        btnRefresh.addActionListener((ae) -> {
            qlspBUS.readDB();
            qlpnBUS.readDB();
            qlctpnBUS.readDB();
            qlnccBUS.readDB();
            txKhoangNgayTu.setText("");
            txKhoangNgayDen.setText("");
            dPicker1.setDate(null);
            dPicker2.setDate(null);
            cbSearchOnChange();
        });

        plTieuchi.add(plKhoangNgay1);
        plTieuchi.add(plKhoangNgay2);
        plTieuchi.add(btnRefresh);
        this.add(plTieuchi, BorderLayout.NORTH);

        //Table thong ke
        tb = new MyTable();
        cbSearchOnChange();
        this.add(tb, BorderLayout.CENTER);
    }

    private void soLuongSanPhamCungCap() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Mã phiếu nhập", "Ngày lập", "Mã sản phẩm", "Tên sản phẩm", "Số lượng"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        int tongTatCa = 0;

        for (NhaCungCap ncc : qlnccBUS.getDsncc()) {
            int tongSoLuong = 0;
            tb.addRow(new String[]{ncc.getMaNCC(), ncc.getTenNCC(), "", "", "", "", ""});

            for (PhieuNhap pn : qlpnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (pn.getMaNCC().equals(ncc.getMaNCC())) {
                    tb.addRow(new String[]{"", "", pn.getMaPN(), String.valueOf(pn.getNgayNhap()), "", "", ""});

                    for (ChiTietPhieuNhap ctpn : qlctpnBUS.search("Mã phiếu nhập", pn.getMaPN())) {
                        tongSoLuong += ctpn.getSoLuong();
                        tb.addRow(new String[]{"", "", "", "",
                            ctpn.getMaSP(),
                            qlspBUS.getSanPham(ctpn.getMaSP()).getTenSP(),
                            String.valueOf(ctpn.getSoLuong())
                        });
                    }
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "Tổng số lượng:", String.valueOf(tongSoLuong)});
            tb.addRow(new String[]{"", "", "", "", "", "", ""});

            tongTatCa += tongSoLuong;
        }
        tb.addRow(new String[]{"", "", "", "", "", "Tổng tất cả:", String.valueOf(tongTatCa)});
    }

    private void tongTienThanhToan() {
        tb.clear();
        tb.setHeaders(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Mã phiếu nhập", "Ngày lập", "Mã sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"});

        MyCheckDate mcd = new MyCheckDate(txKhoangNgayTu, txKhoangNgayDen);

        float tongTatCa = 0;
        for (NhaCungCap ncc : qlnccBUS.getDsncc()) {
            float tongTien = 0;
            tb.addRow(new String[]{ncc.getMaNCC(), ncc.getTenNCC(), "", "", "", "", "", ""});

            for (PhieuNhap pn : qlpnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
                if (pn.getMaNCC().equals(ncc.getMaNCC())) {
                    tb.addRow(new String[]{"", "", pn.getMaPN(), String.valueOf(pn.getNgayNhap()), "", "", "", ""});

                    for (ChiTietPhieuNhap ctpn : qlctpnBUS.search("Mã phiếu nhập", pn.getMaPN())) {
                        tongTien += ctpn.getSoLuong() * ctpn.getDonGia();
                        tb.addRow(new String[]{"", "", "", "",
                            ctpn.getMaSP(),
                            String.valueOf(ctpn.getDonGia()),
                            String.valueOf(ctpn.getSoLuong()),
                            PriceFormatter.format(ctpn.getSoLuong() * ctpn.getDonGia())});
                    }
                }
            }
            tb.addRow(new String[]{"", "", "", mcd.getKhoangTG(), "", "", "Tổng thành tiền:", PriceFormatter.format(tongTien)});
            tb.addRow(new String[]{"", "", "", "", "", "", "", ""});

            tongTatCa += tongTien;
        }
        tb.addRow(new String[]{"", "", "", "", "", "", "Tổng tất cả:", PriceFormatter.format(tongTatCa)});
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
        if (cbTieuChi.getSelectedItem().equals("Số lượng sản phẩm")) {
            soLuongSanPhamCungCap();
        }
        if (cbTieuChi.getSelectedItem().equals("Tổng thành tiền")) {
            tongTienThanhToan();
        }
    }
}

class ThongKe_Hoang extends JPanel {

    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();
    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();
    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    QuanLyChiTietPhieuNhapBUS qlctpnBUS = new QuanLyChiTietPhieuNhapBUS();

    JTextField txNgay1 = new JTextField(7);
    JTextField txNgay2 = new JTextField(7);
    JTextField txNhanVien = new JTextField(10);
    JTextField txKhachHang = new JTextField(10);
    JTextField txNhaCC = new JTextField(10);
    JTextField txSanPham = new JTextField(10);

    DatePicker dPicker1;
    DatePicker dPicker2;
    MoreButton btnChonNhanVien = new MoreButton();
    MoreButton btnChonKhachHang = new MoreButton();
    MoreButton btnChonNhaCC = new MoreButton();
    MoreButton btnChonSanPham = new MoreButton();

    JTabbedPane tabDoiTuongThongKe = new JTabbedPane();
    JPanel plThongKeHoaDon = new JPanel();
    JPanel plThongKePhieuNhap = new JPanel();

    MyTable tbThongKeHoaDon = new MyTable();
    MyTable tbThongKePhieuNhap = new MyTable();
    
    MyTable tbKetQuaHoaDon = new MyTable();
    MyTable tbKetQuaPhieuNhap = new MyTable();
    
    JPanel plSanPham, plNhanVien, plKhachHang, plNhaCC;
    JButton btnRefresh = new JButton("Làm mới");

    public ThongKe_Hoang() {
        setLayout(new BorderLayout());

        // panel chon ngay
        DatePickerSettings ds = new DatePickerSettings();
        ds.setVisibleDateTextField(false);
        dPicker1 = new DatePicker(ds);
        dPicker2 = new DatePicker(ds.copySettings());
        dPicker1.addDateChangeListener((dce) -> {
            txNgay1.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2.addDateChangeListener((dce) -> {
            txNgay2.setText(dPicker2.getDateStringOrEmptyString());
        });
        DateButton db = new DateButton(dPicker1);
        DateButton db2 = new DateButton(dPicker2);

        JPanel plChonNgay = new JPanel();
        plChonNgay.setBorder(BorderFactory.createTitledBorder("Khoảng ngày"));

        addDocumentListener(txNgay1);
        addDocumentListener(txNgay2);
        plChonNgay.add(txNgay1);
        plChonNgay.add(dPicker1);
        plChonNgay.add(txNgay2);
        plChonNgay.add(dPicker2);

        btnChonSanPham.setPreferredSize(new Dimension(30, 30));
        btnChonNhanVien.setPreferredSize(new Dimension(30, 30));
        btnChonKhachHang.setPreferredSize(new Dimension(30, 30));
        btnChonNhaCC.setPreferredSize(new Dimension(30, 30));

        btnChonSanPham.addActionListener((ae) -> {
            ChonSanPhamForm cnv = new ChonSanPhamForm(txSanPham, null, null, null, null);
        });
        btnChonNhanVien.addActionListener((ae) -> {
            ChonNhanVienForm cnv = new ChonNhanVienForm(txNhanVien);
        });
        btnChonKhachHang.addActionListener((ae) -> {
            ChonKhachHangForm ckh = new ChonKhachHangForm(txKhachHang);
        });
        btnChonNhaCC.addActionListener((ae) -> {
            ChonNhaCungCapForm cnv = new ChonNhaCungCapForm(txNhaCC);
        });
        btnRefresh.setIcon(new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        btnRefresh.addActionListener((ae) -> {
            refresh();
        });

        plSanPham = getPanelTieuChi("Sản phẩm", txSanPham, btnChonSanPham);
        plNhanVien = getPanelTieuChi("Nhân viên", txNhanVien, btnChonNhanVien);
        plKhachHang = getPanelTieuChi("Khách hàng", txKhachHang, btnChonKhachHang);
        plNhaCC = getPanelTieuChi("Nhà cung cấp", txNhaCC, btnChonNhaCC);

        // panel chon tieu chi
        JPanel plChonTieuChi = new JPanel();
        plChonTieuChi.add(plChonNgay);
        plChonTieuChi.add(plSanPham);
        plChonTieuChi.add(plNhanVien);
        plChonTieuChi.add(plKhachHang);
        plChonTieuChi.add(plNhaCC);
        plChonTieuChi.add(btnRefresh);

        // panel tieu chi
        JPanel plTieuChi = new JPanel();
        plTieuChi.setLayout(new BorderLayout());
        plTieuChi.add(plChonTieuChi, BorderLayout.CENTER);
        this.add(plTieuChi, BorderLayout.NORTH);

        // panel thong ke hoa don (ban duoc)
        plThongKeHoaDon.setLayout(new BorderLayout());
        tbThongKeHoaDon.setHeaders(new String[]{"Hóa đơn", "Tên nhân viên", "Tên khách hàng", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        tbThongKeHoaDon.setAlignment(0, JLabel.CENTER);
        tbThongKeHoaDon.setAlignment(4, JLabel.CENTER);
        tbThongKeHoaDon.setAlignment(5, JLabel.RIGHT);
        tbThongKeHoaDon.setAlignment(6, JLabel.RIGHT);
        plThongKeHoaDon.add(tbThongKeHoaDon, BorderLayout.CENTER);
        
        tbKetQuaHoaDon.setHeaders(new String[]{"TỔNG TẤT CẢ", "", "", "", "", "", "TỔNG THU NHÂP"});
        tbKetQuaHoaDon.setPreferredSize(new Dimension(ThongKeForm.width, 75));
        tbKetQuaHoaDon.setAlignment(0, JLabel.CENTER);
        tbKetQuaHoaDon.setAlignment(4, JLabel.CENTER);
        tbKetQuaHoaDon.setAlignment(5, JLabel.RIGHT);
        tbKetQuaHoaDon.setAlignment(6, JLabel.RIGHT);
        plThongKeHoaDon.add(tbKetQuaHoaDon, BorderLayout.SOUTH);
        

        // panal thong ke phieu nhap (nhap kho)
        plThongKePhieuNhap.setLayout(new BorderLayout());
        tbThongKePhieuNhap.setHeaders(new String[]{"Phiếu nhập", "Tên nhân viên", "Tên nhà cung cấp", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        tbThongKePhieuNhap.setAlignment(0, JLabel.CENTER);
        tbThongKePhieuNhap.setAlignment(4, JLabel.CENTER);
        tbThongKePhieuNhap.setAlignment(5, JLabel.RIGHT);
        tbThongKePhieuNhap.setAlignment(6, JLabel.RIGHT);
        plThongKePhieuNhap.add(tbThongKePhieuNhap, BorderLayout.CENTER);
        
        tbKetQuaPhieuNhap.setHeaders(new String[]{"TỔNG TẤT CẢ", "", "", "", "", "", "TỔNG THU NHẬP"});
        tbKetQuaPhieuNhap.setPreferredSize(new Dimension(ThongKeForm.width, 75));
        tbKetQuaPhieuNhap.setAlignment(0, JLabel.CENTER);
        tbKetQuaPhieuNhap.setAlignment(4, JLabel.CENTER);
        tbKetQuaPhieuNhap.setAlignment(5, JLabel.RIGHT);
        tbKetQuaPhieuNhap.setAlignment(6, JLabel.RIGHT);
        plThongKePhieuNhap.add(tbKetQuaPhieuNhap, BorderLayout.SOUTH);
        

        // tabpane doi tuong thong ke
        tabDoiTuongThongKe.setBackground(Color.yellow);
        tabDoiTuongThongKe.addTab("Bán ra", getIcon("icons8_small_business_30px_3.png"), plThongKeHoaDon);
        tabDoiTuongThongKe.addTab("Nhập vào", getIcon("icons8_downloads_30px.png"), plThongKePhieuNhap);
        tabDoiTuongThongKe.addTab("Tổng", getIcon("icons8_futures_30px.png"), new JPanel());

        // event chuyen tab
        // tab ban dau la hoa don, nen cần ẩn nha cung cap 
        plNhaCC.setVisible(false);
        // event
        tabDoiTuongThongKe.addChangeListener((ce) -> {
            Boolean HoaDon_isSelected = (tabDoiTuongThongKe.getSelectedComponent() == plThongKeHoaDon);
            plNhaCC.setVisible(!HoaDon_isSelected);
            plKhachHang.setVisible(HoaDon_isSelected);
        });

        this.add(tabDoiTuongThongKe, BorderLayout.CENTER);

        // show giá trị đầu
        onChangeThongKeBanHang();
        onChangeThongKeNhapHang();
    }

    public void refresh() {
        qlcthdBUS.readDB();
        qlhdBUS.readDB();
        qlkhBUS.readDB();
        qlnccBUS.readDB();
        qlnvBUS.readDB();
        qlspBUS.readDB();
        dPicker1.setDate(null);
        dPicker2.setDate(null);
        txSanPham.setText("");
        txNhanVien.setText("");
        txKhachHang.setText("");
        txNhaCC.setText("");
        
        Boolean HoaDon_isSelected = (tabDoiTuongThongKe.getSelectedComponent() == plThongKeHoaDon);
        if(HoaDon_isSelected) {
            onChangeThongKeBanHang();
        } else {
            onChangeThongKeNhapHang();
        }
    }

    private JPanel getPanelTieuChi(String name, JTextField tx, MoreButton b) {
        JPanel result = new JPanel();
        result.setBorder(BorderFactory.createTitledBorder(name));
        tx.setBorder(BorderFactory.createTitledBorder(" "));

        addDocumentListener(tx);

        result.add(tx);
        result.add(b);

        return result;
    }

    private void addDocumentListener(JTextField txField) {
        txField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                Boolean HoaDon_isSelected = (tabDoiTuongThongKe.getSelectedComponent() == plThongKeHoaDon);
                if (HoaDon_isSelected) {
                    onChangeThongKeBanHang();
                } else {
                    onChangeThongKeNhapHang();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }
        });
    }

    private ImageIcon getIcon(String filename) {
        return new ImageIcon(getClass().getResource("/giaodienchuan/images/" + filename));
    }

    private void onChangeThongKeBanHang() {
        tbThongKeHoaDon.clear();

        int tongSLHoaDon = 0;
        int tongSLSanPham = 0;
        float tongTatCaTien = 0;

        String maspLoc = txSanPham.getText();
        String manvLoc = txNhanVien.getText();
        String makhLoc = txKhachHang.getText();

        ArrayList<NhanVien> dsnv = new ArrayList<>(); // danh sách lưu các nhân viên có làm hóa đơn
        ArrayList<KhachHang> dskh = new ArrayList<>(); // danh sách lưu các khách hàng có làm hóa đơn
        ArrayList<SanPham> dssp = new ArrayList<>(); // lưu các sản phẩm

        MyCheckDate mcd = new MyCheckDate(txNgay1, txNgay2);

        for (HoaDon hd : qlhdBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
            float tongTien = 0;
            ArrayList<ChiTietHoaDon> dscthd = qlcthdBUS.getAllChiTiet(hd.getMaHoaDon());

            if (dscthd.size() > 0) {
                NhanVien nv = qlnvBUS.getNhanVien(hd.getMaNhanVien());
                KhachHang kh = qlkhBUS.getKhachHang(hd.getMaKhachHang());

                // lọc theo textfield mã
                // bỏ qua lần lặp for này nếu nhân viên hoặc khách hàng ko thỏa bộ lọc
                if (!manvLoc.equals("") && !nv.getMaNV().equals(manvLoc)
                        || !makhLoc.equals("") && !kh.getMaKH().equals(makhLoc)) {
                    continue; // continue này sẽ lấy vòng lặp HoaDon tiếp theo
                }

                // thêm vào arraylist để đếm
                if (!dsnv.contains(nv)) {
                    dsnv.add(nv); // thêm vào nếu chưa có
                }
                if (!dskh.contains(kh)) {
                    dskh.add(kh); // thêm vào nếu chưa có
                }

                tbThongKeHoaDon.addRow(new String[]{
                    hd.getMaHoaDon() + " (" + hd.getNgayLap().toString() + ")",
                    nv.getTenNV() + " (" + nv.getMaNV() + ")",
                    kh.getTenKH() + " (" + kh.getMaKH() + ")",
                    "", "", "", ""
                });

                for (ChiTietHoaDon cthd : dscthd) {
                    SanPham sp = qlspBUS.getSanPham(cthd.getMaSanPham());

                    // lọc
                    if (!maspLoc.equals("") && !sp.getMaSP().equals(maspLoc)) {
                        continue; // continue này sẽ lấy vòng lặp ChiTietHoaDon tiếp theo
                    }

                    // thêm vào danh sách để đếm
                    if (!dssp.contains(sp)) {
                        dssp.add(sp); // thêm vào nếu chưa có
                    }

                    tbThongKeHoaDon.addRow(new String[]{"", "", "",
                        sp.getTenSP() + " (" + sp.getMaSP() + ")",
                        String.valueOf(cthd.getSoLuong()),
                        PriceFormatter.format(cthd.getDonGia()),
                        PriceFormatter.format(cthd.getSoLuong() * cthd.getDonGia())
                    });

                    tongTien += cthd.getDonGia() * cthd.getSoLuong();
                    tongSLSanPham += cthd.getSoLuong();
                }
            }
            tbThongKeHoaDon.addRow(new String[]{"", "", "", "", "", "Tổng cộng", PriceFormatter.format(tongTien)});
            tbThongKeHoaDon.addRow(new String[]{"", "", "", "", "", "", ""});

            tongTatCaTien += tongTien;
            tongSLHoaDon++;
        }
        
        tbKetQuaHoaDon.clear();
        tbKetQuaHoaDon.addRow(new String[]{
            tongSLHoaDon + " hóa đơn",
            dsnv.size() + " nhân viên",
            dskh.size() + " khách hàng",
            dssp.size() + " mặt hàng",
            tongSLSanPham + " sản phẩm",
            "",
            PriceFormatter.format(tongTatCaTien)
        });
    }

    private void onChangeThongKeNhapHang() {
        tbThongKePhieuNhap.clear();

        int tongSLPhieuNhap = 0;
        int tongSLSanPham = 0;
        float tongTatCaTien = 0;

        String maspLoc = txSanPham.getText();
        String manvLoc = txNhanVien.getText();
        String manccLoc = txNhaCC.getText();

        ArrayList<NhanVien> dsnv = new ArrayList<>(); // danh sách lưu các nhân viên có làm phiếu nhập
        ArrayList<NhaCungCap> dsncc = new ArrayList<>(); // danh sách lưu các ncc có làm phiếu nhập
        ArrayList<SanPham> dssp = new ArrayList<>(); // lưu các sản phẩm

        MyCheckDate mcd = new MyCheckDate(txNgay1, txNgay2);

        for (PhieuNhap pn : qlpnBUS.search("Tất cả", "", mcd.getNgayTu(), mcd.getNgayDen(), -1, -1)) {
            float tongTien = 0;
            ArrayList<ChiTietPhieuNhap> dsctpn = qlctpnBUS.getAllChiTiet(pn.getMaPN());

            if (dsctpn.size() > 0) {
                NhanVien nv = qlnvBUS.getNhanVien(pn.getMaNV());
                NhaCungCap ncc = qlnccBUS.getNhaCungCap(pn.getMaNCC());

                // lọc theo textfield mã
                // bỏ qua lần lặp for này nếu nhân viên hoặc nha cung cap ko thỏa bộ lọc
                if (!manvLoc.equals("") && !nv.getMaNV().equals(manvLoc)
                        || !manccLoc.equals("") && !ncc.getMaNCC().equals(manccLoc)) {
                    continue; // continue này sẽ lấy vòng lặp PhieuNhap tiếp theo
                }

                // thêm vào arraylist để đếm
                if (!dsnv.contains(nv)) {
                    dsnv.add(nv); // thêm vào nếu chưa có
                }
                if (!dsncc.contains(ncc)) {
                    dsncc.add(ncc); // thêm vào nếu chưa có
                }

                tbThongKePhieuNhap.addRow(new String[]{
                    pn.getMaPN() + " (" + pn.getNgayNhap().toString() + ")",
                    nv.getTenNV() + " (" + nv.getMaNV() + ")",
                    ncc.getTenNCC() + " (" + ncc.getMaNCC() + ")",
                    "", "", "", ""
                });

                for (ChiTietPhieuNhap ctpn : dsctpn) {
                    SanPham sp = qlspBUS.getSanPham(ctpn.getMaSP());

                    // lọc
                    if (!maspLoc.equals("") && !sp.getMaSP().equals(maspLoc)) {
                        continue; // continue này sẽ lấy vòng lặp ChiTietPhieuNhap tiếp theo
                    }

                    // thêm vào danh sách để đếm
                    if (!dssp.contains(sp)) {
                        dssp.add(sp); // thêm vào nếu chưa có
                    }

                    tbThongKePhieuNhap.addRow(new String[]{"", "", "",
                        sp.getTenSP() + " (" + sp.getMaSP() + ")",
                        String.valueOf(ctpn.getSoLuong()),
                        PriceFormatter.format(ctpn.getDonGia()),
                        PriceFormatter.format(ctpn.getSoLuong() * ctpn.getDonGia())
                    });

                    tongTien += ctpn.getDonGia() * ctpn.getSoLuong();
                    tongSLSanPham += ctpn.getSoLuong();
                }
            }
            tbThongKePhieuNhap.addRow(new String[]{"", "", "", "", "", "Tổng cộng", PriceFormatter.format(tongTien)});
            tbThongKePhieuNhap.addRow(new String[]{"", "", "", "", "", "", ""});

            tongTatCaTien += tongTien;
            tongSLPhieuNhap++;
        }

        tbKetQuaPhieuNhap.clear();
        tbKetQuaPhieuNhap.addRow(new String[]{
            tongSLPhieuNhap + " phiếu nhập",
            dsnv.size() + " nhân viên",
            dsncc.size() + " nhà cung cấp",
            dssp.size() + " mặt hàng",
            tongSLSanPham + " sản phẩm",
            "",
            PriceFormatter.format(tongTatCaTien)
        });
    }
}

class MyCheckDate {

    LocalDate fromDate = null;
    LocalDate toDate = null;
    String khoangTG = "";

    public MyCheckDate(JTextField txFrom, JTextField txTo) {
        try {
            fromDate = LocalDate.parse(txFrom.getText());
            txFrom.setForeground(Color.black);
            khoangTG += String.valueOf(fromDate);

        } catch (DateTimeParseException e) {
            txFrom.setForeground(Color.red);
            khoangTG += "Từ ngày đầu";
        }
        try {
            toDate = LocalDate.parse(txTo.getText());
            txTo.setForeground(Color.black);
            khoangTG += " đến " + String.valueOf(toDate);

        } catch (DateTimeParseException e) {
            txTo.setForeground(Color.red);
            khoangTG += " đến nay";
        }
    }

    public LocalDate getNgayTu() {
        return fromDate;
    }

    public LocalDate getNgayDen() {
        return toDate;
    }

    public String getKhoangTG() {
        return khoangTG;
    }
}
