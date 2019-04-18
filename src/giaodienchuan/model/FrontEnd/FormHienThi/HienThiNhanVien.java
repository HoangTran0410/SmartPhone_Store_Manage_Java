package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HienThiNhanVien extends JPanel {

    QuanLyNhanVienBUS qlnv = new QuanLyNhanVienBUS();
    MyTable mtb;

    JTextField txTim = new JTextField(15);
    JComboBox<String> cbTypeSearch;
    JButton btnRefresh = new JButton("Làm mới");
    
    JLabel lbImage = new JLabel();

    // index
    final int MANV_I = 1, MACV_I = 2, TENNV_I = 3, NGAYSINH_I = 4, DIACHI_I = 5,  SDT_I = 6;

    public HienThiNhanVien() {
        setLayout(new BorderLayout());

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã nhân viên", "Mã chức vụ", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Số điện thoại"});
        mtb.setColumnsWidth(new double[]{.5, 1.5, 1.5, 2.5, 1.3,3, 1.5});
        mtb.setAlignment(0, JLabel.CENTER);
        setDataToTable(qlnv.getDsnv(), mtb);

        // ======== search panel ===========
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã nhân viên", "Mã chức vụ", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Số điện thoại"});

        JPanel plHeader = new JPanel();
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(cbTypeSearch);
        plTim.add(txTim);
        plHeader.add(plTim);
        
        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_data_backup_30px.png")));
        plHeader.add(btnRefresh);
        
        cbTypeSearch.addActionListener((ActionEvent e) -> {
            txTim.requestFocus();
            if (!txTim.getText().equals("")) {
                txSearchOnChange();
            }
        });
        
        btnRefresh.addActionListener((ae) -> {
            refresh();
        });

        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
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
        
        //=========== add all to this jpanel ===========
        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);
    }

    public void refresh() {
        qlnv.readDB();
        setDataToTable(qlnv.getDsnv(), mtb);
    }

    public String getSelectedNhanVien() {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            return mtb.getModel().getValueAt(i, MANV_I).toString();
        }
        return null;
    }

    private void txSearchOnChange() {
        setDataToTable(qlnv.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void setDataToTable(ArrayList<NhanVien> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (NhanVien nv : data) {
            table.addRow(new String[]{String.valueOf(stt), nv.getMaNV(), nv.getMaCV(), nv.getTenNV(),
                nv.getNgaySinh(),nv.getDiaChi(), nv.getSDT()});
            stt++;
        }
    }
}
