package giaodienchuan.model.FrontEnd.FormHienThi;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyChiTietHoaDonForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;

public class HienThiHoaDon extends JPanel {

    QuanLyHoaDonBUS qlhd = new QuanLyHoaDonBUS();

    JTextField txTim = new JTextField(10);
    JComboBox cbTypeSearch = new JComboBox(new String[]{"Tất cả", "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng"/*, "Ngày lập", "Giờ lập", "Tổng tiền"*/});

    JButton btnRefresh = new JButton("Làm mới");
    JButton btnDetails = new JButton("Xem chi tiết");
    JTextField txKhoangNgay1 = new JTextField(10);
    JTextField txKhoangNgay2 = new JTextField(10);
    JTextField txKhoangTien1 = new JTextField(10);
    JTextField txKhoangTien2 = new JTextField(10);

    DatePicker dPicker1;
    DatePicker dPicker2;

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

        txKhoangNgay1.setBorder(BorderFactory.createTitledBorder("Từ ngày:"));
        txKhoangNgay2.setBorder(BorderFactory.createTitledBorder("Đến ngày:"));
        txKhoangTien1.setBorder(BorderFactory.createTitledBorder("Tổng tiền từ:"));
        txKhoangTien2.setBorder(BorderFactory.createTitledBorder("Đến"));
        DatePickerSettings pickerSettings1 = new DatePickerSettings();
        pickerSettings1.setVisibleDateTextField(false);
        DatePickerSettings pickerSettings2 = pickerSettings1.copySettings();
        
//        pickerSettings.g
        
        dPicker1 = new DatePicker(pickerSettings1);
        dPicker2 = new DatePicker(pickerSettings2);
//        date1.setDateToToday();
        System.out.println(String.valueOf(dPicker1.getDate()));
        

        plTim.add(txKhoangNgay1);
        plTim.add(dPicker1);
        plTim.add(txKhoangNgay2);
        plTim.add(dPicker2);
        plTim.add(txKhoangTien1);
        plTim.add(txKhoangTien2);
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

        cbTypeSearch.addActionListener((ae) -> {
            txSearchOnChange();
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
        txKhoangNgay1.getDocument().addDocumentListener(new DocumentListener() {
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
        dPicker1.addDateChangeListener((dce) -> {
            txKhoangNgay1.setText(dPicker1.getDateStringOrEmptyString());
        });
        dPicker2.addDateChangeListener((dce) -> {
            txKhoangNgay1.setText(dPicker2.getDateStringOrEmptyString());
        });
        txKhoangNgay2.getDocument().addDocumentListener(new DocumentListener() {
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
        txKhoangTien1.getDocument().addDocumentListener(new DocumentListener() {
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
        txKhoangTien2.getDocument().addDocumentListener(new DocumentListener() {
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
        txTim.setText("");
        txKhoangNgay1.setText("");
        txKhoangNgay2.setText("");
        txKhoangTien1.setText("");
        txKhoangTien2.setText("");
    }

    public String getSelectedHoaDon() {
        int i = tbHoaDon.getTable().getSelectedRow();
        if (i >= 0) {
            return tbHoaDon.getModel().getValueAt(i, 1).toString();
        }
        return null;
    }

    private void txSearchOnChange() {
        LocalDate ngay1;
        LocalDate ngay2;
        int tong1;
        int tong2;
        try {
            ngay1 = java.time.LocalDate.parse(txKhoangNgay1.getText());
            txKhoangNgay1.setForeground(Color.black);
        } catch (DateTimeParseException e) {
            txKhoangNgay1.setForeground(Color.red);
            ngay1 = null;
        }
        try {
            ngay2 = java.time.LocalDate.parse(txKhoangNgay2.getText());
            txKhoangNgay2.setForeground(Color.black);
        } catch (DateTimeParseException e) {
            txKhoangNgay2.setForeground(Color.red);
            ngay2 = null;
        }
        try {
            tong1 = Integer.parseInt(txKhoangTien1.getText());
        } catch (NumberFormatException e) {
            tong1 = -1;
        }
        try {
            tong2 = Integer.parseInt(txKhoangTien2.getText());
        } catch (NumberFormatException e) {
            tong2 = -1;
        }
        setDataToTable(qlhd.search(cbTypeSearch.getSelectedItem().toString(), txTim.getText(), ngay1, ngay2, tong1, tong2), tbHoaDon);
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
