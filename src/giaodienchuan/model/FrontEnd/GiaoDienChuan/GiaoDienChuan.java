package giaodienchuan.model.FrontEnd.GiaoDienChuan;

import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyHoaDon_ChiTietHoaDon_form;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLySanPhamForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyKhachHangForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.EmptyPage;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyLoaiSanPhamForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyNhanVienForm;
import giaodienchuan.model.FrontEnd.FormQuanLy.QuanLyTaiKhoanForm;
import giaodienchuan.model.FrontEnd.NavBar.NavBarButton;
import giaodienchuan.model.FrontEnd.NavBar.NavBarContainer;
import giaodienchuan.model.FrontEnd.NavBar.NavBarSeperator;
import giaodienchuan.model.FrontEnd.NavBar.NavBarTitle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GiaoDienChuan extends JFrame implements MouseListener {

    final int WIDTH = 1200, HEIGHT = 800;
    int px, py;
    NavBarContainer menu, header;
    NavBarButton currentTab;

    JPanel plContent = new JPanel();
    EmptyPage emptypage = new EmptyPage();
    QuanLySanPhamForm qlsp;
    QuanLyLoaiSanPhamForm qllsp;
    QuanLyTaiKhoanForm qltk;
    QuanLyNhanVienForm qlnv;
    QuanLyKhachHangForm qlkh;
    QuanLyHoaDon_ChiTietHoaDon_form qlhd;

    public GiaoDienChuan() {

        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setTitle("Quản Lý Điện Thoại");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        ImageIcon logo = new ImageIcon(getClass().getResource("/giaodienchuan/images/icons8_windows_phone_store_30px.png"));
        setIconImage(logo.getImage());

        String[] navItemInfo = {
            "seperate", "2",
            "Sản phẩm", "icons8_multiple_smartphones_30px.png",
            "Loại sản phẩm", "icons8_dossier_folder_30px.png",
            "Hóa đơn", "icons8_agreement_30px.png",
            "seperate", "1",
            "Tài khoản", "icons8_circled_user_male_30px.png",
            "Nhân viên", "icons8_user_group_man_woman_30px.png",
            "Khách hàng", "icons8_user_30px.png",
            "Nhà cung cấp", "icons8_company_30px.png",
            "seperate", "1",
            "Thống kê", "icons8_bar_chart_30px.png",
            "Công cụ", "icons8_maintenance_30px.png",
            "Cài đặt", "icons8_settings_30px.png"
        };

        int menuW = 250;
        menu = new NavBarContainer(new Rectangle(0, 0, menuW, HEIGHT));
        menu.addItem(new NavBarTitle(new Rectangle(0, 0, 0, 55), "CHỨC NĂNG"));
        for (int i = 0; i < navItemInfo.length; i += 2) {
            if (navItemInfo[i].equals("seperate")) {
                NavBarSeperator s = new NavBarSeperator(new Rectangle(0, 0, 0, Integer.parseInt(navItemInfo[i + 1])));
                menu.addItem(s);
            } else {
                NavBarButton nb = new NavBarButton(new Rectangle(0, 0, 0, 60), navItemInfo[i], "/giaodienchuan/images/" + navItemInfo[i + 1]);
                nb.addMouseListener(this);
                menu.addItem(nb);
            }
        }

        int headerBg = 30;
        int headerH = 55;
        header = new NavBarContainer(new Rectangle(0, 0, WIDTH, headerH));
        header.setBackground(new Color(headerBg, headerBg, headerBg));

        NavBarTitle headerTitle = new NavBarTitle(new Rectangle((WIDTH - 400) / 2, 0, 400, headerH), "QUẢN LÝ SIÊU THỊ");
        headerTitle.setColorDefault(new Color(200, 200, 200));
        headerTitle.setBgDefault(new Color(headerBg, headerBg, headerBg));
        headerTitle.setFontSize(23);
        header.addItem(headerTitle, false);

        // Close Button
        int btnWidth = 50;
        int iconSize = 30;
        NavBarButton btnClose = new NavBarButton(new Rectangle(WIDTH - btnWidth, 0, btnWidth, headerH), "abc", "/giaodienchuan/images/icons8_shutdown_30px_1.png");
        btnClose.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
        btnClose.setBgDefault(new Color(headerBg, headerBg, headerBg));
        btnClose.setBgHover(new Color(190, 45, 45));
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
        NavBarButton btnMinimize = new NavBarButton(new Rectangle(WIDTH - btnWidth * 2, 0, btnWidth, headerH), "abc", "/giaodienchuan/images/icons8_angle_down_30px.png");
        btnMinimize.setIconLocation(new Rectangle((btnWidth - iconSize) / 2, (headerH - iconSize) / 2, iconSize, iconSize));
        btnMinimize.setBgDefault(new Color(headerBg, headerBg, headerBg));
        btnMinimize.setBgHover(new Color(49, 49, 49));
        btnMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                setState(ICONIFIED);
            }
        });
        header.addItem(btnMinimize, false);

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

        addMouseListener(this);
        add(menu, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(plContent, BorderLayout.CENTER);
    }

    public void doAction(String nameAction) {
        plContent.removeAll();
        switch (nameAction) {
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
                emptypage.setLabelText("Nhà cung cấp đang bảo trì");
                plContent.add(emptypage, BorderLayout.CENTER);
                break;

            case "Hóa đơn":
                if (qlhd == null) {
                    qlhd = new QuanLyHoaDon_ChiTietHoaDon_form();
                }
                plContent.add(qlhd, BorderLayout.CENTER);
                break;

            case "Thống kê":
                emptypage.setLabelText("Thống kê đang bảo trì");
                plContent.add(emptypage, BorderLayout.CENTER);
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
        // https://stackoverflow.com/questions/12989388/switching-panels-with-menubar
        revalidate();//refresh ui and layout
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getSource() instanceof NavBarButton) {

            NavBarButton btn = (NavBarButton) me.getSource();
            if (!btn.text.equals("Thoát") && !btn.text.equals("Thu nhỏ")) {
                if (currentTab != null) {
                    currentTab.setActive(false);
                }

                btn.setActive(true);
                currentTab = btn;
            }
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
