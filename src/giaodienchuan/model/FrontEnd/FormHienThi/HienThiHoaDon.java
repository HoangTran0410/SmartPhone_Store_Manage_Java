package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HienThiHoaDon extends JPanel {

    QuanLyHoaDonBUS qlhd = new QuanLyHoaDonBUS();

    JTextField txtSearchbox = new JTextField();
    JComboBox timComboBox = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});

    JButton btnXoaHoaDon = new JButton("Xóa");
    JButton btnReadDB = new JButton("Đọc DB");
    JButton btnDetails = new JButton("Xem chi tiết");

    MyTable tbHoaDon = new MyTable();

    public HienThiHoaDon() {

        JPanel pnlHoaDon = new JPanel();
        pnlHoaDon.setLayout(new BorderLayout());
        pnlHoaDon.setPreferredSize(new Dimension(1200 - 250, 350));

        JPanel pnlInput = new JPanel();
        pnlInput.setLayout(new GridLayout(1, 4));
        pnlInput.setPreferredSize(new Dimension(900, 45));

        JPanel pnlBtn = new JPanel();
        pnlBtn.setLayout(new GridLayout(1, 8));
        txtSearchbox.setBorder(BorderFactory.createTitledBorder("Nhập vào đây đê"));
        pnlBtn.add(btnDetails);
        pnlBtn.add(btnReadDB);
        pnlBtn.add(txtSearchbox);
        pnlBtn.add(timComboBox);
        pnlHoaDon.add(tbHoaDon);

        tbHoaDon.setHeaders(new String[]{"STT", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày lập", "Giờ lập", "Tổng tiền"});
        tbHoaDon.setColumnsWidth(new double[]{.5, 1.6, 1.6, 1.6, 1.6, 1.5, 1.6});
        tbHoaDon.setAlignment(0, JLabel.CENTER);
        tbHoaDon.setAlignment(4, JLabel.CENTER);
        tbHoaDon.setAlignment(5, JLabel.CENTER);
        tbHoaDon.setAlignment(6, JLabel.RIGHT);
        setDataToTable(qlhd.getDshd(), tbHoaDon);
        this.add(pnlBtn);
        this.add(pnlHoaDon);

        btnReadDB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refresh();
            }
        });

        btnDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tbHoaDon.getTable().getSelectedRow();
                if (i >= 0) {
                    btnDetailsMouseClicked(i);
                }
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
        setDataToTable(qlhd.search(timComboBox.getSelectedItem().toString(), txtSearchbox.getText()), tbHoaDon);
    }

    private void btnDetailsMouseClicked(int i) {
        HienThiChiTietHoaDon htcthd = new HienThiChiTietHoaDon(tbHoaDon.getModel().getValueAt(i, 1).toString());
        htcthd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refresh();
            }
        });
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
