/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author nguye
 */
public class ThongKeForm extends JPanel {

    public ThongKeForm() {
        ThongKeSanPham tksp = new ThongKeSanPham();
        ThongKeNhanVien tknv = new ThongKeNhanVien();
        ThongKeKhachHang tkkh = new ThongKeKhachHang();
        ThongKeNhaCungCap tkncc = new ThongKeNhaCungCap();
        ThongKeThuNhap tktn = new ThongKeThuNhap();
        this.setBackground(Color.LIGHT_GRAY);
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setPreferredSize(new Dimension(1200, 850));

        //add tab thong ke san pham
        tabs.addTab("Sản phẩm", tksp);
        tabs.addTab("Nhân viên", tknv);
        tabs.addTab("Khách hàng", tkkh);
        tabs.addTab("Nhà cung cấp", tkncc);
        tabs.addTab("Thu nhập", tktn);
        this.add(tabs);
        this.setVisible(true);
    }
}

class ThongKeSanPham extends JPanel {

    QuanLyChiTietHoaDonBUS qlcthdBUS;
    ArrayList<ChiTietHoaDon> dscthd;

    ArrayList<SanPham> dssp;
    MyTable mtb;

    public ThongKeSanPham() {
        this.setLayout(new CardLayout(1200, 850));
        dssp = new QuanLySanPhamBUS().getDssp();
//        dscthd = qlcthdBUS.getDscthd();
        mtb = new MyTable();
        mtb.setHeaders(new String[]{"Mã sản phẩm","Tên sản phẩm","Loại sản phẩm","Đơn giá","Số lượng bán"});
        //Thong ke tong tien da ban cua tung san pham
        this.add(mtb);
        mtb.setSize(500, 500);
        this.setBackground(Color.green);
    }

    private void setDataToTable(ArrayList<Object> data, MyTable table) {
        table.clear();
    }

}

class ThongKeNhanVien extends JPanel {

    public ThongKeNhanVien() {
        setSize(500, 500);
    }

}

class ThongKeKhachHang extends JPanel {

    public ThongKeKhachHang() {
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
