/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.FrontEnd.FormChon.ChonKhachHangForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonNhanVienForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author DELL
 */
public class BanHangForm extends JPanel {

    public static HoaDonBanHang hdbh;
    public static ChonSanPhamBanHang csp;

    public BanHangForm(int width, int height) {
        setLayout(null);

        csp = new ChonSanPhamBanHang(0, 0, width - 555, height);

        this.add(csp);

        hdbh = new HoaDonBanHang(width - 550, 0, 550, height);

        this.add(hdbh);
    }

}

class ChonSanPhamBanHang extends JPanel {

    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    MyTable tbSanPham = new MyTable();
    JTextField txTimKiem = new JTextField(30);

    JLabel lbImage = new JLabel();
    JTextField txMaSP = new JTextField(15);
    JTextField txLoaiSP = new JTextField(15);
    JTextField txTenSP = new JTextField(15);
    JTextField txDonGia = new JTextField(15);
    JTextField txSoLuong = new JTextField(10);

    JButton btnThem = new JButton("Thêm");

    public ChonSanPhamBanHang(int _x, int _y, int _width, int _height) {
        this.setBounds(_x, _y, _width, _height);
        this.setBackground(new Color(59, 68, 75));
        this.setLayout(new BorderLayout());

        // panel hiển thị sản phẩm
        int plSP_height = _height - 300;
        JPanel plSanPham = new JPanel();
        plSanPham.setPreferredSize(new Dimension(_width - 10, plSP_height));
        plSanPham.setBackground(new Color(49, 49, 49));
        plSanPham.setLayout(new BorderLayout());

        txTimKiem.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTimKiem.setHorizontalAlignment(JLabel.CENTER);
        addDocumentListener(txTimKiem);
        plSanPham.add(txTimKiem, BorderLayout.NORTH);

        tbSanPham.setHeaders(new String[]{"Mã", "Loại", "Tên", "Đơn giá", "Số lượng"});
        tbSanPham.setColumnsWidth(new double[]{.5, .5, 3, 1, .5});
        tbSanPham.setAlignment(3, JLabel.RIGHT);
        tbSanPham.setAlignment(4, JLabel.RIGHT);
        plSanPham.add(tbSanPham, BorderLayout.CENTER);

        this.add(plSanPham, BorderLayout.CENTER);

        // panel chi tiết sản phẩm chọn
        JPanel plChiTiet = new JPanel();
        plChiTiet.setPreferredSize(new Dimension(_width - 10, 258));
        plChiTiet.setBackground(new Color(100, 100, 100));
        plChiTiet.setLayout(new BorderLayout());

        lbImage.setBackground(Color.yellow);
        lbImage.setPreferredSize(new Dimension(200, 200));
        plChiTiet.add(lbImage, BorderLayout.WEST);

        JPanel plTextField = new JPanel();
        plTextField.setLayout(new FlowLayout());
        txMaSP.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        txLoaiSP.setBorder(BorderFactory.createTitledBorder("Loại sản phẩm"));
        txTenSP.setBorder(BorderFactory.createTitledBorder("Tên sản phẩm"));
        txDonGia.setBorder(BorderFactory.createTitledBorder("Đơn giá"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        plTextField.add(txMaSP);
        plTextField.add(txLoaiSP);
        plTextField.add(txTenSP);
        plTextField.add(txDonGia);
        plTextField.add(txSoLuong);

        btnThem.addActionListener((ae) -> {
            try {
                String masp = txMaSP.getText();
                int soluong = Integer.parseInt(txSoLuong.getText());
                BanHangForm.hdbh.addChiTiet(masp, soluong);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(txSoLuong, "Số lượng phải là số nguyên!");
                txSoLuong.requestFocus();
            }
        });

        plChiTiet.add(plTextField, BorderLayout.CENTER);
        plChiTiet.add(btnThem, BorderLayout.SOUTH);

        this.add(plChiTiet, BorderLayout.SOUTH);

        // event
        tbSanPham.getTable().addMouseListener(new MouseAdapter() { // copy từ HienThiSanPham
            @Override
            public void mouseReleased(MouseEvent me) {
                String masp = getSelectedSanPham(0);
                if (masp != null) {
                    showInfo(masp, 1);
                }
            }
        });

        setDataToTable(qlspBUS.getDssp(), tbSanPham);
    }

    public void showInfo(String masp, int soluong) {
        // https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        if (masp != null) {
            // show hình
            for (SanPham sp : qlspBUS.getDssp()) {
                if (sp.getMaSP().equals(masp)) {
                    int w = lbImage.getWidth();
                    int h = lbImage.getHeight();
                    ImageIcon img = new ImageIcon(getClass().getResource("/giaodienchuan/images/Product Images/" + sp.getFileNameHinhAnh()));
                    Image imgScaled = img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    lbImage.setIcon(new ImageIcon(imgScaled));
                    
                    // show info
                    String loai = new QuanLyLoaiSanPhamBUS().getLoaiSanPham(sp.getMaLSP()).getTenLSP();
                    txMaSP.setText(sp.getMaSP());
                    txTenSP.setText(sp.getTenSP());
                    txLoaiSP.setText(loai + " - " + sp.getMaLSP());
                    txDonGia.setText(String.valueOf(sp.getDonGia()));
                    txSoLuong.setText(String.valueOf(soluong));
                }
            }
        }
    }

    private void addDocumentListener(JTextField tx) {
        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        tx.getDocument().addDocumentListener(new DocumentListener() {
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

    public void txSearchOnChange() {
        setDataToTable(qlspBUS.search(txTimKiem.getText(), "Tất cả", -1, -1, -1, -1, 0), tbSanPham);
    }

    public String getSelectedSanPham(int col) {
        int i = tbSanPham.getTable().getSelectedRow();
        if (i >= 0) {
            return tbSanPham.getModel().getValueAt(i, col).toString();
        }
        return null;
    }

    private void setDataToTable(ArrayList<SanPham> data, MyTable table) {
        table.clear();
        for (SanPham sp : data) {
            if (sp.getTrangThai() == 0) {
                table.addRow(new String[]{
                    sp.getMaSP(),
                    sp.getMaLSP(),
                    sp.getTenSP(),
                    String.valueOf(sp.getDonGia()),
                    String.valueOf(sp.getSoLuong()),});
            }
        }
    }
}

class HoaDonBanHang extends JPanel {

    NhanVien nhanVien;
    KhachHang khachHang;

    JTextField txMaHoaDon = new JTextField(20);
    JTextField txNhanVien = new JTextField(17);
    JTextField txNgayLap = new JTextField(20);
    JTextField txGioLap = new JTextField(20);
    JTextField txKhachHang = new JTextField(17);
    JTextField txTongTien = new JTextField(20);

    MoreButton btnChonNhanVien = new MoreButton();
    MoreButton btnChonKhachHang = new MoreButton();

    MyTable tbChiTietHoaDon = new MyTable();
    JButton btnXoa = new JButton("Xóa");
    JButton btnSua = new JButton("Sửa");
    JButton btnThanhToan = new JButton("Thanh toán");
    JButton btnHuy = new JButton("Hủy");

    ArrayList<ChiTietHoaDon> dscthd = new ArrayList<>();

    public HoaDonBanHang(int _x, int _y, int _width, int _height) {
        this.setBounds(_x, _y, _width, _height);
        this.setBackground(new Color(59, 68, 75));
        this.setLayout(new FlowLayout());

        // =============== panel input =================
        int plIP_height = 180;
        JPanel plInput = new JPanel();
        plInput.setPreferredSize(new Dimension(_width - 10, plIP_height));
        plInput.setBackground(new Color(49, 49, 49));
        plInput.setLayout(new FlowLayout());

        // btn
        btnChonKhachHang.setPreferredSize(new Dimension(30, 30));
        btnChonKhachHang.addActionListener((ae) -> {
            ChonKhachHangForm ckh = new ChonKhachHangForm(txKhachHang);
            ckh.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String makh = txKhachHang.getText();
                    khachHang = new QuanLyKhachHangBUS().getKhachHang(makh);
                    if (khachHang != null) {
                        txKhachHang.setText(khachHang.getTenKH() + " (" + khachHang.getMaKH() + ")");
                    }
                }
            });
        });

        btnChonNhanVien.setPreferredSize(new Dimension(30, 30));
        btnChonNhanVien.addActionListener((ae) -> {
            ChonNhanVienForm cnv = new ChonNhanVienForm(txNhanVien);
            cnv.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String mavn = txNhanVien.getText();
                    nhanVien = new QuanLyNhanVienBUS().getNhanVien(mavn);
                    if (nhanVien != null) {
                        txNhanVien.setText(nhanVien.getTenNV() + " (" + nhanVien.getMaNV() + ")");
                    }
                }
            });
        });

        // set border
        txMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn:"));
        txNhanVien.setBorder(BorderFactory.createTitledBorder("Nhân viên:"));
        txNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập:"));
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
        txMaHoaDon.setFont(f);
        txTongTien.setFont(f);

        // set Text
        if (LoginForm.nhanVienLogin != null) {
            nhanVien = LoginForm.nhanVienLogin;
            txNhanVien.setText(nhanVien.getTenNV() + " (" + nhanVien.getMaNV() + ")");
        }

        txMaHoaDon.setText(new QuanLyHoaDonBUS().getNextID());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txNgayLap.setText(LocalDate.now().toString());
                txGioLap.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                if (txNhanVien.getText().equals("")
                        || txKhachHang.getText().equals("")
                        || txTongTien.getText().equals("")
                        || txTongTien.getText().equals("0")) {
                    btnThanhToan.setEnabled(false);
                } else {
                    btnThanhToan.setEnabled(true);
                }
            }
        }, 0, 1000);

        // set editable
        txMaHoaDon.setEditable(false);
        txNhanVien.setEditable(false);
        txKhachHang.setEditable(false);
        txNgayLap.setEditable(false);
        txGioLap.setEditable(false);
        txTongTien.setEditable(false);

        // add to panel
        plInput.add(txMaHoaDon);
        plInput.add(txNhanVien);
        plInput.add(btnChonNhanVien);
        plInput.add(txNgayLap);
        plInput.add(txGioLap);
        plInput.add(txKhachHang);
        plInput.add(btnChonKhachHang);
        plInput.add(txTongTien);

        this.add(plInput);

        // =============== panel các sản phẩm đã chọn ==================
        int plSP_height = 495;
        JPanel plSanPham = new JPanel();
        plSanPham.setPreferredSize(new Dimension(_width - 10, plSP_height));
        plSanPham.setBackground(new Color(250, 250, 29));
        plSanPham.setLayout(new BorderLayout());

        int plBtn_height = 50;
        JPanel plButtonChiTiet = new JPanel();
        plButtonChiTiet.setLayout(new FlowLayout(FlowLayout.CENTER));
        plButtonChiTiet.setBackground(new Color(220, 220, 220));
        plButtonChiTiet.setPreferredSize(new Dimension(_width - 10, plBtn_height));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_wrench_30px.png")));

        btnXoa.addActionListener((ae) -> {
            btnXoaOnClick();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaOnClick();
        });

        plButtonChiTiet.add(btnXoa);
        plButtonChiTiet.add(btnSua);

        tbChiTietHoaDon.setPreferredSize(new Dimension(_width - 10, plSP_height - plBtn_height));
        tbChiTietHoaDon.setHeaders(new String[]{"STT", "Mã", "Tên", "Số lượng", "Đơn giá", "Thành tiền"});
        tbChiTietHoaDon.setColumnsWidth(new double[]{1, 2, 4, 2.2, 2, 3});
        tbChiTietHoaDon.setAlignment(0, JLabel.CENTER);
        tbChiTietHoaDon.setAlignment(1, JLabel.CENTER);
        tbChiTietHoaDon.setAlignment(3, JLabel.CENTER);
        tbChiTietHoaDon.setAlignment(4, JLabel.RIGHT);
        tbChiTietHoaDon.setAlignment(5, JLabel.RIGHT);

        plSanPham.add(tbChiTietHoaDon, BorderLayout.CENTER);
        plSanPham.add(plButtonChiTiet, BorderLayout.SOUTH);

        this.add(plSanPham);

        // ============= panel Thanh toán ==============
        int plTT_height = _height - plIP_height - plSP_height - 20;
        JPanel plThanhToan = new JPanel();
        plThanhToan.setLayout(new FlowLayout(FlowLayout.RIGHT));
        plThanhToan.setPreferredSize(new Dimension(_width - 10, plTT_height));
        plThanhToan.setBackground(new Color(59, 68, 75));

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnThanhToan.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_us_dollar_30px.png")));

        btnHuy.addActionListener((ae) -> {
            btnHuyOnClick();
        });
        btnThanhToan.addActionListener((ae) -> {
            btnThanhToanOnClick();
        });

        plThanhToan.add(btnHuy);
        plThanhToan.add(btnThanhToan);

        this.add(plThanhToan);
    }

    private void btnHuyOnClick() {

    }

    private void btnThanhToanOnClick() {
        HoaDon hd = new HoaDon(
                txMaHoaDon.getText(), 
                nhanVien.getMaNV(), 
                khachHang.getMaKH(), 
                "KM1", 
                LocalDate.parse(txNgayLap.getText()), 
                LocalTime.parse(txGioLap.getText()), 
                Float.parseFloat(txTongTien.getText()));
        new QuanLyHoaDonBUS().add(hd);
        
        QuanLyChiTietHoaDonBUS qlcthd = new QuanLyChiTietHoaDonBUS();
        for(ChiTietHoaDon ct : dscthd) {
            qlcthd.add(ct);
        }
        JOptionPane.showMessageDialog(this, "Thanh toán thành công");
        refresh();
    }
    
    public void refresh() {
        txKhachHang.setText("");
        txTongTien.setText("");
        setDataToTable(new ArrayList<>(), tbChiTietHoaDon);
    }

    private void btnXoaOnClick() {
        int i = tbChiTietHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            dscthd.remove(i);
            setDataToTable(dscthd, tbChiTietHoaDon);
        }
    }

    private void btnSuaOnClick() {
        int i = tbChiTietHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            ChiTietHoaDon ct = dscthd.get(i);
            BanHangForm.csp.showInfo(ct.getMaSanPham(), ct.getSoLuong());
            
            dscthd.remove(i);
            setDataToTable(dscthd, tbChiTietHoaDon);
        }
    }

    public void addChiTiet(String masp, int soluong) {
        SanPham sp = new QuanLySanPhamBUS().getSanPham(masp);
        if (soluong > sp.getSoLuong()) {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong kho không đủ (" + sp.getSoLuong() + ")");
            return;
        }
        ChiTietHoaDon cthd = new ChiTietHoaDon(new QuanLyHoaDonBUS().getNextID(), masp, soluong, sp.getDonGia());
        dscthd.add(cthd);

        setDataToTable(dscthd, tbChiTietHoaDon);
    }

    public void setDataToTable(ArrayList<ChiTietHoaDon> arr, MyTable t) {
        t.clear();
        float tongtien = 0;
        int stt = 1;
        for (ChiTietHoaDon cthd : arr) {
            String masp = cthd.getMaSanPham();
            String tensp = new QuanLySanPhamBUS().getSanPham(masp).getTenSP();
            int soluong = cthd.getSoLuong();
            float dongia = cthd.getDonGia();
            float thanhtien = soluong * dongia;

            t.addRow(new String[]{
                String.valueOf(stt),
                masp,
                tensp,
                String.valueOf(soluong),
                String.valueOf(dongia),
                String.valueOf(thanhtien)
            });
            stt++;
            tongtien += thanhtien;
        }

        txTongTien.setText(String.valueOf(tongtien));
    }
}
