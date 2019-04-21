/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    
    NhanVien nv;
    KhachHang kh;

    JTextField txMaHoaDon = new JTextField(20);
    JTextField txMaNhanVien = new JTextField(20);
    JTextField txNgayLap = new JTextField(20);
    JTextField txGioLap = new JTextField(20);
    JTextField txMaKhachHang = new JTextField(17);
    MoreButton btnChonKhachHang = new MoreButton();
    JTextField txTongTien = new JTextField(20);
    
    MyTable tbChiTietHoaDon = new MyTable();
    JButton btnXoa = new JButton("Xóa");
    JButton btnSua = new JButton("Sửa");
    
    JPanel plInput = new JPanel();
    JPanel plSanPham = new JPanel();

    public HoaDonBanHang(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setBackground(new Color(181, 230, 29));
        this.setLayout(new FlowLayout());
        
        // =============== panel input =================
        int plIP_height = 180;
        plInput.setPreferredSize(new Dimension(width - 10, plIP_height));
        plInput.setBackground(new Color(181, 250, 29));
        plInput.setLayout(new FlowLayout());
        
        txMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn:"));
        txMaNhanVien.setBorder(BorderFactory.createTitledBorder("Nhân viên:"));
        txNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập:"));
        txGioLap.setBorder(BorderFactory.createTitledBorder("Giờ lập:"));
        txMaKhachHang.setBorder(BorderFactory.createTitledBorder("Khách hàng:"));
        btnChonKhachHang.setPreferredSize(new Dimension(30, 30));
        txTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền (triệu vnd):"));
        
        plInput.add(txMaHoaDon);
        plInput.add(txMaNhanVien);
        plInput.add(txNgayLap);
        plInput.add(txGioLap);
        plInput.add(txMaKhachHang);
        plInput.add(btnChonKhachHang);
        plInput.add(txTongTien);
        
        this.add(plInput);
        
        // =============== panel chọn sản phẩm ==================
        int plSP_height = 480;
        plSanPham.setPreferredSize(new Dimension(width - 10, plSP_height));
        plSanPham.setBackground(new Color(250, 250, 29));
        plSanPham.setLayout(new BorderLayout());
        
        int plBtn_height = 50;
        JPanel plButtonChiTiet = new JPanel();
        plButtonChiTiet.setBackground(Color.yellow);
        plButtonChiTiet.setPreferredSize(new Dimension(width - 10, plBtn_height));
        plButtonChiTiet.setLayout(new FlowLayout());
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnSua.setPreferredSize(new Dimension(100, 40));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
        plButtonChiTiet.add(btnXoa);
        plButtonChiTiet.add(btnSua);
        
        tbChiTietHoaDon.setPreferredSize(new Dimension(width - 10, plSP_height - plBtn_height));
        tbChiTietHoaDon.setHeaders(new String[] {"Mã", "Tên", "Đơn giá", "Số lượng", "Thành tiền"});
        tbChiTietHoaDon.addRow(new String[] {"SP1","Iphone X", "15", "2", "30"});
        tbChiTietHoaDon.addRow(new String[] {"SP1","Samsung", "12", "3", "36"});
        
        plSanPham.add(tbChiTietHoaDon, BorderLayout.CENTER);
        plSanPham.add(plButtonChiTiet, BorderLayout.SOUTH);
        
        this.add(plSanPham);
        
        // ============= panel Thanh toán ==============
        
    }

}
