package giaodienchuan.model.FrontEnd.FormQuanLy.ThongKe;

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
import giaodienchuan.model.FrontEnd.MyButton.RefreshButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

        ThongKe_Hoang tkH = new ThongKe_Hoang();
        ThongKeForm_NewVersion tk2 = new ThongKeForm_NewVersion();

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setPreferredSize(new Dimension(width, height));

        //add tab thong ke san pham
//        tabs.addTab("Thống kê ver2", getIcon("icons8_company_30px.png"), tk2);
        tabs.addTab("Thống kê tổng quát", getIcon("icons8_pie_chart_30px.png"), tkH);
        tabs.addTab("Sản phẩm", getIcon("icons8_multiple_smartphones_30px.png"), null);
        tabs.addTab("Nhân viên", getIcon("icons8_assistant_30px.png"), null);
        tabs.addTab("Khách hàng", getIcon("icons8_user_30px.png"), null);
        tabs.addTab("Nhà cung cấp", getIcon("icons8_company_30px.png"), null);

        tabs.addChangeListener((ce) -> {
            int i = tabs.getSelectedIndex();
            if (tabs.getComponentAt(i) == null) {
                switch (tabs.getTitleAt(i)) {
                    case "Sản phẩm":
                        tabs.setComponentAt(i, new ThongKeSanPham());
                        break;
                    case "Nhân viên":
                        tabs.setComponentAt(i, new ThongKeNhanVien());
                        break;
                    case "Khách hàng":
                        tabs.setComponentAt(i, new ThongKeKhachHang());
                        break;
                    case "Nhà cung cấp":
                        tabs.setComponentAt(i, new ThongKeNhaCungCap());
                        break;
                }
            }
        });

        this.add(tabs);
    }

    private ImageIcon getIcon(String filename) {
        return new ImageIcon(getClass().getResource("/giaodienchuan/images/" + filename));
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
    JPanel plThongKeSoLuong = new JPanel();

    MyTable tbThongKeHoaDon = new MyTable();
    MyTable tbThongKePhieuNhap = new MyTable();

    MyTable tbKetQuaHoaDon = new MyTable();
    MyTable tbKetQuaPhieuNhap = new MyTable();

    JPanel plSanPham, plNhanVien, plKhachHang, plNhaCC;
    RefreshButton btnRefresh = new RefreshButton();

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
        
        txNgay1.setBorder(BorderFactory.createTitledBorder("Từ"));
        txNgay2.setBorder(BorderFactory.createTitledBorder("Đến"));

        JPanel plChonNgay = new JPanel();
        plChonNgay.setBorder(BorderFactory.createTitledBorder("Khoảng ngày"));

        addDocumentListener(txNgay1);
        addDocumentListener(txNgay2);
        plChonNgay.add(txNgay1);
        plChonNgay.add(dPicker1);
        plChonNgay.add(txNgay2);
        plChonNgay.add(dPicker2);
        
        // event
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
        this.add(plChonTieuChi, BorderLayout.NORTH);

        // panel thong ke hoa don (ban duoc)
        plThongKeHoaDon.setLayout(new BorderLayout());
        tbThongKeHoaDon.setHeaders(new String[]{"Hóa đơn", "Tên nhân viên", "Tên khách hàng", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        tbThongKeHoaDon.setAlignment(0, JLabel.CENTER);
        tbThongKeHoaDon.setAlignment(4, JLabel.CENTER);
        tbThongKeHoaDon.setAlignment(5, JLabel.RIGHT);
        tbThongKeHoaDon.setAlignment(6, JLabel.RIGHT);
        plThongKeHoaDon.add(tbThongKeHoaDon, BorderLayout.CENTER);

        tbKetQuaHoaDon.setHeaders(new String[]{"TỔNG TẤT CẢ", "", "", "", "", "", "TỔNG BÁN RA"});
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

        tbKetQuaPhieuNhap.setHeaders(new String[]{"TỔNG TẤT CẢ", "", "", "", "", "", "TỔNG NHẬP VÀO"});
        tbKetQuaPhieuNhap.setPreferredSize(new Dimension(ThongKeForm.width, 75));
        tbKetQuaPhieuNhap.setAlignment(0, JLabel.CENTER);
        tbKetQuaPhieuNhap.setAlignment(4, JLabel.CENTER);
        tbKetQuaPhieuNhap.setAlignment(5, JLabel.RIGHT);
        tbKetQuaPhieuNhap.setAlignment(6, JLabel.RIGHT);
        plThongKePhieuNhap.add(tbKetQuaPhieuNhap, BorderLayout.SOUTH);
        
        // panel thong ke tong so
        plThongKeSoLuong = new JPanel();
        setDataToPanelTong();

        // tabpane doi tuong thong ke
        tabDoiTuongThongKe.setBackground(Color.yellow);
        tabDoiTuongThongKe.addTab("Tổng", getIcon("icons8_futures_30px.png"), plThongKeSoLuong);
        tabDoiTuongThongKe.addTab("Bán ra", getIcon("icons8_small_business_30px_3.png"), plThongKeHoaDon);
        tabDoiTuongThongKe.addTab("Nhập vào", getIcon("icons8_downloads_30px.png"), plThongKePhieuNhap);

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
    
    private void setDataToPanelTong() {
        plThongKeSoLuong.removeAll();
        plThongKeSoLuong.add(getJPanelTong("SẢN PHẨM", "icons8_google_mobile_100px.png", qlspBUS.getDssp().size(), Color.BLUE));
        plThongKeSoLuong.add(getJPanelTong("NHÂN VIÊN", "icons8_assistant_100px.png", qlnvBUS.getDsnv().size(), Color.BLUE));
        plThongKeSoLuong.add(getJPanelTong("KHÁCH HÀNG", "icons8_person_male_100px.png", qlkhBUS.getDskh().size(), Color.BLUE));
        plThongKeSoLuong.add(getJPanelTong("NHÀ CUNG CẤP", "icons8_company_100px.png", qlnccBUS.getDsncc().size(), Color.BLUE));
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
        if (HoaDon_isSelected) {
            onChangeThongKeBanHang();
        } else {
            onChangeThongKeNhapHang();
        }
        setDataToPanelTong();
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
    
    private JPanel getJPanelTong(String name, String iconName, int soluong, Color c) {
        JPanel result = new JPanel();
        result.setLayout(new BorderLayout());
        result.setPreferredSize(new Dimension(ThongKeForm.width / 4 - 15, 200));
        result.setBorder(BorderFactory.createLineBorder(c));
        
        // hinh anh
        JLabel lbIcon = new JLabel();
        lbIcon.setIcon(getIcon(iconName));
        result.add(lbIcon, BorderLayout.WEST);
        
        // tieu de
        JPanel plLeft = new JPanel();
        
        JLabel lbTieuDe = new JLabel(name, JLabel.CENTER);
        lbTieuDe.setPreferredSize(new Dimension(ThongKeForm.width / 4 - 100, 70));
        lbTieuDe.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        plLeft.add(lbTieuDe);
        
        JLabel lbSoLuong = new JLabel(String.valueOf(soluong), JLabel.CENTER);
        lbSoLuong.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        lbSoLuong.setForeground(c);
        plLeft.add(lbSoLuong);
        
        result.add(plLeft, BorderLayout.CENTER);
        
        return result;
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
