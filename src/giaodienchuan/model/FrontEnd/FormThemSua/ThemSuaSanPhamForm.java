package giaodienchuan.model.FrontEnd.FormThemSua;

import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.FrontEnd.FormChon.ChonLoaiSanPhamForm;
import giaodienchuan.model.FrontEnd.FormChon.ChonSanPhamForm;
import giaodienchuan.model.FrontEnd.MyButton.FileButton;
import giaodienchuan.model.FrontEnd.MyButton.MoreButton;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ThemSuaSanPhamForm extends JFrame {

    String type;
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();
    SanPham spSua;

    JTextField txMasp = new JTextField(15);
    JTextField txMalsp = new JTextField(15);
    JTextField txTen = new JTextField(15);
    JTextField txGia = new JTextField(15);
    JTextField txSoLuong = new JTextField(15);
    JTextField txHinhAnh = new JTextField(15);

    FileDialog fdChooseImg = new FileDialog(this);
    FileButton btnChonAnh = new FileButton();
    MoreButton btnChonLoai = new MoreButton();

    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnHuy = new JButton("Hủy");

    public ThemSuaSanPhamForm(String _type, String _masp) {
        this.setLayout(new BorderLayout());
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.type = _type;

        // inputs
        txMasp.setBorder(BorderFactory.createTitledBorder("Mã sản phẩm"));
        txMalsp.setBorder(BorderFactory.createTitledBorder(" "));
        txTen.setBorder(BorderFactory.createTitledBorder("Tên"));
        txGia.setBorder(BorderFactory.createTitledBorder("Đơn Giá (triệu)"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        txHinhAnh.setBorder(BorderFactory.createTitledBorder(" "));
        
        JPanel plChonLoai = new JPanel();
        plChonLoai.setBorder(BorderFactory.createTitledBorder("Mã loại"));
        plChonLoai.add(txMalsp);
        plChonLoai.add(btnChonLoai);
        
        JPanel plChonAnh = new JPanel();
        plChonAnh.setBorder(BorderFactory.createTitledBorder("Url Hình ảnh"));
        plChonAnh.add(txHinhAnh);
        plChonAnh.add(btnChonAnh);

        JPanel plInput = new JPanel();
        plInput.add(txMasp);
        plInput.add(plChonLoai);
        plInput.add(txTen);
        plInput.add(txGia);
        plInput.add(txSoLuong);
        plInput.add(plChonAnh);

        // panel buttons
        JPanel plButton = new JPanel();

        // 2 case Thêm - Sửa
        if (this.type.equals("Thêm")) {
            this.setTitle("Thêm sản phẩm");
            txMasp.setText("SP" + String.valueOf(qlspBUS.getDssp().size() + 1));
            
            btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
            plButton.add(btnThem);

        } else {
            this.setTitle("Sửa sản phẩm");
            for (SanPham sp : qlspBUS.getDssp()) {
                if (sp.getMaSP().equals(_masp)) {
                    this.spSua = sp;
                }
            }
            if (this.spSua == null) {
                JOptionPane.showMessageDialog(null, "Lỗi, không tìm thấy sản phẩm");
                this.dispose();
            }

            txMasp.setText(this.spSua.getMaSP());
            txMalsp.setText(this.spSua.getMaLSP());
            txTen.setText(this.spSua.getTenSP());
            txGia.setText(String.valueOf(this.spSua.getDonGia()));
            txSoLuong.setText(String.valueOf(this.spSua.getSoLuong()));
            txHinhAnh.setText(this.spSua.getUrlHinhAnh().replace("\\", "\\\\"));

            txMasp.setEditable(false);
            
            btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
            plButton.add(btnSua);
        }
        
        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        plButton.add(btnHuy);

        this.add(plInput, BorderLayout.CENTER);
        this.add(plButton, BorderLayout.SOUTH);

        // mouse listener
        btnThem.addActionListener((ae) -> {
            btnThemMouseClicked();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaMouseClicked();
        });
        btnHuy.addActionListener((ae) -> {
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy? Mọi giá trị nhập vào sẽ mất!", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                this.dispose();
            }
        });
        btnChonLoai.addActionListener((ActionEvent ae) -> {
            btnChonLoaiMouseClicked();
        });
        btnChonAnh.addActionListener((ae) -> {
            btnChonAnhMouseClicked();
        });
        
        this.setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String masp = txMasp.getText();
            String maloai = txMalsp.getText();
            String ten = txTen.getText();
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoLuong.getText());
            String url = txHinhAnh.getText();

            if (qlspBUS.add(masp, maloai, ten, dongia, soluong, url)) {
                JOptionPane.showMessageDialog(this, "Thêm " + ten + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String masp = txMasp.getText();
            String maloai = txMalsp.getText();
            String ten = txTen.getText();
            float dongia = Float.parseFloat(txGia.getText());
            int soluong = Integer.parseInt(txSoLuong.getText());
            String url = txHinhAnh.getText();

            if (qlspBUS.update(masp, maloai, ten, dongia, soluong, url)) {
                JOptionPane.showMessageDialog(this, "Sửa " + masp + " thành công!");
                this.dispose();
            }
        }
    }

    private void btnChonLoaiMouseClicked() {
        ChonLoaiSanPhamForm clsp = new ChonLoaiSanPhamForm(txMalsp); // truyền vào textfield
//        ChonSanPhamForm csp = new ChonSanPhamForm(txMalsp);
    }

    private void btnChonAnhMouseClicked() {
        fdChooseImg.setVisible(true);
        String url = fdChooseImg.getDirectory() + fdChooseImg.getFile();
        if (!url.equals("nullnull")) {
            // https://stackoverflow.com/questions/2242417/how-to-remove-the-backslash-in-string-using-regex-in-java
            url = url.replace("\\", "\\\\");
            txHinhAnh.setText(url);
        }
    }

    private Boolean checkEmpty() {
        String mssp = txMasp.getText();
        String maloai = txMalsp.getText();
        String ten = txTen.getText();
        String dongia = txGia.getText();
        String soluong = txSoLuong.getText();
        String url = txHinhAnh.getText();

        if (mssp.trim().equals("")) {
            return showErrorTx(txMasp, "Mã sp không được để trống");

        } else if (maloai.trim().equals("")) {
            return showErrorTx(txMalsp, "Mã loại không được để trống");

        } else if (ten.trim().equals("")) {
            return showErrorTx(txTen, "Tên không được để trống");

        } else if (dongia.trim().equals("")) {
            return showErrorTx(txGia, "Đơn giá không được để trống");

        } else if (soluong.trim().equals("")) {
            return showErrorTx(txSoLuong, "Số lượng không được để trống");

        } else if (url.trim().equals("")) {
            return showErrorTx(txHinhAnh, "Đường dẫn ảnh không được để trống");

        } else {
            try {
                float dg = Float.parseFloat(dongia);
            } catch (NumberFormatException e) {
                return showErrorTx(txGia, "Đơn giá không hợp lệ (phải là số thực)");
            }

            try {
                int sl = Integer.parseInt(soluong);
                if (sl < 0) {
                    return showErrorTx(txSoLuong, "Số lượng không hợp lệ (phải là số duơng)");
                }
            } catch (NumberFormatException e) {
                return showErrorTx(txSoLuong, "Số lượng không hợp lệ (phải là số nguyên)");
            }
        }
        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(tx, errorInfo);
        tx.requestFocus();
        return false;
    }
}
