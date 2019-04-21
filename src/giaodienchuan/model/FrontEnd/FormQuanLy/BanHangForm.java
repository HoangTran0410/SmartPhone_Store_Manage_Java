/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.FrontEnd.FormChon.ChonKhachHangForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonNhanVienForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class BanHangForm extends JPanel {
    
    public BanHangForm(int width, int height) {
        setLayout(null);
        add(new HoaDonBanHang(width - 550, 0, 550, height));
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

    public HoaDonBanHang(int _x, int _y, int _width, int _height) {
        this.setBounds(_x, _y, _width, _height);
        this.setBackground(new Color(181, 230, 29));
        this.setLayout(new FlowLayout());
        
        // =============== panel input =================
        int plIP_height = 170;
        JPanel plInput = new JPanel();
        plInput.setPreferredSize(new Dimension(_width - 10, plIP_height));
        plInput.setBackground(new Color(181, 250, 29));
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
                    if(khachHang != null) {
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
                    if(nhanVien != null) {
                        txNhanVien.setText(nhanVien.getTenNV()+ " (" + nhanVien.getMaNV()+ ")");
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
        
        // alignment
//        txMaHoaDon.setHorizontalAlignment(0);
//        txNhanVien.setHorizontalAlignment(0);
//        txNgayLap.setHorizontalAlignment(0);
//        txGioLap.setHorizontalAlignment(0);
//        txKhachHang.setHorizontalAlignment(0);
//        txMaHoaDon.setHorizontalAlignment(0);
//        txTongTien.setHorizontalAlignment(0);

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
        txMaHoaDon.setText(new QuanLyHoaDonBUS().getNextID());
        txNgayLap.setText(LocalDate.now().toString());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txGioLap.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        }, 0, 1000);
        
        // set editable
        txMaHoaDon.setEditable(false);
        txNgayLap.setEditable(false);
        txGioLap.setEditable(false);
        
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
        
        // =============== panel chọn sản phẩm ==================
        int plSP_height = 480;
        JPanel plSanPham = new JPanel();
        plSanPham.setPreferredSize(new Dimension(_width - 10, plSP_height));
        plSanPham.setBackground(new Color(250, 250, 29));
        plSanPham.setLayout(new BorderLayout());
        
        int plBtn_height = 50;
        JPanel plButtonChiTiet = new JPanel();
        plButtonChiTiet.setLayout(new FlowLayout(FlowLayout.CENTER));
        plButtonChiTiet.setBackground(Color.yellow);
        plButtonChiTiet.setPreferredSize(new Dimension(_width - 10, plBtn_height));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_wrench_30px.png")));
        plButtonChiTiet.add(btnXoa);
        plButtonChiTiet.add(btnSua);
        
        tbChiTietHoaDon.setPreferredSize(new Dimension(_width - 10, plSP_height - plBtn_height));
        tbChiTietHoaDon.setHeaders(new String[] {"Mã", "Tên", "Đơn giá", "Số lượng", "Thành tiền"});
        tbChiTietHoaDon.addRow(new String[] {"SP1","Iphone X", "15", "2", "30"});
        tbChiTietHoaDon.addRow(new String[] {"SP1","Samsung", "12", "3", "36"});
        
        plSanPham.add(tbChiTietHoaDon, BorderLayout.CENTER);
        plSanPham.add(plButtonChiTiet, BorderLayout.SOUTH);
        
        this.add(plSanPham);
        
        // ============= panel Thanh toán ==============
        int plTT_height = _height - plIP_height - plSP_height - 20;
        JPanel plThanhToan = new JPanel();
        plThanhToan.setLayout(new FlowLayout(FlowLayout.RIGHT));
        plThanhToan.setPreferredSize(new Dimension(_width - 10, plTT_height));
        plThanhToan.setBackground(new Color(50, 60, 70));
        
        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnThanhToan.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_us_dollar_30px.png")));
        
        plThanhToan.add(btnHuy);
        plThanhToan.add(btnThanhToan);
        
        this.add(plThanhToan);
    }

}
