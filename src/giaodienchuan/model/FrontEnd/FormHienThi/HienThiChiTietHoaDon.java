package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemChiTietHoaDonForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HienThiChiTietHoaDon extends JFrame {

    QuanLyChiTietHoaDonBUS qlcthd = new QuanLyChiTietHoaDonBUS();
    JTextField txtSearchbox = new JTextField();
    JComboBox timComboBox = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});
    JButton btnThemCTHD = new JButton("Thêm");
    JButton btnXoaChiTietHoaDon = new JButton("Xóa");
    String mahd;

    MyTable table;

    public HienThiChiTietHoaDon(String _mahd) {
        this.mahd = _mahd;
        setLayout(new BorderLayout());

        table = new MyTable();
        this.setSize(1000, 600);
        table.setPreferredSize(new Dimension(1200 - 250, 300));
        table.setHeaders(new String[]{"STT", "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd), table);

        JPanel pnlSearch = new JPanel();
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        pnlSearch.add(timComboBox);
        pnlSearch.add(txtSearchbox);
        pnlSearch.add(btnXoaChiTietHoaDon);
        pnlSearch.add(btnThemCTHD);
        pnlSearch.setLayout(new GridLayout(1, 4));
        pnlSearch.setPreferredSize(new Dimension(500, 75));

        btnXoaChiTietHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnXoaChiTietHoaDonMouseClicked();
            }
        });

        btnThemCTHD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnThemChiTietMouseClicked();
            }
        });
        txtSearchbox.getDocument().addDocumentListener(new DocumentListener() {
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
        this.add(pnlSearch, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void btnThemChiTietMouseClicked() {
        ThemChiTietHoaDonForm tcthd = new ThemChiTietHoaDonForm(this.mahd);
        tcthd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refresh();
            }
        });
    }

    private void btnXoaChiTietHoaDonMouseClicked() {
        int i = table.getTable().getSelectedRow();
        if (i >= 0) {
            qlcthd.delete(table.getTable().getModel().getValueAt(i, 1).toString(), table.getTable().getModel().getValueAt(i, 2).toString());
        }
        refresh();
    }

    public void refresh() {
        qlcthd.readDB();
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd), table);
    }

    private void txSearchOnChange() {
        setDataToTable(qlcthd.search(timComboBox.getSelectedItem().toString(), txtSearchbox.getText()), table);
    }

    private void setDataToTable(ArrayList<ChiTietHoaDon> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (ChiTietHoaDon hd : data) {
            table.addRow(new String[]{String.valueOf(stt), hd.getMaHoaDon(), hd.getMaSanPham(),
                String.valueOf(hd.getSoLuong()), String.valueOf(hd.getDonGia())});
            stt++;
        }
    }

}
