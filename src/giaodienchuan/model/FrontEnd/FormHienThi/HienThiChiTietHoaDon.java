package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemChiTietHoaDonForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
        this.setTitle("Chi tiết hóa đơn "+_mahd);

        table = new MyTable();
        this.setSize(1000, 600);
        table.setPreferredSize(new Dimension(1200 - 250, 300));
        table.setHeaders(new String[]{"STT", "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd), table);

        JPanel plBtn = new JPanel();
        btnThemCTHD.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoaChiTietHoaDon.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        plBtn.add(btnThemCTHD);
        plBtn.add(btnXoaChiTietHoaDon);
        plBtn.setBackground(new Color(150, 150, 150));
        
        JPanel pnlSearch = new JPanel();
        pnlSearch.setLayout(new BorderLayout());
        pnlSearch.setPreferredSize(new Dimension(500, 120));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        pnlSearch.add(timComboBox,BorderLayout.WEST);
        pnlSearch.add(txtSearchbox,BorderLayout.CENTER);
        pnlSearch.add(plBtn,BorderLayout.NORTH);

        
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
//        this.add(plBtn, BorderLayout.NORTH);
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
        String _masp = null;
        String _mahd = null;
        if (i >= 0) {
            _mahd = table.getTable().getModel().getValueAt(i, 1).toString();
            _masp = table.getTable().getModel().getValueAt(i, 2).toString();
        }
        if (_mahd != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa chi tiết này của hóa đơn " + _mahd + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyChiTietHoaDonBUS().delete(_mahd, _masp);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
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
