package giaodienchuan.model.FrontEnd.GiaoDienChuan;

import giaodienchuan.model.BackEnd.ReadWriteFile.WorkWithFile;
import giaodienchuan.model.FrontEnd.FormQuanLy.BanHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.BeginForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.EmptyPage;
import giaodienchuan.model.FrontEnd.FormQuanLy.NhapHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyNhaCungCapForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyHoaDonForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLySanPhamForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyKhachHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyKhuyenMaiForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyLoaiSanPhamForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyNhanVienForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyPhieuNhapForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyQuyenForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyTaiKhoanForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.ThongKe.ThongKeForm;
import giaodienchuan.model.FrontEnd.NavBar.NavBarButton;
import giaodienchuan.model.FrontEnd.NavBar.NavBarContainer;
import giaodienchuan.model.FrontEnd.NavBar.NavBarSeperator;
import giaodienchuan.model.FrontEnd.NavBar.NavBarTitle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GiaoDienChuan extends JFrame implements MouseListener {
    
    final int WIDTH = 1400, HEIGHT = 800;
    int px, py;
    NavBarContainer menu, header;
    NavBarButton currentTab;
    NavBarTitle headerTitle;

    JPanel plContent = new JPanel();
    EmptyPage emptypage = new EmptyPage();
    BanHangForm banhang;
    NhapHangForm nhaphang;
    QuanLySanPhamForm qlsp;
    QuanLyLoaiSanPhamForm qllsp;
    QuanLyTaiKhoanForm qltk;
    QuanLyNhanVienForm qlnv;
    QuanLyKhachHangForm qlkh;
    QuanLyQuyenForm qlq;
    QuanLyHoaDonForm qlhd;
    QuanLyPhieuNhapForm qlpn;
    QuanLyKhuyenMaiForm qlkm;
    QuanLyNhaCungCapForm qlncc;
    ThongKeForm thongke;

    public GiaoDienChuan() {

        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setTitle("Quản Lý Điện Thoại");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        ImageIcon logo = new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_windows_phone_store_30px.png"));
        setIconImage(logo.getImage());

        // ======================== Menu =======================
        String[] navItemInfo = {
            "seperate", "2", "", "",
            "Bán hàng", "icons8_small_business_30px_3.png", "qlBanHang", "qlBanHang",
            "Nhập hàng", "icons8_downloads_30px.png", "qlNhapHang", "qlNhapHang",
            "seperate", "2", "", "",
            "Sản phẩm", "icons8_multiple_smartphones_30px.png", "xemSanPham", "qlSanPham",
            "Loại sản phẩm", "icons8_dossier_folder_30px.png", "xemLoaiSanPham", "qlLoaiSanPham",
            "Hóa đơn", "icons8_agreement_30px.png", "xemHoaDon", "qlHoaDon",
            "Phiếu nhập", "icons8_truck_30px.png", "xemPheuNhap", "qlPhieuNhap",
            "Khuyến mãi", "icons8_gift_30px.png", "xemKhuyenMai", "qlKhuyenMai",
            "seperate", "1", "", "",
            "Nhân viên", "icons8_assistant_30px.png", "xemNhanVien", "qlNhanVien",
            "Khách hàng", "icons8_user_30px.png", "xemKhachHang", "qlKhachHang",
            "Nhà cung cấp", "icons8_company_30px.png", "xemNCC", "qlNCC",
            "seperate", "1", "", "",
            "Tài khoản", "icons8_key_30px.png", "xemTaiKhoan", "qlTaiKhoan",
            "Quyền", "icons8_police_badge_30px.png", "xemQuyen", "qlQuyen",
            "seperate", "1", "", "",
            "Thống kê", "icons8_bar_chart_30px.png", "", "",
            "Công cụ", "icons8_maintenance_30px.png", "", "",
            "Cài đặt", "icons8_settings_30px.png", "", ""
        };

        int menuW = 250;
        int menuH = 0;
        menu = new NavBarContainer(new Rectangle(0, 0, menuW, HEIGHT));
        // menu.addItem(new NavBarTitle(new Rectangle(0, 0, 0, 55), "CHỨC NĂNG"));
        for (int i = 0; i < navItemInfo.length; i += 4) {
            if (navItemInfo[i].equals("seperate")) {
                NavBarSeperator s = new NavBarSeperator(new Rectangle(0, 0, 0, Integer.parseInt(navItemInfo[i + 1])));
                menu.addItem(s);

            } else {

                String chitietquyen = LoginForm.quyenLogin.getChiTietQuyen();
                if (chitietquyen.contains(navItemInfo[i + 2]) || chitietquyen.contains(navItemInfo[i + 3])) {
                    NavBarButton nb = new NavBarButton(new Rectangle(0, 0, 0, 60), navItemInfo[i], navItemInfo[i + 1]);
                    nb.addMouseListener(this);
                    menu.addItem(nb);
                    menuH += 60;
                }
            }
        }

        //https://stackoverflow.com/questions/1385737/scrollable-jpanel
        //https://stackoverflow.com/questions/5590242/how-to-hide-the-jscrollbars-in-a-jscrollpane
        //https://stackoverflow.com/questions/5583495/how-do-i-speed-up-the-scroll-speed-in-a-jscrollpane-when-using-the-mouse-wheel
        JScrollPane scrollMenu = new JScrollPane(menu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menu.setAutoscrolls(true);
        menu.setPreferredSize(new Dimension(menuW, menuH + 100));
        scrollMenu.setPreferredSize(new Dimension(menuW, HEIGHT));
        scrollMenu.setBorder(BorderFactory.createEmptyBorder());
        scrollMenu.getVerticalScrollBar().setUnitIncrement(5);

        // ================ Header ===================
        int headerBg = 30;
        int headerH = 55;
        header = new NavBarContainer(new Rectangle(0, 0, WIDTH, headerH));
        header.setBackground(new Color(headerBg, headerBg, headerBg));

        headerTitle = new NavBarTitle(new Rectangle((WIDTH - 400) / 2, 0, 400, headerH), "QUẢN LÝ SIÊU THỊ");
        headerTitle.setColorDefault(new Color(200, 200, 200));
        headerTitle.setBgDefault(new Color(headerBg, headerBg, headerBg));
        headerTitle.setFontSize(23);
        header.addItem(headerTitle, false);

        // Close Button
        int btnWidth = 50;
        int iconSize = 30;
        NavBarButton btnClose = new NavBarButton(new Rectangle(WIDTH - btnWidth, 0, btnWidth, headerH), "", "icons8_shutdown_30px_1.png");
        btnClose.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
        btnClose.setBgDefault(new Color(headerBg, headerBg, headerBg));
        btnClose.setBgHover(new Color(190, 45, 45));
        btnClose.setToolTipText("Thoát");
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                int reply = JOptionPane.showConfirmDialog(getRootPane(),
                        "Bạn có chắc muốn thoát chương trình Quản Lý?", "Chú ý",
                        JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        header.addItem(btnClose, false);

        // Minimize Button
        NavBarButton btnMinimize = new NavBarButton(new Rectangle(WIDTH - btnWidth * 2, 0, btnWidth, headerH), "", "icons8_angle_down_30px.png");
        btnMinimize.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
        btnMinimize.setBgDefault(new Color(headerBg, headerBg, headerBg));
        btnMinimize.setBgHover(new Color(49, 49, 49));
        btnMinimize.setToolTipText("Thu nhỏ");
        btnMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                setState(ICONIFIED);
            }
        });
        header.addItem(btnMinimize, false);

        // logout button
        if (LoginForm.taiKhoanLogin != null) {

            String tenNhanVien = LoginForm.nhanVienLogin.getTenNV();

            NavBarButton btnLogout = new NavBarButton(new Rectangle(0, 0, menuW - btnWidth, headerH), tenNhanVien, "icons8_exit_30px.png");
            btnLogout.setBgDefault(new Color(headerBg, headerBg, headerBg));
            btnLogout.setBgHover(new Color(49, 49, 49));
            btnLogout.relocate2();
            btnLogout.setToolTipText("Đăng xuất (" + tenNhanVien + " - " + LoginForm.nhanVienLogin.getMaNV() + ")");
            btnLogout.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    logout();
                }
            });
            header.addItem(btnLogout, false);

            // btn Xem tài khoản đăng nhập
            NavBarButton btnSettingUser = new NavBarButton(new Rectangle(menuW - btnWidth, 0, btnWidth, headerH), "", "icons8_settings_30px_1.png");
            btnSettingUser.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
            btnSettingUser.setBgDefault(new Color(headerBg, headerBg, headerBg));
            btnSettingUser.setBgHover(new Color(49, 49, 49));
            btnSettingUser.setToolTipText("Tài khoản");
            btnSettingUser.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    new DoiMatKhauForm(LoginForm.taiKhoanLogin.getUsername()).setVisible(true);
                }
            });
            header.addItem(btnSettingUser, false);
        }

        // ========= Draggable ===========
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                px = me.getX();
                py = me.getY();
            }
        });

        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - px, getLocation().y + me.getY() - py);
            }
        });

        plContent.setLayout(new BorderLayout());
        plContent.add(new BeginForm("Chào " + LoginForm.nhanVienLogin.getTenNV() + " - " + LoginForm.nhanVienLogin.getMaNV()), BorderLayout.CENTER);

        addMouseListener(this);
        add(scrollMenu, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(plContent, BorderLayout.CENTER);
    }

    private void logout() {
        int reply = JOptionPane.showConfirmDialog(getRootPane(),
                "Bạn có chắc muốn đăng xuất khỏi " + LoginForm.nhanVienLogin.getTenNV() + "?", "Chú ý",
                JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            new WorkWithFile(LoginForm.saveFileName).write(""); // xóa dữ liệu đăng nhập
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }

    public void doAction(String nameAction) {
        plContent.removeAll();
        switch (nameAction) {
            case "Bán hàng":
                if (banhang == null) {
                    banhang = new BanHangForm(WIDTH - 250, HEIGHT - 55);
                }
                plContent.add(banhang, BorderLayout.CENTER);
                break;
                
            case "Nhập hàng":
                if (nhaphang == null) {
                    nhaphang = new NhapHangForm(WIDTH - 250, HEIGHT - 55);
                }
                plContent.add(nhaphang, BorderLayout.CENTER);
                break;

            case "Sản phẩm":
                if (qlsp == null) {
                    qlsp = new QuanLySanPhamForm();
                }
                plContent.add(qlsp, BorderLayout.CENTER);
                break;

            case "Loại sản phẩm":
                if (qllsp == null) {
                    qllsp = new QuanLyLoaiSanPhamForm();
                }
                plContent.add(qllsp, BorderLayout.CENTER);
                break;

            case "Hóa đơn":
                if (qlhd == null) {
                    qlhd = new QuanLyHoaDonForm();
                }
                plContent.add(qlhd, BorderLayout.CENTER);
                break;

            case "Khuyến mãi":
                if (qlkm == null) {
                    qlkm = new QuanLyKhuyenMaiForm();
                }
                plContent.add(qlkm, BorderLayout.CENTER);
                break;

            case "Phiếu nhập":
                if (qlpn == null) {
                    qlpn = new QuanLyPhieuNhapForm();
                }
                plContent.add(qlpn, BorderLayout.CENTER);
                break;

            case "Quyền":
                if (qlq == null) {
                    qlq = new QuanLyQuyenForm();
                }
                plContent.add(qlq, BorderLayout.CENTER);
                break;

            case "Tài khoản":
                if (qltk == null) {
                    qltk = new QuanLyTaiKhoanForm();
                }
                plContent.add(qltk, BorderLayout.CENTER);
                break;

            case "Nhân viên":
                if (qlnv == null) {
                    qlnv = new QuanLyNhanVienForm();
                }
                plContent.add(qlnv, BorderLayout.CENTER);
                break;

            case "Khách hàng":
                if (qlkh == null) {
                    qlkh = new QuanLyKhachHangForm();
                }
                plContent.add(qlkh, BorderLayout.CENTER);
                break;

            case "Nhà cung cấp":

                if (qlncc == null) {
                    qlncc = new QuanLyNhaCungCapForm();
                }
                plContent.add(qlncc, BorderLayout.CENTER);
                break;

            case "Thống kê":
                if (thongke == null) {
                    thongke = new ThongKeForm();
                }
                plContent.add(thongke, BorderLayout.CENTER);
                break;

            case "Công cụ":
                emptypage.setLabelText("Công cụ đang bảo trì");
                plContent.add(emptypage, BorderLayout.CENTER);
                break;

            case "Cài đặt":
                emptypage.setLabelText("Cài đặt đang bảo trì");
                plContent.add(emptypage, BorderLayout.CENTER);
                break;
        }
        headerTitle.setLabel(nameAction.toUpperCase());
        // https://stackoverflow.com/questions/12989388/switching-panels-with-menubar
        revalidate();//refresh ui and layout
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getSource() instanceof NavBarButton) {

            NavBarButton btn = (NavBarButton) me.getSource();
            if (currentTab != null) {
                currentTab.setActive(false);
            }

            btn.setActive(true);
            currentTab = btn;
            doAction(btn.text);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
