package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyChiTietHoaDonForm;
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

public class HienThiHoaDon extends JPanel {

    QuanLyHoaDonBUS qlhd = new QuanLyHoaDonBUS();

    JTextField txTim = new JTextField(15);
    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});

    JButton btnRefresh = new JButton("Làm mới");
    JButton btnDetails = new JButton("Xem chi tiết");

    MyTable tbHoaDon;

    public HienThiHoaDon() {
        setLayout(new BorderLayout());

        tbHoaDon = new MyTable();
        tbHoaDon.setHeaders(new String[]{"STT", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});
        tbHoaDon.setColumnsWidth(new double[]{.5, 1.6, 1.6, 1.6, 1.6, 1.5, 1.6});
        tbHoaDon.setAlignment(0, JLabel.CENTER);
        tbHoaDon.setAlignment(4, JLabel.CENTER);
        tbHoaDon.setAlignment(5, JLabel.CENTER);
        tbHoaDon.setAlignment(6, JLabel.RIGHT);
        setDataToTable(qlhd.getDshd(), tbHoaDon);

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
        this.add(tbHoaDon, BorderLayout.CENTER);

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
        qlhd.readDB();
        setDataToTable(qlhd.getDshd(), tbHoaDon);
    }

    public String getSelectedHoaDon() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            return tbHoaDon.getModel().getValueAt(i, 1).toString();
        }
        return null;
    }

    private void txSearchOnChange() {
        setDataToTable(qlhd.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText()), tbHoaDon);
    }

    private void btnDetailsMouseClicked() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            QuanLyChiTietHoaDonForm htcthd = new QuanLyChiTietHoaDonForm(tbHoaDon.getModel().getValueAt(i, 1).toString());
            htcthd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refresh();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn nào để xem!");
        }
    }

    private void setDataToTable(ArrayList<HoaDon> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (HoaDon hd : data) {
            table.addRow(new String[]{String.valueOf(stt), hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(),
                String.valueOf(hd.getNgayLap()), String.valueOf(hd.getGioLap()), String.valueOf(hd.getTongTien())});
            stt++;
        }
    }
}
