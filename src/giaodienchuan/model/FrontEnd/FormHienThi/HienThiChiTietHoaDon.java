package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HienThiChiTietHoaDon extends JPanel {

    QuanLyChiTietHoaDonBUS qlcthd = new QuanLyChiTietHoaDonBUS();

    JTextField txTim = new JTextField(15);
    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả"/*, "Mã hóa đơn"*/, "Mã sản phẩm", "Số lượng", "Đơn giá"});
    JButton btnRefresh = new JButton("Làm mới");
    String mahd;

    MyTable mtb;

    public HienThiChiTietHoaDon(String _mahd) {
        setLayout(new BorderLayout());
        this.mahd = _mahd;

        mtb = new MyTable();
        mtb.setHeaders(new String[]{"STT", "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá"});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(3, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd), mtb);

        JPanel plHeader = new JPanel();
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);
        
        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnRefresh);

        txTim.getDocument().addDocumentListener(new DocumentListener() {
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

        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);
    }

    private void btnXoaMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        String _masp = null;
        String _mahd = null;
        if (i >= 0) {
            _mahd = mtb.getTable().getModel().getValueAt(i, 1).toString();
            _masp = mtb.getTable().getModel().getValueAt(i, 2).toString();
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
        setDataToTable(qlcthd.search("Mã hóa đơn", this.mahd), mtb);
    }

    private void txSearchOnChange() {
        setDataToTable(qlcthd.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText()), mtb);
    }

    public String getSelectedChiTietHoaDon(int col) {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            return mtb.getModel().getValueAt(i, col).toString();
        }
        return null;
    }

    private void setDataToTable(ArrayList<ChiTietHoaDon> data, MyTable mtb) {
        mtb.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (ChiTietHoaDon hd : data) {
            mtb.addRow(new String[]{String.valueOf(stt), hd.getMaHoaDon(), hd.getMaSanPham(),
                String.valueOf(hd.getSoLuong()), String.valueOf(hd.getDonGia())});
            stt++;
        }
    }
}
