package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
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

public class HienThiSanPham extends JPanel {

    QuanLySanPhamBUS qlsp = new QuanLySanPhamBUS();
    MyTable mtb;

    JTextField txTim = new JTextField(15);
    JComboBox<String> cbTypeSearch;
    JButton btnRefresh = new JButton("Làm mới");

    JLabel lbImage = new JLabel();

    // index
    final int MASP_I = 1, MALSP_I = 2, TEN_I = 3, GIA_I = 4, SOLUONG_I = 5;

    public HienThiSanPham() {
        setLayout(new BorderLayout());

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 600));
        mtb.setHeaders(new String[]{"STT", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá (triệu)", "Số lượng"});
        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2, 1});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        mtb.setAlignment(5, JLabel.CENTER);
        setDataToTable(qlsp.getDssp(), mtb);

        // ======== search panel ===========
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng"});

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
        mtb.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                String masp = getSelectedSanPham(1);
                if (masp != null) {
                    // show hình
                    for (SanPham sp : qlsp.getDssp()) {
                        if (sp.getMaSP().equals(masp)) {
                            // https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
                            lbImage.setIcon(new ImageIcon(new ImageIcon(sp.getUrlHinhAnh()).getImage().getScaledInstance(lbImage.getWidth(), lbImage.getHeight(), Image.SCALE_DEFAULT)));
                        }
                    }

                }
            }
        });

        JPanel plcenterImage = new JPanel();
        lbImage.setPreferredSize(new Dimension(250, 250));
        plcenterImage.add(lbImage);

        //=========== add all to this jpanel ===========
        this.add(plHeader, BorderLayout.NORTH);
        this.add(mtb, BorderLayout.CENTER);
        this.add(plcenterImage, BorderLayout.WEST);
    }

    public void refresh() {
        qlsp.readDB();
        setDataToTable(qlsp.getDssp(), mtb);
    }

    public String getSelectedSanPham(int col) {
        int i = mtb.getTable().getSelectedRow();
        if (i >= 0) {
            return mtb.getModel().getValueAt(i, col).toString();
        }
        return null;
    }
    
    private void txSearchOnChange() {
        setDataToTable(qlsp.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void setDataToTable(ArrayList<SanPham> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (SanPham sp : data) {
            table.addRow(new String[]{String.valueOf(stt), sp.getMaSP(), sp.getMaLSP(), sp.getTenSP(),
                String.valueOf(sp.getDonGia()), String.valueOf(sp.getSoLuong())});
            stt++;
        }
    }
}
