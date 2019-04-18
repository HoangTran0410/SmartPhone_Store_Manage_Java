/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyPN.PhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPN.QuanLyPhieuNhapBUS;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyChiTietPhieuNhapForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class HienThiPhieuNhap extends JPanel{
    QuanLyPhieuNhapBUS qlpn = new QuanLyPhieuNhapBUS();

    JTextField txTim = new JTextField(15);
    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã phiếu nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày lập", "Giờ lập", "Tổng tiền"});

    JButton btnRefresh = new JButton("Làm mới");
    JButton btnDetails = new JButton("Xem chi tiết");

    MyTable mtb;

    public HienThiPhieuNhap() {
        setLayout(new BorderLayout());

        mtb = new MyTable();
        mtb.setHeaders(new String[]{"STT", "Mã phiếu nhập", "Mã nhà cung cấp", "Mã nhân viên", "Ngày nhập", "Giờ nhập", "Tổng tiền"});
        mtb.setColumnsWidth(new double[]{.5, 1.6, 1.6, 1.6, 1.6, 1.5, 1.6});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.CENTER);
        mtb.setAlignment(5, JLabel.CENTER);
        mtb.setAlignment(6, JLabel.RIGHT);
        
        setDataToTable(qlpn.getDspn(), mtb);

        JPanel plHeader = new JPanel();
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);

        btnDetails.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_show_property_30px.png")));
        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnDetails);
        plHeader.add(btnRefresh);

        //=========== add all to this jpanel ===========
        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);

        btnRefresh.addActionListener((ae) -> {
            refresh();
        });

        btnDetails.addActionListener((ae) -> {
            btnDetailsMouseClicked();
        });
        
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

    }

    public void refresh() {
        qlpn.readDB();
        setDataToTable(qlpn.getDspn(), mtb);
    }

    public String getSelectedPhieuNhap() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            return mtb.getModel().getValueAt(i, 1).toString();
        }
        return null;
    }

    private void txSearchOnChange() {
        setDataToTable(qlpn.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText()), mtb);
    }

    private void btnDetailsMouseClicked() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            QuanLyChiTietPhieuNhapForm ctpn = new QuanLyChiTietPhieuNhapForm(mtb.getModel().getValueAt(i, 1).toString());
            ctpn.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refresh();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn nào để xem!");
        }
    }

    private void setDataToTable(ArrayList<PhieuNhap> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (PhieuNhap pn : data) {
            table.addRow(new String[]{String.valueOf(stt), pn.getMaPN(), pn.getMaNCC(), pn.getMaNV(),
                String.valueOf(pn.getNgayNhap()), String.valueOf(pn.getGioNhap()), String.valueOf(pn.getTongTien())});
            stt++;
        }
    }
}
