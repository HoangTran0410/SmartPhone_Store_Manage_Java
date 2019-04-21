/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyChiTietPhieuNhap.ChiTietPhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyChiTietPhieuNhap.QuanLyChiTietPhieuNhapBUS;
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

/**
 *
 * @author Admin
 */
public class HienThiChiTietPN extends JPanel{
    QuanLyChiTietPhieuNhapBUS qlctpn=new QuanLyChiTietPhieuNhapBUS(); 
    
    JTextField txTim = new JTextField(15);
    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã phiếu nhập", "Mã sản phẩm", "Số lượng", "Đơn giá"});
    JButton btnRefresh = new JButton("Làm mới");
    String mapn;

    MyTable mtb;

    public HienThiChiTietPN(String _mapn) {
        setLayout(new BorderLayout());
        this.mapn = _mapn;

        mtb = new MyTable();
        mtb.setHeaders(new String[]{"STT", "Mã phiếu nhập", "Mã sản phẩm", "Số lượng", "Đơn giá"});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(3, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        setDataToTable(qlctpn.search("Mã phiếu nhập", this.mapn), mtb);

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
        String _mapn = null;
        if (i >= 0) {
            _mapn = mtb.getTable().getModel().getValueAt(i, 1).toString();
            _masp = mtb.getTable().getModel().getValueAt(i, 2).toString();
        }
        if (_mapn != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa chi tiết này của phiếu nhập " + _mapn + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyChiTietPhieuNhapBUS().delete(_mapn, _masp);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
        refresh();
    }

    public void refresh() {
        qlctpn.readDB();
        setDataToTable(qlctpn.search("Mã hóa đơn", this.mapn), mtb);
    }

    private void txSearchOnChange() {
        setDataToTable(qlctpn.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText()), mtb);
    }

    public String getSelectedChiTietPhieuNhap(int col) {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            return mtb.getModel().getValueAt(i, col).toString();
        }
        return null;
    }

    private void setDataToTable(ArrayList<ChiTietPhieuNhap> data, MyTable mtb) {
        mtb.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (ChiTietPhieuNhap pn : data) {
            mtb.addRow(new String[]{String.valueOf(stt), pn.getMa(), pn.getMaSP(),
                String.valueOf(pn.getSoLuong()), String.valueOf(pn.getDonGia())});
            stt++;
        }
    }
}
