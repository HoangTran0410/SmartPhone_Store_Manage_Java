/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.LoaiSanPham;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class BanHangForm extends JPanel {

    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();

    QuanLyChiTietHoaDonBUS qlcthdBUS = new QuanLyChiTietHoaDonBUS();
    ArrayList<ChiTietHoaDon> listChiTietHoaDon = new ArrayList<>();

    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    ArrayList<SanPham> listSanPham = new ArrayList<>();
    
    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();

    final int WIDTH = 1250, HEIGHT = 900;
    JTextField txMaHoaDon = new JTextField();
    JTextField txMaNhanVien = new JTextField();
    JTextField txNgayLap = new JTextField();
    JTextField txGioLap = new JTextField();
    JTextField txMaKhachHang = new JTextField();
    JTextField txTongTien = new JTextField();
    
    JButton btnThanhToan = new JButton("Thanh toán");
    JButton btnSua = new JButton("Sửa");
    JButton btnXoa = new JButton("Xóa");

    JTextField txSearch = new JTextField();
    
    MyTable tbChiTietHoaDon;
    MyTable tbSanPham ;

    public BanHangForm() {

        this.setLayout(new BorderLayout());
        tbChiTietHoaDon = new MyTable();     
        this.setBackground(Color.DARK_GRAY);
        
        //Chia doi form thanh 2 panel
        JPanel plLapHoaDon = new JPanel();
        plLapHoaDon.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        plLapHoaDon.setLayout(null);
        
        
        //Panel lap hoa don gom panelText,panel table,panel nut thanh toan
        plLapHoaDon.setBackground(Color.green);
        
        //panel text
        JPanel plTxt = new JPanel();
        plTxt.setLayout(null);
        plTxt.setBackground(Color.yellow);
        plTxt.setBounds(70, 50, 500, 160);
        
        txMaHoaDon.setBounds(0,0, 250, 50);
        txMaHoaDon.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        txMaHoaDon.setEditable(false);
        plTxt.add(txMaHoaDon);

        txMaNhanVien.setBounds(275, 0, 250, 50);
        txMaNhanVien.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
        txMaNhanVien.setEditable(false);
        plTxt.add(txMaNhanVien);

        txNgayLap.setBounds(0, 55, 250, 50);
        txNgayLap.setBorder(BorderFactory.createTitledBorder("Ngày lập"));
        txNgayLap.setEditable(false);
        plTxt.add(txNgayLap);

        txGioLap.setBounds(275, 55, 250, 50);
        txGioLap.setBorder(BorderFactory.createTitledBorder("Giờ lập"));
        txGioLap.setEditable(false);
        plTxt.add(txGioLap);

        txMaKhachHang.setBounds(0, 110, 250, 50);
        txMaKhachHang.setBorder(BorderFactory.createTitledBorder("Khách hàng"));
        plTxt.add(txMaKhachHang);

        txTongTien.setBounds(275, 110, 250, 50);
        txTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));
        txTongTien.setEditable(false);
        plTxt.add(txTongTien);
        
        plLapHoaDon.add(plTxt);
        
        //panel chi tiet hoa don
        JPanel plChiTietHoaDon = new JPanel();
        plChiTietHoaDon.setLayout(null);
        plChiTietHoaDon.setBackground(Color.pink);
        plChiTietHoaDon.setBounds(13, 250, WIDTH / 2 - 25, HEIGHT/2 + 50);
        tbChiTietHoaDon.setHeaders(new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        tbChiTietHoaDon.setBounds(5, 5, WIDTH / 2 - 35 , HEIGHT/2);
        tbChiTietHoaDon.setColumnsWidth(new double[]{10,20,20,15,15,15});
        btnSua.setBounds(WIDTH/2 - 130, HEIGHT/2 +10, 100, 35);
        plChiTietHoaDon.add(tbChiTietHoaDon);
        btnXoa.setBounds(WIDTH/2 - 215, HEIGHT/2 +10  , 75, 35);
        plChiTietHoaDon.add(btnSua);
        plChiTietHoaDon.add(btnXoa);
        plLapHoaDon.add(plChiTietHoaDon);
        
        //Lay du lieu test
        btnThemMouseClicked("SP1", 5);
        btnThemMouseClicked("SP2", 3);
        
        //Tu lay gio moi giay cho textfield GioLap
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txGioLap.setText(LocalTime.now().toString());
                txNgayLap.setText(LocalDate.now().toString());
            }
        }, 0, 1000);
        
        btnThanhToan.setBounds(WIDTH/2 - 175, 320+HEIGHT/2 , 150, 50);
        plLapHoaDon.add(btnThanhToan);
       
        this.add(plLapHoaDon, BorderLayout.EAST);

        //Panel chon san pham gom panel search sp , panel table san pham, panel hien thi thong tin san pham
        JPanel plChonSanPham = new JPanel();
        plChonSanPham.setLayout(null);
        plChonSanPham.setBackground(Color.BLUE);
        plChonSanPham.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        this.add(plChonSanPham, BorderLayout.WEST);
        
        //panel tim kiem san pham
        JPanel plTimKiem = new JPanel();
        plTimKiem.setLayout(new FlowLayout());
        plTimKiem.setBounds(5, 10, WIDTH/2 - 10, 50);
        txSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txSearch.setPreferredSize(new Dimension(WIDTH/2 -10,50));
        plTimKiem.add(txSearch);
        plChonSanPham.add(plTimKiem);
        

        //Panel table san pham
        tbSanPham = new MyTable();
        tbSanPham.setHeaders(new String[]{"Mã sản phẩm","Tên loại", "Tên", "Đơn giá", "Số lượng"});
        tbSanPham.setBounds(10, HEIGHT/8, WIDTH/2-20, HEIGHT/2);
        plChonSanPham.add(tbSanPham);
        
    }

//    private
    public String getSelectedChiTietHoaDon(int col) {
        int i = tbChiTietHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            return tbChiTietHoaDon.getModel().getValueAt(i, col).toString();
        }
        return null;
    }

    private void setDataToTableSanPham(ArrayList<SanPham> data, MyTable mtb) {
        mtb.clear();
        int stt = 1;
        ArrayList<LoaiSanPham> lsp = new ArrayList<>();
//        lsp = qllspBUS.search(_maloai, "Mã loại");
        // lưu số thứ tự dòng hiện tại
        for (SanPham sp : data) {
            mtb.addRow(new String[]{
                sp.getMaSP(),
                lsp.get(0).getTenLSP(),
                String.valueOf(sp.getSoLuong()),
                String.valueOf(sp.getDonGia()),
                String.valueOf(sp.getSoLuong() * sp.getDonGia())});
            stt++;
        }
    }

    private void setDataToTableChiTiet(ArrayList<SanPham> data, MyTable mtb) {
        mtb.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        mtb.addRow(new String[]{
            String.valueOf(1),
            data.get(0).getMaSP(),
            data.get(0).getTenSP(),
            String.valueOf(data.get(0).getSoLuong()),
            String.valueOf(data.get(0).getDonGia()),
            String.valueOf(data.get(0).getSoLuong() * data.get(0).getDonGia())
        });
    }

    private void btnThemMouseClicked(String _masp, int _soLuong) {
        listSanPham = qlspBUS.search(_masp, "Mã sản phẩm", -1, -1, -1, -1, 0);
        SanPham sp = qlspBUS.getSanPham(_masp);
        listSanPham.get(0).setSoLuong(_soLuong);
//          ChiTietHoaDon ct = new ChiTietHoaDon(_masp, _masp, _soLuong, TOP_ALIGNMENT);
        setDataToTableChiTiet(listSanPham, tbChiTietHoaDon);
    }

    private void btnThanhToan() {
        HoaDon hd = new HoaDon(txMaHoaDon.getText(), txMaNhanVien.getText(), txMaKhachHang.getText(), "Không", LocalDate.parse(txNgayLap.getText()), LocalTime.parse(txGioLap.getText()), Integer.parseInt(txTongTien.getText()));
        if (qlhdBUS.add(hd)) {
            for (int i = 0; i < tbChiTietHoaDon.getModel().getRowCount(); i++) {
                int j = tbChiTietHoaDon.getTable().getSelectedRow();
                ChiTietHoaDon ct = new ChiTietHoaDon(hd.getMaHoaDon(), tbChiTietHoaDon.getTable().getValueAt(j, 1).toString(), Integer.parseInt(tbChiTietHoaDon.getTable().getValueAt(j, 3).toString()), Float.parseFloat(tbChiTietHoaDon.getTable().getValueAt(j, 4).toString()));
                if (qlcthdBUS.add(ct)) {

                }
            }

        }
    }
}
